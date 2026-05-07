<!--
SPDX-FileCopyrightText: 2026 klikli-dev

SPDX-License-Identifier: MIT
-->

# Layout-driven quick start

The smallest layout-first CDG flow is:

1. define a `LayoutSpec`
2. implement a menu with `LayoutMenuView`
3. implement a screen with `LayoutScreenView` and `GuiHost`
4. let the controllers do the plumbing

## Reference example

See:

- `example.ExampleLayouts`
- `example.ExampleMenu`
- `example.ExampleScreen`

## What it demonstrates

- named layout nodes like `main.input` and `main.output`
- menu-side slot binding through `MenuLayoutController`
- screen-side widget resolution through `ScreenLayoutController`
- `GuiRootWidget` lifecycle and host-relative positioning

## Minimal flow

```java
LayoutSpec layout = ExampleLayouts.BASIC_SCREEN;
```

```java
public class ExampleMenu extends AbstractContainerMenu implements LayoutMenuView {
    private final MenuLayoutController layoutController = new MenuLayoutController(this);

    public ExampleMenu(int containerId, Inventory inventory) {
        super((MenuType<?>) null, containerId);
        this.layoutController.bind();
    }
}
```

```java
public class ExampleScreen extends AbstractContainerScreen<ExampleMenu> implements GuiHost, LayoutScreenView {
    private final GuiRootWidget root = new GuiRootWidget(this);
}
```

From there, bind menu slots to nodes and resolve screen widgets against the same nodes.
