<!--
SPDX-FileCopyrightText: 2026 klikli-dev

SPDX-License-Identifier: MIT
-->

# Using the library

Code Defined GUI is now documented as a layout-driven GUI framework.

## Main idea

- define a `LayoutSpec`
- bind menu slots against named layout nodes
- resolve screen widgets against named layout nodes
- pull visuals from `GuiStyleRegistry` through `StyleContext`
- use reusable widgets inside that layout flow

## Core types

### Layout definition and lookup

- `api.layout.LayoutSpec` - layout definition entrypoint
- `api.layout.LayoutGroupBuilder` / `LayoutNodeBuilder` - builder types for groups and nodes
- `api.layout.LayoutNodeView` - resolved node coordinates and optional size
- `api.layout.GuiLayoutKey` - layout identifier value object

### Menu-side composition

- `api.layout.LayoutMenuView` - menu contract for a layout-backed menu
- `api.layout.MenuLayoutController` - executes menu bindings
- `api.layout.MenuBindingRegistry` / `MenuBindingContext` - bind slots to nodes

### Screen-side composition

- `api.layout.LayoutScreenView` - screen contract for layout-backed widget resolution
- `api.layout.ScreenLayoutController` - executes screen resolvers
- `api.layout.LayoutResolverRegistry` / `LayoutResolveContext` - resolve widgets against nodes

### Screen host plumbing

- `api.screen.GuiHost` - GUI-relative host contract for screens
- `api.screen.GuiRootWidget` - root widget that owns and resyncs child widgets
- `api.screen.GuiSyncable` - optional sync hook for widgets that follow host bounds

### Styling and visuals

- `api.style.GuiStyle`, `GuiStyleKey`, `GuiPartKey`
- `api.style.GuiStyleRegistry`
- `api.style.StyleContext`
- `api.style.GuiStyleProperties`
- `api.texture.GuiSprite` / `GuiSprites`

### Generic widgets

- `api.widget.GuiBackgroundWidget`
- `api.widget.GuiSpriteWidget`
- `api.widget.GuiTextWidget`
- `api.widget.IconButtonWidget`
- `api.widget.HorizontalSeparatorWidget` / `VerticalSeparatorWidget`

## Defining a layout

Layouts are built as named groups and nodes.

```java
LayoutSpec layout = LayoutSpec.create(root -> {
    root.group("main", main -> {
        main.node("panel").at(0, 0).size(176, 90);
        main.node("input").at(8, 18).size(18, 18);
        main.node("output").at(150, 18).size(18, 18);
        main.node("title").at(8, 6).size(160, 8);
    });

    root.group("player_inventory", inv -> {
        inv.at(8, 108);
        // define player inventory nodes here
    });
});
```

Each node has:

- a stable id
- resolved `x()` / `y()` coordinates
- optional `width()` / `height()`

Lookups use dot paths such as `main.output` or `player_inventory.hotbar.slot_0`.

When a resolver needs size, prefer `widthOrThrow()` / `heightOrThrow()`.

## Menu-side binding

Menus bind slots to layout nodes through `LayoutMenuView` and `MenuLayoutController`.

```java
public final class ExampleMenu extends AbstractContainerMenu implements LayoutMenuView {
    private final LayoutSpec layout;
    private final MenuLayoutController layoutController;

    public ExampleMenu(int containerId, Inventory inventory) {
        super(MY_MENU_TYPE.get(), containerId);
        this.layout = ExampleLayouts.MAIN;
        this.layoutController = new MenuLayoutController(this);
        this.layoutController.bind();
    }

    @Override
    public LayoutSpec layoutSpec() {
        return this.layout;
    }

    @Override
    public void registerBindings(MenuBindingRegistry registry) {
        registry.bind("main.input", ctx -> this.addSlot(new Slot(inputContainer, 0, ctx.node().x(), ctx.node().y())));
        registry.bind("main.output", ctx -> this.addSlot(new Slot(resultContainer, 0, ctx.node().x(), ctx.node().y())));
    }
}
```

Use `bind(...)` for the primary binding at a node and `add(...)` for extra layered slot work.

## Screen controller setup

Screen-side layout composition uses `GuiHost` and `GuiRootWidget` together with `ScreenLayoutController`.

```java
public abstract class ExampleScreen extends AbstractContainerScreen<ExampleMenu> implements GuiHost, LayoutScreenView {
    protected final GuiRootWidget root;
    private final ScreenLayoutController layoutController;

    protected ExampleScreen(ExampleMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.root = new GuiRootWidget(this);
        this.layoutController = new ScreenLayoutController(this, this, this.root, new GuiStyleContext(GuiStyleRegistry.get(menu.styleKey())));
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(this.root);
        this.root.clearChildren();
        this.layoutController.init();
        this.root.syncWithHost();
    }
}
```

## Screen-side resolving

Screens resolve widgets against layout nodes through `LayoutScreenView`.

```java
@Override
public void registerResolvers(LayoutResolverRegistry registry) {
    registry.resolve("main.panel", ctx -> ctx.addWidget(new GuiBackgroundWidget(
            this,
            ctx.node().x(),
            ctx.node().y(),
            ctx.node().widthOrThrow(),
            ctx.node().heightOrThrow(),
            ctx.style().sprite(MY_PARTS.PANEL, GuiSprites.GUI_BACKGROUND)
    )));

    registry.resolve("main.title", ctx -> ctx.addWidget(new GuiTextWidget(
            ctx.node().x(),
            ctx.node().y(),
            () -> this.title,
            () -> ctx.style().textColor(MY_PARTS.TITLE, 0xFF000000),
            false
    )));

    registry.add("main.output", 10, ctx -> ctx.addWidget(new GuiSpriteWidget(
            ctx.node().x() - 1,
            ctx.node().y() - 1,
            ctx.style().sprite(MY_PARTS.OUTPUT_SLOT, GuiSprites.INVENTORY_SLOT)
    )));
}
```

`resolve(...)` replaces the primary resolver for a node.

`add(...)` appends additional resolvers. Screen resolvers support priorities, so lower priorities can render backgrounds before higher-priority overlays.

`scope("main")` is useful when several bindings or resolvers share the same prefix.

## Styling inside layouts

`LayoutResolveContext.style()` exposes the active `StyleContext`.

Common helpers include:

- `sprite(...)`
- `hoverSprite(...)`
- `pressedSprite(...)`
- `onSprite(...)` / `offSprite(...)`
- `textColor(...)`
- `color(...)`

That keeps visuals data-driven instead of hardcoded into the screen class.

## Generic widgets

The reusable widget package remains useful inside the layout-driven model.

Typical building blocks are:

- `GuiBackgroundWidget` for panels and backgrounds
- `GuiSpriteWidget` for icons and slot frames
- `GuiTextWidget` for titles and labels
- `IconButtonWidget` for clickable icon buttons
- separator widgets for dividers

## Package guidance

- Import generic building blocks from `api.*`.
- Use `premade.filter.*` only when you are intentionally building on the shipped filter feature set.
- Do not import `internal.*`.

## Reference implementations in this repo

Start with the dedicated example namespace for the smallest layout-first reference path:

- `example.ExampleLayouts`
- `example.ExampleMenu`
- `example.ExampleScreen`

The stock filter screens remain the most complete production-style end-to-end examples:

- `premade.filter.ListFilterScreen`
- `premade.filter.AttributeFilterScreen`
- `premade.filter.core.FilterMenu`
