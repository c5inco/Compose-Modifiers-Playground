import { defineConfig } from "@playwright/test";

// The JS/Wasm tradeoff is engine-dependent, so the smoke suite can run against
// additional engines: WEB_BROWSERS=chromium,webkit,firefox npm test
// (after `npx playwright install` for the extra browsers).
const browsers = (process.env.WEB_BROWSERS ?? "chromium")
  .split(",")
  .map(name => name.trim())
  .filter(Boolean);

export default defineConfig({
  testDir: "./tests",
  timeout: 30_000,
  fullyParallel: false,
  workers: 1,
  reporter: "line",
  use: {
    baseURL: "http://127.0.0.1:4173",
    headless: true,
    viewport: { width: 1280, height: 720 },
  },
  projects: browsers.map(browserName => ({ name: browserName, use: { browserName } })),
  webServer: {
    command: "node serve.mjs",
    url: "http://127.0.0.1:4173/jsApp/build/dist/js/productionExecutable/",
    reuseExistingServer: false,
  },
});
