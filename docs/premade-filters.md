<!--
SPDX-FileCopyrightText: 2026 klikli-dev

SPDX-License-Identifier: MIT
-->

# Premade filters

CDG ships with a supported premade filter feature set on top of the generic layout and style APIs.

## What is included

### List filter

- `ListFilterItem`
- `ListFilterMenu`
- `ListFilterDefinition`
- `ListFilterState`
- `ListFilterMode`
- `ListFilterConfig`
- `ListFilterScreen`

### Attribute filter

- `AttributeFilterItem`
- `AttributeFilterMenu`
- `AttributeFilterDefinition`
- `AttributeFilterState`
- `AttributeFilterMode`
- `AttributeFilterConfig`
- `AttributeFilterScreen`

## Core extension points

- `FilterItem<S>` — item entrypoint that opens the menu and chooses the style key
- `FilterDefinition<S>` — summary/matching behavior for a filter state
- `FilterMenu` — base menu with ghost-slot handling, layout metadata, and layout slot tracking
- `BuiltinFilterLayouts` — built-in layout ids such as `LIST_FILTER` and `ATTRIBUTE_FILTER`
- `BuiltinFilterParts` — built-in style parts consumed by the stock filter screens

## Choosing layouts and styles

Each filter item has a default layout key and can choose a GUI style per opened layout.

The style customization point is:

```java
public GuiStyleKey guiStyleKey(ItemStack stack, GuiLayoutKey layout)
```

That lets a downstream mod return different styles for different premade layouts.

## Built-in screens

`CodeDefinedGuiClient` registers the stock client screens for the built-in list and attribute filter menus.

If you create your own filter menu or screen subclass, register your own menu screen factory the same way you would for any custom NeoForge menu.

## Layout and style helpers

Use these constants when working with the shipped filter feature:

- `BuiltinFilterLayouts.LIST_FILTER`
- `BuiltinFilterLayouts.ATTRIBUTE_FILTER`
- `BuiltinFilterParts.*`
- `BuiltinFilterSlotRoles.*`

Player inventory visuals on the stock filter screens use generic GUI parts such as `BuiltinGuiParts.PLAYER_SLOT`. Filter-owned ghost slots use `BuiltinFilterParts.FILTER_SLOT` by default.

## Attribute registry lifecycle

Attribute filters use `ItemAttributes` as a registry of available attribute types.

- register custom `ItemAttributeType` instances during bootstrap
- duplicate ids are rejected
- registration is frozen after `ItemAttributes.bootstrap()`

This registry is intentionally stricter than the old overwrite-friendly behavior.

## JEI integration

When [JEI](https://www.curseforge.com/minecraft/mc-mods/jei) is installed, you can drag item ingredients from the JEI ingredient list onto the ghost slots of the stock filter screens.

- The list filter accepts an item on any of its filter grid slots.
- The attribute filter accepts an item on its reference slot. The summary slot is display-only and does not accept drops.
- The dropped item is stored as a single-count ghost copy, the same as placing an item by clicking.

The drop is applied locally right away and then sent to the server, which validates that the player's open container is the filter menu and that the target slot is an interactive ghost slot before updating the draft filter.

JEI is an optional dependency and is only referenced by the client-side `internal.integration.jei` package, so CDG loads without it.

EMI is not integrated yet.

## Menu payload note

If you override `FilterItem.writeMenuData(...)`, call `super.writeMenuData(...)` first.

The built-in client menu constructors expect the hand and chosen style key to be written first and in that order.

## Modes and serialized names

Built-in filter modes are:

- list filter: `ALLOW`, `DENY`
- attribute filter: `MATCH_ANY`, `MATCH_ALL`, `DENY`

Serialized names are snake_case such as `allow`, `deny`, `match_any`, and `match_all`.
