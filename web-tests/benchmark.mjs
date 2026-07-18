import { chromium } from "@playwright/test";
import { readdirSync, readFileSync, statSync } from "node:fs";
import { resolve } from "node:path";
import { fileURLToPath } from "node:url";
import { gzipSync } from "node:zlib";
import { startServer } from "./serve.mjs";
import { points, sweep, targets } from "./targets.mjs";

const samplesPerTarget = Number(process.env.WEB_BENCHMARK_SAMPLES ?? 10);
const interactionClicks = Number(process.env.WEB_BENCHMARK_CLICKS ?? 21);
const cpuThrottleRate = Number(process.env.WEB_BENCHMARK_CPU_THROTTLE ?? 4);
const headed = process.env.WEB_BENCHMARK_HEADED === "1";
const repoRoot = fileURLToPath(new URL("../", import.meta.url));

function distributionBytes(targetPath) {
  const files = [];
  const visit = path => {
    if (statSync(path).isDirectory()) {
      for (const name of readdirSync(path)) visit(resolve(path, name));
    } else if (!path.endsWith(".map")) {
      files.push(path);
    }
  };
  visit(resolve(repoRoot, targetPath.slice(1)));
  return {
    rawBytes: files.reduce((sum, file) => sum + statSync(file).size, 0),
    gzipBytes: files.reduce((sum, file) => sum + gzipSync(readFileSync(file)).length, 0),
  };
}

function percentile(values, fraction) {
  const sorted = [...values].sort((a, b) => a - b);
  return sorted[Math.max(0, Math.ceil(sorted.length * fraction) - 1)];
}

function median(values) {
  const sorted = [...values].sort((a, b) => a - b);
  const middle = Math.floor(sorted.length / 2);
  return sorted.length % 2 === 0
    ? (sorted[middle - 1] + sorted[middle]) / 2
    : sorted[middle];
}

function summarize(values) {
  return {
    median: Number(median(values).toFixed(1)),
    p90: Number(percentile(values, 0.9).toFixed(1)),
    min: Number(Math.min(...values).toFixed(1)),
    max: Number(Math.max(...values).toFixed(1)),
  };
}

// Records requestAnimationFrame deltas in the page while `interact` runs, so
// the reported frame durations reflect main-thread cost only — Playwright's
// protocol round trips show up as extra frames, not longer ones.
async function recordFrames(page, interact) {
  await page.evaluate(() => {
    const recorder = { frames: [], running: true, last: undefined };
    window.__frameRecorder = recorder;
    const tick = now => {
      if (recorder.last !== undefined) recorder.frames.push(now - recorder.last);
      recorder.last = now;
      if (recorder.running) requestAnimationFrame(tick);
    };
    requestAnimationFrame(tick);
  });
  await interact();
  const frames = await page.evaluate(() => new Promise(done => {
    requestAnimationFrame(() => requestAnimationFrame(() => {
      window.__frameRecorder.running = false;
      done(window.__frameRecorder.frames);
    }));
  }));
  if (frames.length === 0) throw new Error("frame recorder captured no frames");
  return frames;
}

function settleFrames(page) {
  return page.evaluate(() => new Promise(done => {
    requestAnimationFrame(() => requestAnimationFrame(done));
  }));
}

async function runSample(browser, target, path, { verify }) {
  const context = await browser.newContext({ viewport: { width: 1280, height: 720 } });
  const page = await context.newPage();
  const errors = [];
  page.on("pageerror", error => errors.push(error.message));
  page.on("console", message => {
    if (message.type() === "error") errors.push(message.text());
  });

  const cdp = await context.newCDPSession(page);
  await cdp.send("Emulation.setCPUThrottlingRate", { rate: cpuThrottleRate });

  await page.goto(`http://127.0.0.1:4173${path}`);
  await page.waitForFunction(() => document.documentElement.dataset.composeReadyMs);
  const readyMs = await page.evaluate(() => Number(document.documentElement.dataset.composeReadyMs));

  if (verify) {
    const before = await page.screenshot();
    await page.mouse.click(points.modifierToggle.x, points.modifierToggle.y);
    await settleFrames(page);
    const after = await page.screenshot();
    if (after.equals(before)) {
      throw new Error(
        `${target}: click at (${points.modifierToggle.x}, ${points.modifierToggle.y}) produced no repaint — the toggle coordinate is stale`,
      );
    }
    await page.mouse.click(points.modifierToggle.x, points.modifierToggle.y);
    await settleFrames(page);
  }

  const clickFrames = await recordFrames(page, async () => {
    for (let click = 0; click < interactionClicks; click += 1) {
      await page.mouse.click(points.modifierToggle.x, points.modifierToggle.y);
    }
  });

  const sweepFrames = await recordFrames(page, async () => {
    await page.mouse.move(sweep.from.x, sweep.from.y);
    await page.mouse.move(sweep.to.x, sweep.to.y, { steps: sweep.steps });
    await page.mouse.move(sweep.from.x, sweep.from.y, { steps: sweep.steps });
  });

  if (errors.length > 0) throw new Error(`${target} browser errors: ${errors.join("; ")}`);
  await context.close();

  return {
    readyMs,
    clickFrameP90Ms: Number(percentile(clickFrames, 0.9).toFixed(1)),
    clickFrameMaxMs: Number(Math.max(...clickFrames).toFixed(1)),
    sweepFrameP90Ms: Number(percentile(sweepFrames, 0.9).toFixed(1)),
    sweepFrameMaxMs: Number(Math.max(...sweepFrames).toFixed(1)),
  };
}

const server = await startServer();
const browser = await chromium.launch({ headless: !headed });
const results = Object.fromEntries(Object.keys(targets).map(target => [target, []]));

try {
  // Warm-up pass: absorbs first-run cache effects asymmetric to whichever
  // target goes first, and verifies the toggle coordinate still repaints.
  for (const [target, path] of Object.entries(targets)) {
    await runSample(browser, target, path, { verify: true });
  }
  for (let sample = 0; sample < samplesPerTarget; sample += 1) {
    const order = sample % 2 === 0 ? Object.entries(targets) : Object.entries(targets).reverse();
    for (const [target, path] of order) {
      results[target].push(await runSample(browser, target, path, { verify: false }));
    }
  }
} finally {
  await browser.close();
  await new Promise(resolve => server.close(resolve));
}

const summary = Object.fromEntries(Object.entries(results).map(([target, samples]) => [target, {
  startupMs: summarize(samples.map(sample => sample.readyMs)),
  clickFrameP90Ms: summarize(samples.map(sample => sample.clickFrameP90Ms)),
  sweepFrameP90Ms: summarize(samples.map(sample => sample.sweepFrameP90Ms)),
  distribution: distributionBytes(targets[target]),
}]));

console.log(JSON.stringify({
  samplesPerTarget,
  interactionClicks,
  cpuThrottleRate,
  headed,
  summary,
  samples: results,
}, null, 2));
