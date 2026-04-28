<!--
SPDX-FileCopyrightText: 2026 klikli-dev

SPDX-License-Identifier: MIT
-->

# GuiTexture render-state delegation design

## Summary

Replace direct `GuiGraphicsExtractor.blitSprite(..., guiTexture.sprite(), ...)` calls with `GuiTexture.extractRenderState(...)` so `GuiTexture` owns how its sprite render state is emitted.

## Scope

- Update current render sites that use `GuiTextures` constants and still call `blitSprite` directly with `.sprite()`.
- Also update other direct `GuiTexture` sprite blits in the same area, including instance-backed textures such as `IconButtonWidget`'s `icon` field.
- Keep all widget positions, dimensions, text rendering, item rendering, hover logic, and click behavior unchanged.
- Do not change unrelated rendering code that does not go through `GuiTexture`.

## Approach options considered

### Option 1: migrate only `GuiTextures.*` constant call sites

- Smallest diff.
- Matches the initial narrow request.
- Leaves mixed rendering patterns in widgets that already depend on `GuiTexture`.

### Option 2: migrate all direct `GuiTexture.sprite()` blits in affected widgets

- Slightly broader, but still local and low risk.
- Makes widgets consistently delegate sprite render-state extraction to `GuiTexture`.
- Reuses the existing pattern already present in `TextureWidget`.

Chosen option: **Option 2**.

## Detailed design

Each affected widget will replace this pattern:

```java
graphics.blitSprite(RenderPipelines.GUI_TEXTURED, texture.sprite(), x, y, width, height);
```

with this pattern:

```java
texture.extractRenderState(graphics, x, y, width, height);
```

This keeps the render pipeline selection and tint handling centralized in `GuiTexture`.

## Affected areas

- `IconButtonWidget`
  - Delegate both the background `GuiTextures.*` selection and the instance `icon` texture through `extractRenderState(...)`.
- `FilterIndicatorWidget`
  - Delegate the selected on/off texture through `extractRenderState(...)`.
- `AttributeSelectionWidget`
  - Delegate the background frame texture through `extractRenderState(...)`.
- `AttributeRuleSummaryWidget`
  - Delegate the summary background texture through `extractRenderState(...)`.

`TextureWidget` already follows the target pattern and serves as the reference behavior.

## Risks and mitigations

- Risk: accidental coordinate or sizing changes while rewriting call sites.
  - Mitigation: preserve the exact existing `x`, `y`, `width`, and `height` expressions.
- Risk: unused imports after removing direct `blitSprite` usage.
  - Mitigation: clean up imports in edited files.

## Validation

- Run `./gradlew.bat compileJava` from the repository root.
- If compilation fails, fix only issues caused by this migration.
