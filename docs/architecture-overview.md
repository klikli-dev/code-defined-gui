<!--
SPDX-FileCopyrightText: 2026 klikli-dev

SPDX-License-Identifier: MIT
-->

# Architecture overview

Code Defined GUI is now organized as a layout-driven GUI framework with a small generic API surface and a higher-level shipped filter feature set.

## Package map

### `com.klikli_dev.codedefinedgui.api.*`

Supported generic API for downstream mods.

- `api.layout`
  - `LayoutSpec`, `LayoutGroupBuilder`, `LayoutNodeBuilder`
  - `LayoutMenuView` + `MenuLayoutController`
  - `LayoutScreenView` + `ScreenLayoutController`
  - `MenuBindingRegistry`, `LayoutResolverRegistry`
  - `GuiLayoutKey`, `SlotRoleKey`, built-in slot-role constants
- `api.screen`
  - `GuiHost`, `GuiRootWidget`, `GuiSyncable`
- `api.style`
  - `GuiStyle`, `GuiStyleKey`, `GuiPartKey`
  - `GuiStyleRegistry`, `StyleContext`, `GuiStyleProperties`
  - `BuiltinGuiParts`, `BuiltinGuiStyles`
- `api.texture`
  - `GuiSprite`, `GuiSprites`
- `api.widget`
  - reusable widgets such as `GuiBackgroundWidget`, `GuiSpriteWidget`, `GuiTextWidget`, `IconButtonWidget`, separators, and slot helpers

### `com.klikli_dev.codedefinedgui.premade.filter.*`

Supported premade feature layer built on the generic API.

- `premade.filter.core`
  - `FilterItem`, `FilterDefinition`, `FilterMenu`, filter state types
- `premade.filter.list`
  - built-in list filter item, menu, mode, config, and state
- `premade.filter.attribute`
  - built-in attribute filter item, menu, mode, config, state, and attribute registry/types
- `premade.filter.core.layout`
  - `BuiltinFilterLayouts`, `BuiltinFilterParts`, `BuiltinFilterSlotRoles`
- `premade.filter`
  - stock list and attribute filter screens

### `com.klikli_dev.codedefinedgui.internal.*`

Not supported as public API.

This includes internal renderers, bootstrap wiring, registries, networking, commands, and internal inventory layout helpers.

Downstream mods should not import `internal.*`.

## Primary runtime model

The intended flow is:

1. define a `LayoutSpec`
2. bind menu slots against named layout nodes
3. resolve widgets on the screen side against named layout nodes
4. pull visuals from `GuiStyleRegistry` through `StyleContext`

This keeps layout structure, behavior, and visuals separate.

## Layout model

`LayoutSpec` is a tree of named groups and nodes.

- groups let you build nested structure such as `main.top_bar`
- nodes carry resolved positions and optional sizes
- resolved node lookups use dot-separated paths such as `main.filter_area.reset`

`LayoutNodeView.width()` and `height()` are nullable. When a resolver or binding requires size, use `widthOrThrow()` / `heightOrThrow()`.

## Menu and screen composition

### Menu side

- implement `LayoutMenuView`
- return a `LayoutSpec`
- register slot bindings in `registerBindings(MenuBindingRegistry)`
- delegate execution to `MenuLayoutController.bind()`

### Screen side

- implement `LayoutScreenView`
- implement `GuiHost`
- own a `GuiRootWidget`
- register widget resolvers in `registerResolvers(LayoutResolverRegistry)`
- delegate execution to `ScreenLayoutController.init()`
- use `resolve(...)` for the primary widget at a node and `add(...)` for extra layered widgets

Screen resolvers support priorities. Lower priorities run first.

## Styling model

CDG uses a single global style registry.

- register a `GuiStyle` under a `GuiStyleKey`
- widgets and screens look up values by `GuiPartKey + GuiStyleProperty`
- premade filters use the same registry as generic widgets

There is no separate slot style registry.

## Stability policy

- `api.*` is the generic modder-facing surface
- `premade.*` is supported for the shipped filter feature set
- `internal.*` may change freely and is not part of the supported contract

## Reference code

The repo still contains debug/reference classes, but they are internal-only:

- `com.klikli_dev.codedefinedgui.internal.screen.TestScreen`
- `com.klikli_dev.codedefinedgui.internal.command.CdgCommand`
- `com.klikli_dev.codedefinedgui.internal.network.OpenTestScreenMessage`

Treat them as implementation reference, not supported API.
