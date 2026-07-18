# JS and Wasm browser comparison

Build both production targets from the repository root:

```bash
./gradlew :jsApp:jsBrowserDistribution :wasmApp:wasmJsBrowserDistribution
```

Install the browser test dependency once, then run the smoke journeys and benchmark:

```bash
cd web-tests
npm ci
npx playwright install chromium
npm test
npm run benchmark
```

## Smoke suite

Runs the same canvas interactions against both targets on Chromium by default.
Because the JS/Wasm tradeoff is engine-dependent, it can also run on other
engines after `npx playwright install webkit firefox`:

```bash
WEB_BROWSERS=chromium,webkit,firefox npm test
```

## Benchmark

The benchmark alternates target order, uses a fresh browser context for every
sample, and runs one discarded warm-up sample per target that also verifies the
shared click coordinate (see `targets.mjs`) still repaints the canvas — so a
layout drift fails loudly instead of silently timing clicks on empty space.

Reported metrics:

- **startupMs** — cold navigation to the second animation frame after Compose
  initialization.
- **clickFrameP90Ms / sweepFrameP90Ms** — p90 requestAnimationFrame delta,
  recorded in-page during a 21-click visibility-toggle burst and a pointer
  sweep across the modifiers panel. Measuring frame durations in the page keeps
  Playwright protocol round trips out of the numbers.
- **distribution** — production-distribution size excluding source maps, both
  raw and gzipped (compressed size is what real delivery pays, and JS and Wasm
  compress very differently).

Raw per-sample values are included in the JSON output for later inspection.

Environment knobs:

- `WEB_BENCHMARK_SAMPLES` (default 10) and `WEB_BENCHMARK_CLICKS` (default 21).
- `WEB_BENCHMARK_CPU_THROTTLE` (default 4) — CDP CPU throttling rate, so both
  targets are measured at mid-range-hardware speed instead of vanishing into a
  fast dev machine's frame budget. Set to 1 to disable.
- `WEB_BENCHMARK_HEADED=1` — run headed. Headless Chromium rasterizes in
  software, which can change the relative JS/Wasm ordering for a Skia canvas
  app; use a headed run to confirm conclusions on a real GPU.

The benchmark is Chromium-only (it relies on CDP for throttling). These are
local comparative measurements, not stable pass/fail thresholds.
