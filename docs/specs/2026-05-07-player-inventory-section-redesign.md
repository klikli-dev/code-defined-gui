<!--
SPDX-FileCopyrightText: 2026 klikli-dev

SPDX-License-Identifier: MIT
-->

# Player Inventory Layout Support Redesign

## Summary

Redesign player inventory layout support as four separate pieces:

1. **Structure-only layout fragment**
2. **Reusable default menu binder**
3. **Reusable default screen renderer**
4. **Prefab wrapper API** that combines the above for the common case

This replaces the current direction where `PlayerInventoryLayoutFragment` mixes structure with screen rendering and depends on filter-specific concepts.

The result should be:

- reusable across many GUI families, not just filters
- simple for modders to use
- cleanly separated architecturally
- free of filter-specific leaks

## Goals

### Primary goals

- Make player inventory support reusable across unrelated screens/menus.
- Keep the common usage trivial:
  - drop in a player inventory section
  - bind it
  - render it
  - style it later
- Separate:
  - geometry
  - menu binding
  - client rendering
- Remove filter-specific leaks from the current design.
- Make this the pattern for future prefab GUI sections.

### Secondary goals

- Enable later variants:
  - hotbar-only
  - armor/offhand
  - custom slot renderers
  - alternative binders
- Keep advanced customization possible without complicating the default path.

## Non-goals

- Not a full rewrite of the whole layout framework.
- Not a full redesign of every existing filter abstraction.
- Not a promise of binary/source compatibility; pre-release cleanliness wins.
- Not solving every inventory variant immediately.

## Design principles

### 1. Fragments define structure only

A layout fragment should define nodes and relative geometry, not host-specific rendering logic.

### 2. Binding and rendering are separate concerns

Slot binding belongs to menu-side infrastructure. Widget/slot chrome rendering belongs to screen-side infrastructure.

### 3. The default path should be one object

Most users should consume a single prefab-style API and never need to care about the internal split.

### 4. Generic concepts belong in generic packages

Anything reusable outside filters must not live in `filter/...` packages or use filter-prefixed names.

### 5. Advanced customization should compose

Users should be able to keep:

- same fragment + custom renderer
- same fragment + custom binder
- same prefab + different style

without rewriting the base geometry.

## Problems in the current direction

### Current issues

1. `PlayerInventoryLayoutFragment` is doing more than layout.
2. `FilterLayoutScreenAccess` ties a generic concept to filters.
3. `MenuSlotView` is generic in purpose but filter-specific in name and location.
4. `BuiltinFilterParts.PLAYER_SLOT` and `PLAYER_INVENTORY_BACKGROUND` are not actually filter-specific.
5. Generic player inventory logic currently depends on filter infrastructure.

## Proposed architecture

## A. Structure-only fragment

### Type

`PlayerInventoryLayoutFragment`

### Responsibility

Defines only the player inventory layout nodes.

### Defines

Under its root:

- `background`
- `main.slot_0` ... `main.slot_26`
- `hotbar.slot_0` ... `hotbar.slot_8`

### Must not

- inspect slot metadata
- bind slots
- resolve sprites
- know about screens
- know about menus
- know about filters

## B. Default menu binder

### Interfaces

```java
public interface LayoutMenuBinder {
    void bind(MenuBindingRegistry registry, LayoutMenuBinderHost host);
}
```

```java
public interface LayoutMenuBinderHost {
    Inventory playerInventory();
    Slot addLayoutSlot(Slot slot, SlotRoleKey role, GuiPartKey part, String nodePath);
}
```

### Default implementation

`PlayerInventoryMenuBinder`

### Responsibility

Bind standard player inventory slots to the fragment’s nodes.

### Behavior

When scoped at `player_inventory`:

- binds 27 main slots to `main.slot_*`
- binds 9 hotbar slots to `hotbar.slot_*`

### Must not

- render widgets
- know about filters
- contain layout geometry

## C. Default screen renderer

### Interfaces

```java
public interface LayoutScreenRenderer {
    void registerResolvers(LayoutResolverRegistry registry, LayoutScreenRendererHost host);
}
```

```java
public interface LayoutScreenRendererHost extends GuiHost, LayoutScreen {
    List<? extends LayoutSlotView> layoutSlots();
    GuiSprite resolvedPartSprite(GuiPartKey part, GuiSprite fallback);
    GuiSprite resolvedSlotSprite(LayoutSlotView slotView);
}
```

### Default implementation

`PlayerInventoryScreenRenderer`

### Responsibility

Render default player inventory visuals:

- inventory background
- slot chrome for player inventory slots

### Behavior

When scoped at `player_inventory`:

- resolves `background`
- renders slot chrome for all slot views with player inventory roles

### Must not

- define geometry
- bind slots
- know about filters

## D. Prefab wrapper API

### Type

`PlayerInventorySection`

### Responsibility

Expose one easy API while internally composing:

- fragment
- binder
- renderer

### Recommended shape

```java
public final class PlayerInventorySection {
    public static PlayerInventorySection standard();

    public void define(LayoutGroupBuilder root);
    public void bindMenu(MenuBindingRegistry registry, LayoutMenuBinderHost host);
    public void registerResolvers(LayoutResolverRegistry registry, LayoutScreenRendererHost host);

    public LayoutFragment fragment();
    public LayoutMenuBinder menuBinder();
    public LayoutScreenRenderer screenRenderer();
}
```

### Intent

Most users should only need:

```java
var playerInventory = PlayerInventorySection.standard();
```

and then call it in layout/menu/screen phases.

## Public API usage

## Layout definition

```java
private static final PlayerInventorySection PLAYER_INVENTORY = PlayerInventorySection.standard();

LayoutSpec.create(root -> {
    root.group("player_inventory", inv -> {
        inv.at(27, 123);
        PLAYER_INVENTORY.define(inv);
    });
});
```

## Menu binding

```java
@Override
public void registerBindings(MenuBindingRegistry registry) {
    PLAYER_INVENTORY.bindMenu(registry.scope("player_inventory"), this);
    // other screen-specific bindings
}
```

## Screen rendering

```java
@Override
public void registerResolvers(LayoutResolverRegistry registry) {
    PLAYER_INVENTORY.registerResolvers(registry.scope("player_inventory"), this);
    // other screen-specific resolvers
}
```

This is the target ergonomic path.

## Generic slot metadata

## Problem

Current:

- `filter/core/layout/MenuSlotView`

This is generic in purpose but filter-specific in name and location.

## Replacement

Introduce a generic type:

```java
public record LayoutSlotView(
    Slot slot,
    SlotRoleKey role,
    GuiPartKey part,
    String nodePath
) { }
```

### Recommended package

`gui/layout/LayoutSlotView`

### Responsibility

Bridge:

- bound menu slot
- semantic role
- visual part
- layout node path

### Migration

- replace `MenuSlotView` everywhere
- delete or deprecate `MenuSlotView`

## Generic slot roles

## Problem

`BuiltinSlotRoles` mixes:

- generic roles
- filter-specific roles

## Split

### Generic roles

Create something like:

```java
public final class BuiltinLayoutSlotRoles {
    public static final SlotRoleKey PLAYER_MAIN = ...
    public static final SlotRoleKey PLAYER_HOTBAR = ...
}
```

### Filter-specific roles

Keep separate:

```java
public final class BuiltinFilterSlotRoles {
    public static final SlotRoleKey FILTER_GRID = ...
    public static final SlotRoleKey FILTER_REFERENCE = ...
    public static final SlotRoleKey FILTER_SUMMARY = ...
}
```

### Result

Player inventory infrastructure no longer depends on filter slot roles.

## Generic built-in part keys

## Problem

`BuiltinFilterParts` currently contains generic concepts:

- `PLAYER_SLOT`
- `PLAYER_INVENTORY_BACKGROUND`

## Split

### Generic parts

Introduce:

```java
public final class BuiltinGuiParts {
    public static final GuiPartKey PLAYER_SLOT = ...
    public static final GuiPartKey PLAYER_INVENTORY_BACKGROUND = ...
}
```

Potential later additions:

- `SLOT`
- `HOTBAR_SLOT`
- `CONTAINER_SLOT`
- `PANEL_BACKGROUND`

### Filter parts

Keep only truly filter-specific parts in `BuiltinFilterParts`.

### Result

The player inventory renderer becomes generic.

## Lifecycle integration

## Layout phase

`PlayerInventoryLayoutFragment.define(...)`

- contributes nodes only

## Menu phase

`PlayerInventoryMenuBinder.bind(...)`

- creates slots from resolved layout nodes
- records `LayoutSlotView`

## Screen phase

`PlayerInventoryScreenRenderer.registerResolvers(...)`

- resolves background node
- renders slot chrome based on `LayoutSlotView`

## Internal class/package direction

## Generic layout package

Add:

- `gui/layout/LayoutSlotView`
- `gui/layout/LayoutMenuBinder`
- `gui/layout/LayoutMenuBinderHost`
- `gui/layout/LayoutScreenRenderer`
- `gui/layout/LayoutScreenRendererHost`

## Generic prefab/inventory package

Recommended:

- `gui/layout/inventory/PlayerInventoryLayoutFragment`
- `gui/layout/inventory/PlayerInventoryMenuBinder`
- `gui/layout/inventory/PlayerInventoryScreenRenderer`
- `gui/layout/inventory/PlayerInventorySection`

## Generic style package

Add:

- `gui/style/BuiltinGuiParts`

## Generic role package

Add:

- `gui/layout/BuiltinLayoutSlotRoles`

## Filter package cleanup

Keep only filter-specific concerns in filter packages.

## Migration from current code

## 1. `PlayerInventoryLayoutFragment`

### Current

Structure + client rendering mixed together.

### Change

Refactor to structure-only.

## 2. `FilterLayoutScreenAccess`

### Current

Generic inventory rendering tied to filters.

### Change

Delete it.
Replace with generic renderer host interfaces.

## 3. `MenuSlotView`

### Current

Generic slot metadata in filter namespace.

### Change

Replace with `LayoutSlotView` in generic layout package.

## 4. `BuiltinFilterParts.PLAYER_SLOT`

## 5. `BuiltinFilterParts.PLAYER_INVENTORY_BACKGROUND`

### Current

Generic parts in filter namespace.

### Change

Move to `BuiltinGuiParts`.

## 6. `BuiltinSlotRoles.PLAYER_MAIN` / `PLAYER_HOTBAR`

### Current

Generic roles mixed with filter-specific ones.

### Change

Move to generic role registry.

## Filter integration after redesign

Filter screens/menus should stop special-casing player inventory as a filter concern.

Instead they should consume the generic prefab:

- layout uses `PlayerInventorySection`
- menus use its binder
- screens use its renderer

Then filter-specific logic only handles:

- filter panel
- filter controls
- filter-owned slots
- filter-specific visuals

## Compatibility story

Since this is pre-release:

- prioritize clean API over preserving unstable structure
- optional temporary aliases are fine only if they simplify migration
- long-term target should remove the old leaks fully

Behavioral expectation:

- preserve current default player inventory appearance and slot placement
- preserve style fallback behavior

## Recommended implementation order

### Phase 1 — generic vocabulary

1. Add `LayoutSlotView`
2. Add `BuiltinGuiParts`
3. Add `BuiltinLayoutSlotRoles`
4. Update existing code to use them

### Phase 2 — split the player inventory implementation

5. Refactor `PlayerInventoryLayoutFragment` to structure-only
6. Add `PlayerInventoryMenuBinder`
7. Add `PlayerInventoryScreenRenderer`

### Phase 3 — prefab API

8. Add `PlayerInventorySection.standard()`
9. Update filter screens/menus to use it

### Phase 4 — remove leaks

10. Remove `FilterLayoutScreenAccess`
11. Remove `MenuSlotView`
12. Remove generic player inventory keys from `BuiltinFilterParts`
13. Split generic and filter-specific slot roles fully

### Phase 5 — polish

14. Update examples/docs
15. Use the same pattern for future reusable sections

## Why this option is best

Compared to the other approaches, this is the cleanest because:

- users get the easiest API
- geometry stays geometry
- rendering stays rendering
- binding stays binding
- reusable infrastructure no longer depends on filter-specific hosts
- future prefab sections can follow the same pattern

## Final recommendation

Adopt option 3 fully:

- **structure-only fragment**
- **default binder**
- **default renderer**
- **single prefab wrapper**
- **remove all filter-specific leaks**

This is the cleanest long-term architecture and the best modder-facing API.
