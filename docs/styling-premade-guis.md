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

- `BuiltinGuiParts.PLAYER_SLOT`
- `BuiltinGuiParts.PLAYER_INVENTORY_BACKGROUND`
- `BuiltinGuiParts.PLAYER_INVENTORY_LABEL`
- `BuiltinFilterParts.FILTER_SLOT`
- `BuiltinFilterParts.BUTTON`
- `BuiltinFilterParts.INDICATOR`
- `BuiltinFilterParts.LIST_PANEL`, `LIST_TOP_BAR`, `LIST_TITLE`
- `BuiltinFilterParts.ATTRIBUTE_PANEL`, `ATTRIBUTE_TOP_BAR`, `ATTRIBUTE_TITLE`
- `BuiltinFilterParts.ATTRIBUTE_SELECTION`, `ATTRIBUTE_SELECTION_HEADER`, `ATTRIBUTE_SUMMARY`

`ATTRIBUTE_SELECTION` styles the visible selected-entry text, while `ATTRIBUTE_SELECTION_HEADER`
styles the selection tooltip title/header text.

Common properties live in `GuiStyleProperties`, for example:

- `SPRITE`
- `HOVER_SPRITE`
- `PRESSED_SPRITE`
- `ON_SPRITE`
- `OFF_SPRITE`
- `TEXT_COLOR`
- `COLOR`

## Example style registration

Register styles on the client before a built-in CDG screen is opened:

```java
@Mod(value = ExampleMod.MODID, dist = Dist.CLIENT)
public final class ExampleModClient {
    public ExampleModClient(IEventBus modEventBus) {
        modEventBus.addListener(this::onClientSetup);
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> GuiStyleRegistry.register(MY_STYLE, GuiStyle.builder()
                .set(BuiltinGuiParts.PLAYER_INVENTORY_BACKGROUND, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(0xFFC2AA88))
                .set(BuiltinGuiParts.PLAYER_SLOT, GuiStyleProperties.SPRITE, GuiSprites.INVENTORY_SLOT.tinted(0xFFB8946A))
                .set(BuiltinGuiParts.PLAYER_INVENTORY_LABEL, GuiStyleProperties.TEXT_COLOR, 0xFF3E2A1A)
                .set(BuiltinFilterParts.LIST_TOP_BAR, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(0xFF7A5A3A))
                .set(BuiltinFilterParts.LIST_TITLE, GuiStyleProperties.TEXT_COLOR, 0xFFF8E7C5)
                .set(BuiltinFilterParts.BUTTON, GuiStyleProperties.SPRITE, GuiSprites.FILTER_BUTTON.tinted(0xFFB78F63))
                .set(BuiltinFilterParts.BUTTON, GuiStyleProperties.HOVER_SPRITE, GuiSprites.FILTER_BUTTON_HOVER.tinted(0xFFC89E70))
                .set(BuiltinFilterParts.BUTTON, GuiStyleProperties.PRESSED_SPRITE, GuiSprites.FILTER_BUTTON_DOWN.tinted(0xFFB78F63))
                .build()));
    }
}
```

If you want to tint the player inventory background and player inventory slots in a downstream mod,
those are the two part keys you usually want:

- `BuiltinGuiParts.PLAYER_INVENTORY_BACKGROUND`
- `BuiltinGuiParts.PLAYER_SLOT`

## Choosing styles per layout

Registering a style only makes it available under a `GuiStyleKey`.
It does not automatically replace styles on every CDG screen.

For the stock filter screens, the selected style is still handed over per opened screen/menu:

1. your mod registers a `GuiStyle` under a `GuiStyleKey`
2. your `FilterItem` returns the chosen `GuiStyleKey` for the opened layout
3. `FilterMenu` carries that key to the client screen
4. the screen resolves the concrete style from `GuiStyleRegistry`

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

That is enough to restyle the stock list filter screen without subclassing it.
The built-in filter screens resolve backgrounds, buttons, indicators, titles, player inventory labels,
and attribute filter text through the same `GuiStyleRegistry` lookup.

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
