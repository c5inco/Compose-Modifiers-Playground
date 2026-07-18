export const targets = {
  js: "/jsApp/build/dist/js/productionExecutable/",
  wasm: "/wasmApp/build/dist/wasmJs/productionExecutable/",
};

// Canvas coordinates shared by the smoke suite and the benchmark, valid for a
// 1280x720 viewport on the default Rainbow template.
export const points = {
  templateChooser: { x: 1080, y: 28 },
  templateItem: { x: 1080, y: 92 },
  modifierToggle: { x: 1220, y: 292 },
  resetTemplate: { x: 1252, y: 28 },
};

// Pointer sweep across the modifiers panel used by the benchmark to exercise
// continuous hit-testing and hover invalidation.
export const sweep = {
  from: { x: 1100, y: 150 },
  to: { x: 1250, y: 650 },
  steps: 60,
};
