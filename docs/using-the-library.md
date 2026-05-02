<!--
SPDX-FileCopyrightText: 2026 klikli-dev

SPDX-License-Identifier: MIT
-->

# Using the library

Code Defined GUI is meant to feel close to vanilla Minecraft screen code.

## Main idea

- create a screen
- instantiate widgets directly
- position them with fixed GUI-relative coordinates
- compose the background from reusable widgets and sprites

## Core types

### Generic GUI infrastructure

- `gui.core.GuiHost` - host contract for a screen
- `gui.core.GuiRootWidget` - root that manages child widgets
- `gui.texture.GuiSprite` / `gui.texture.GuiSprites` - reusable sprite references

### Generic widgets

- `gui.widget.GuiBackgroundWidget` - renders a full or partial GUI background
- `gui.widget.GuiSpriteWidget` - renders a sprite at a fixed position
- `gui.widget.FrameWidget` - simple framed or beveled regions
- `gui.widget.IconButtonWidget` - clickable icon buttons
- `gui.widget.HorizontalSeparatorWidget` / `VerticalSeparatorWidget` - dividers

### Filter-specific widgets

These are intentionally separated from the generic widget package:

- `gui.filter.widget.FilterIndicatorWidget`
- `gui.filter.widget.AttributeSelectionWidget`
- `gui.filter.widget.AttributeRuleSummaryWidget`

## Positioning widgets

Screens still use fixed positions.

`GuiHost` now provides small helpers for GUI-relative coordinates:

```java
this.guiX(8);
this.guiY(18);
```

That means you can write:

```java
this.root.addChild(new GuiSpriteWidget(this.guiX(8), this.guiY(18), GuiSprites.INVENTORY_SLOT));
this.root.addChild(new FrameWidget(this.guiX(120), this.guiY(18), 40, 24));
```

instead of repeating `leftPos + ...` and `topPos + ...` everywhere.

## Typical screen flow

1. implement `GuiHost` on your screen
2. create a `GuiRootWidget`
3. add the root as a renderable widget
4. clear and rebuild child widgets in `init()`
5. place widgets with `guiX(...)` and `guiY(...)`

Example:

```java
this.root.clearChildren();
this.root.addChild(new GuiBackgroundWidget(this));
this.root.addChild(new GuiSpriteWidget(this.guiX(8), this.guiY(18), GuiSprites.INVENTORY_SLOT));
this.root.addChild(new IconButtonWidget(this.guiX(152), this.guiY(75), icon, message, onPress));
this.root.syncBoundsToHost();
```

## Package guidance

- Use `gui.widget` for reusable building blocks.
- Use `gui.filter.widget` only when you are building filter UIs.
- Treat `GuiRootWidget` as composition infrastructure, not as a layout system.

## Good usage pattern

- keep widget positions fixed and explicit
- use sprites and frames to assemble the background
- keep feature-specific widgets in feature packages
- reuse generic widgets for anything that is not filter-specific

## Examples in this repo

- `example/screen/TestScreen.java` shows a small composed screen
- `gui/filter/ListFilterScreen.java` shows a menu-backed screen with fixed-position widgets
- `gui/filter/AttributeFilterScreen.java` shows a more complex feature screen using both generic and filter-specific widgets
