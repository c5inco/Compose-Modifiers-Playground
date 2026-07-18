import { expect, test } from "@playwright/test";
import { points, targets } from "../targets.mjs";

for (const [target, path] of Object.entries(targets)) {
  test.describe(`${target} browser target`, () => {
    test("loads and completes basic canvas journeys", async ({ page }) => {
      const errors = [];
      page.on("pageerror", error => errors.push(error.message));
      page.on("console", message => {
        if (message.type() === "error") errors.push(message.text());
      });

      await page.goto(path);
      await page.waitForFunction(() => document.documentElement.dataset.composeReadyMs);

      const canvas = page.locator("#ComposeTarget");
      await expect(canvas).toBeVisible();
      await expect(canvas).toHaveJSProperty("width", 1280);
      await expect(canvas).toHaveJSProperty("height", 720);
      await expect(page.locator("#loadingIndicator")).toBeHidden();

      const initial = await page.screenshot();

      // Open the template chooser, select another template, and verify a repaint.
      await page.mouse.click(points.templateChooser.x, points.templateChooser.y);
      await page.waitForTimeout(150);
      const chooserOpen = await page.screenshot();
      expect(chooserOpen.equals(initial)).toBe(false);
      await page.mouse.click(points.templateItem.x, points.templateItem.y);
      await page.waitForTimeout(150);
      const templateChanged = await page.screenshot();
      expect(templateChanged.equals(initial)).toBe(false);

      // Toggle the first modifier visibility, then reset the template.
      await page.mouse.click(points.modifierToggle.x, points.modifierToggle.y);
      await page.waitForTimeout(150);
      const modifierHidden = await page.screenshot();
      expect(modifierHidden.equals(templateChanged)).toBe(false);
      await page.mouse.click(points.resetTemplate.x, points.resetTemplate.y);
      await page.waitForTimeout(150);
      const reset = await page.screenshot();
      expect(reset.equals(modifierHidden)).toBe(false);

      expect(errors).toEqual([]);
    });
  });
}
