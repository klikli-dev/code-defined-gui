<!--
SPDX-FileCopyrightText: 2026 klikli-dev

SPDX-License-Identifier: MIT
-->

# Styling premade GUIs

CDG premade GUIs use a single generic styling system.

## Main idea

- register one `GuiStyle` under a `GuiStyleKey`
- style values are looked up by `GuiPartKey + GuiStyleProperty`
- screens and widgets choose which part keys they consume
- mods choose which `GuiStyleKey` a screen layout should use

There is only one style registry: `gui.style.GuiStyleRegistry`.
Slots are not special - they use the same part/property lookup model as every other styled part.

## Key classes to check out

- `gui.style.GuiStyle` - the style sheet itself
- `gui.style.GuiStyleRegistry` - style registration/lookup
- `gui.style.GuiStyleKey` - style ids
- `gui.style.GuiPartKey` - styleable part ids
- `gui.style.GuiStyleProperty` and `GuiStyleProperties` - typed properties
- `filter.core.FilterItem` - where a style key is chosen for a layout
- `filter.core.FilterMenu` - where the chosen style key is carried to the client screen
- `filter.core.layout.BuiltinFilterLayouts` - built-in filter layout ids
- `filter.core.layout.BuiltinFilterParts` - built-in styleable parts for filter screens
- `gui.filter.AbstractFilterScreen` - how style values are consumed by the stock screens
- `gui.filter.ListFilterScreen` and `gui.filter.AttributeFilterScreen` - examples of built-in screens using parts directly

## Built-in filter parts

Some useful built-in parts are:

- `BuiltinFilterParts.PLAYER_SLOT`
- `BuiltinFilterParts.FILTER_SLOT`
- `BuiltinFilterParts.PLAYER_INVENTORY_BACKGROUND`
- `BuiltinFilterParts.BUTTON`
- `BuiltinFilterParts.INDICATOR`
- `BuiltinFilterParts.LIST_PANEL`, `LIST_TOP_BAR`, `LIST_TITLE`
- `BuiltinFilterParts.ATTRIBUTE_PANEL`, `ATTRIBUTE_TOP_BAR`, `ATTRIBUTE_TITLE`
- `BuiltinFilterParts.ATTRIBUTE_SELECTION`, `ATTRIBUTE_SUMMARY`

Common properties live in `GuiStyleProperties`, for example:

- `SPRITE`
- `HOVER_SPRITE`
- `PRESSED_SPRITE`
- `ON_SPRITE`
- `OFF_SPRITE`
- `OFFSET_X`, `OFFSET_Y`
- `TEXT_COLOR`
- `COLOR`

## Example style registration

```java
GuiStyleRegistry.register(MY_STYLE, GuiStyle.builder()
    .set(BuiltinFilterParts.PLAYER_INVENTORY_BACKGROUND, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(0xFFC2AA88))
    .set(BuiltinFilterParts.PLAYER_SLOT, GuiStyleProperties.SPRITE, GuiSprites.INVENTORY_SLOT.tinted(0xFFB8946A))
    .set(BuiltinFilterParts.PLAYER_SLOT, GuiStyleProperties.OFFSET_X, 1)
    .set(BuiltinFilterParts.PLAYER_SLOT, GuiStyleProperties.OFFSET_Y, 1)
    .set(BuiltinFilterParts.BUTTON, GuiStyleProperties.SPRITE, GuiSprites.FILTER_BUTTON.tinted(0xFFB78F63))
    .set(BuiltinFilterParts.BUTTON, GuiStyleProperties.HOVER_SPRITE, GuiSprites.FILTER_BUTTON_HOVER.tinted(0xFFC89E70))
    .set(BuiltinFilterParts.BUTTON, GuiStyleProperties.PRESSED_SPRITE, GuiSprites.FILTER_BUTTON_DOWN.tinted(0xFFB78F63))
    .build());
```

If you want to tint the player inventory background and player inventory slots in a downstream mod,
those are the two part keys you usually want:

- `BuiltinFilterParts.PLAYER_INVENTORY_BACKGROUND`
- `BuiltinFilterParts.PLAYER_SLOT`

## Choosing styles per layout

`FilterItem` exposes:

```java
public GuiStyleKey guiStyleKey(ItemStack stack, GuiLayoutKey layout)
```

This lets a mod choose different styles for different built-in layouts.

Example:

```java
@Override
public GuiStyleKey guiStyleKey(ItemStack stack, GuiLayoutKey layout) {
    if (layout.equals(BuiltinFilterLayouts.ATTRIBUTE_FILTER)) {
        return MY_ATTRIBUTE_STYLE;
    }

    return MY_LIST_STYLE;
}
```

## Future screen subclassing

The style system only controls visuals.
Screen subclasses still control behavior and layout.

That means you can still subclass a CDG screen later to:

- add widgets
- move widgets
- change behavior
- introduce your own additional `GuiPartKey`s

without changing the style API.

## Important menu payload note

If you override `FilterItem.writeMenuData(...)`, call `super.writeMenuData(...)` first.
The stock client menu constructors expect the hand and chosen style key to be written first and in that order.
