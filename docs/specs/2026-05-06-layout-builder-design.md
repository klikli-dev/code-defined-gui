<!--
SPDX-FileCopyrightText: 2026 klikli-dev

SPDX-License-Identifier: MIT
-->

# Layout builder and resolver architecture

## Goal

Make CDG GUI definitions cleaner, more composable, and less dependent on hardcoded screen-relative constants while keeping the architecture sound for both client widgets and menu slot backends.

## Problems in the current design

- Screen layout is mostly absolute and screen-relative.
- Complex screens like `AttributeFilterScreen` accumulate many position constants, helper methods, and inheritance-based assembly.
- Widget ownership is hierarchical, but geometry is not. Moving a conceptual section of the UI requires updating many unrelated coordinates.
- Menu slots and screen widgets have split sources of truth for position.
- `GuiStyleProperties.OFFSET_X` and `OFFSET_Y` are layout concerns mixed into styling and should be removed.
- The current `addBackgroundWidgets()`, `addPlayerInventoryBackgroundWidgets()`, `addStaticWidgets()`, and `addScreenWidgets()` split makes extension and override awkward.

## Design goals

- Define GUI layout with parent-relative coordinates.
- Support grouping so moving a parent moves all children.
- Keep the shared layout model server-safe and client-agnostic.
- Let menu slot binding and client widget creation share one geometry source of truth.
- Keep authoring concise and builder-oriented.
- Allow helper methods and reusable fragments for composition.
- Allow direct widget creation in the client resolve phase without extra registries or factories.
- Keep style overrides for visual concerns only.
- Remove `OFFSET_X` and `OFFSET_Y` from style.

## Recommended approach

Introduce a new shared layout tree with only two structural concepts:

- `group(id)` for nodes with children
- `node(id)` for leaf geometry anchors

This layout tree is structural only. It does not create widgets, does not know slot behavior, and does not encode client-only concepts.

The system is then split into three explicit phases:

1. **Layout definition**: build the shared tree of groups and nodes.
2. **Menu binding**: bind slots and other backend menu elements to named layout nodes.
3. **Client resolver registration and execution**: register lambdas that resolve named layout nodes into concrete widgets after registration is complete.

## Shared layout model

### Core API shape

The shared builder should stay intentionally small.

- `group(String id, Consumer<GroupBuilder> builder)`
- `node(String id)`
- `at(int x, int y)`
- `at(PositionResolver resolver)`
- `size(int width, int height)`

`size(...)` is optional. If present, the resolver can use it. If absent, width and height are unavailable unless the resolver handles missing size explicitly.

### Geometry rules

- All positions are relative to the parent group.
- Group movement automatically moves all descendants.
- Nodes are addressed by string ids.
- Node lookup only searches the current scope and then walks up the ancestor chain.
- Layout code may use `ctx.node("id")` to reference already-defined nearby nodes for custom coordinate math.
- Cross-tree global lookup is intentionally not part of the default API.

### Optional geometry accessors

Inside the layout builder and resolve contexts, node/group geometry should be queryable through helpers such as:

- `x()`
- `y()`
- `width()` / `height()` returning optional values
- `right()` / `bottom()` when width and height exist
- `size()` returning an optional size object

These helpers exist to reduce duplicated constants without introducing automatic layout logic.

## Example shared layout for the attribute filter

```java
var playerInventory = PlayerInventoryFragment.create();

LayoutSpec layout = LayoutSpec.create(root -> {
    root.group("main", main -> {
        main.at(0, 0);

        main.group("filter_area", area -> {
            area.at(0, 12);
            area.size(241, 75);

            area.node("panel_bg").at(3, 0).size(area.width() - 6, area.height());
            area.node("top_bar").at(0, -12).size(241, 15);

            area.node("reference").at(19, 12).size(18, 18);
            area.node("selection").at(42, 11).size(137, 18);
            area.node("add_button").at(190, 11).size(18, 18);
            area.node("add_inverted_button").at(208, 11).size(18, 18);

            area.node("match_any").at(47, 49).size(18, 18);
            area.node("match_all").at(65, 49).size(18, 18);
            area.node("deny").at(83, 49).size(18, 18);

            area.node("summary").at(18, 43);
            area.node("horizontal_separator").at(3, 36).size(235, 1);
        });

        main.node("vertical_separator").at(202, 48).size(1, 39);
    });

    root.group("player_inventory", inv -> {
        inv.at(40, 109);
        playerInventory.define(inv);
    });
});
```

This keeps the layout tree generic. The meaning of `panel_bg`, `reference`, or `selection` comes later during menu binding and client resolution.

## Menu binding

The menu owns backend behavior. The layout only provides geometry.

The layout should not encode:

- ghost vs normal slot behavior
- readonly behavior
- quick-move behavior
- custom slot classes
- menu index semantics

These remain in menu code.

### Menu binding example

```java
@Override
protected void addFilterSlots() {
    var ctx = this.menuLayoutContext();

    var reference = ctx.node("main.filter_area.reference");
    this.addGhostSlot(REFERENCE_SLOT, reference.x(), reference.y(), BuiltinSlotRoles.FILTER_REFERENCE);

    var summary = ctx.node("main.filter_area.summary");
    this.addGhostSlot(SUMMARY_SLOT, summary.x(), summary.y(), BuiltinSlotRoles.FILTER_SUMMARY);

    playerInventory.bindMenu(ctx.scope("player_inventory"));
}
```

This preserves full menu flexibility while removing duplicated geometry constants.

## Client resolution

Replace the current multi-method screen assembly model with one deferred resolver registration hook.

### New screen hook

```java
protected void registerResolvers(LayoutResolverRegistry registry)
```

This method registers lambdas but does not execute them immediately. After the full inheritance chain and all fragments have registered their resolvers, CDG executes them.

This allows a child screen to call `super.registerResolvers(registry)` and then override a parent resolver by registering another resolver for the same id.

### Resolver registry semantics

- `resolve(id, fn)` replaces any previous primary resolver for that id.
- `appendResolve(id, fn)` adds an additional resolver for that id.
- Resolver execution is deferred until registration is complete.
- Base ordering is registration order.
- Each resolver may optionally specify a render priority.
- Final execution order is sorted by priority and then registration order.

This allows readable layout definition order while still making it possible to render some widgets earlier or later than others.

### Resolver context

The resolver context should provide:

- `node()` to access the node currently being resolved
- `node(String id)` to access another node in the local or ancestor scope
- `scope(String id)` to create a scoped context
- `addWidget(...)` to add one or more widgets

Using `node()` avoids repeating the same id inside the lambda.

### Client resolve example

```java
@Override
protected void registerResolvers(LayoutResolverRegistry r) {
    super.registerResolvers(r);

    r.resolve("panel_bg", -10, ctx -> {
        var node = ctx.node();
        ctx.addWidget(new GuiBackgroundWidget(
                this,
                node.x(),
                node.y(),
                node.widthOrThrow(),
                node.heightOrThrow(),
                this.partSprite(BuiltinFilterParts.ATTRIBUTE_PANEL, GuiSprites.GUI_BACKGROUND)
        ));
    });

    r.resolve("top_bar", 10, ctx -> {
        var node = ctx.node();
        ctx.addWidget(new GuiBackgroundWidget(
                this,
                node.x(),
                node.y(),
                node.widthOrThrow(),
                node.heightOrThrow(),
                this.partSprite(BuiltinFilterParts.ATTRIBUTE_TOP_BAR, GuiSprites.GUI_BACKGROUND)
        ));
    });

    r.resolve("selection", ctx -> {
        var node = ctx.node();
        this.selectionWidget = this.root.addChild(new AttributeSelectionWidget(
                node.x(),
                node.y(),
                node.widthOrThrow(),
                node.heightOrThrow(),
                this.partSprite(BuiltinFilterParts.ATTRIBUTE_SELECTION, GuiSprites.ATTRIBUTE_FILTER_SELECTION),
                this::candidates,
                this.menu::selectedCandidateIndex,
                this::changeSelection
        ).withTitle(Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.AVAILABLE)));
    });

    playerInventory.client().registerResolvers(r.scope("player_inventory"));
}
```

This removes the need for separate `addBackgroundWidgets()` and `addScreenWidgets()` buckets and gives subclasses a clean override point.

## Fragments

Reusable layout pieces should be modeled as fragments with explicit hooks for each phase.

They should be installed explicitly during layout definition rather than discovered later by walking the final tree.

### Fragment shape

```java
public interface LayoutFragment {
    void define(GroupBuilder root);
    void bindMenu(MenuLayoutContext ctx);
    ClientLayoutFragment client();
}

public interface ClientLayoutFragment {
    void registerResolvers(LayoutResolverRegistry registry);
}
```

Fragments should usually be created through factories such as `PlayerInventoryFragment.create()` rather than singletons such as `INSTANCE`.

This avoids singleton smell and leaves room for future configuration.

### Fragment installation example

```java
var playerInventory = PlayerInventoryFragment.create();

root.group("player_inventory", inv -> {
    inv.at(40, 109);
    playerInventory.define(inv);
});
```

Then later:

```java
playerInventory.bindMenu(menuCtx.scope("player_inventory"));
playerInventory.client().registerResolvers(screenRegistry.scope("player_inventory"));
```

Contexts for fragments should be scoped to the fragment root so fragment internals can use simple local ids without colliding with unrelated screen nodes.

## Style system impact

The existing style system remains useful for visual overrides and should stay focused on appearance.

Style should continue to handle things such as:

- sprite
- hover sprite
- pressed sprite
- on sprite
- off sprite
- color
- text color

`GuiStyleProperties.OFFSET_X` and `OFFSET_Y` should be removed rather than renamed. Layout and resolver logic should handle positional adjustments directly where needed.

This keeps layout out of style and preserves a clean architectural boundary.

## Migration strategy

Recommended migration order:

1. Introduce the shared layout tree API.
2. Introduce menu and client resolve contexts.
3. Introduce deferred resolver registration.
4. Convert `AbstractFilterScreen` to the new resolver hook.
5. Extract a reusable `PlayerInventoryFragment`.
6. Migrate `AttributeFilterScreen` as the proving ground.
7. Migrate `ListFilterScreen` and any similar screens.
8. Remove obsolete assembly hooks and old absolute-position helpers where possible.
9. Remove `OFFSET_X` and `OFFSET_Y` from `GuiStyleProperties` and adapt any code that relied on them.

## Benefits

- Moving a group moves all of its children, including slot anchors.
- Complex screens become compositional instead of constant-heavy.
- Reusable fragments become practical for shared sections like player inventory.
- Client and menu geometry stay aligned from one shared layout tree.
- Screen inheritance becomes cleaner through deferred resolver override.
- Style remains focused on visuals rather than layout.

## Non-goals

- No advanced automatic layout engine.
- No global node registry.
- No slot behavior metadata in the shared layout tree.
- No requirement for external registries or factories to define widgets.

## Open implementation notes

- Width and height should likely be represented as optional values in resolved nodes.
- Helper accessors such as `widthOrThrow()` are useful for resolvers that require size.
- Scoped registries and contexts will be important for fragment reuse and clean composition.
- The exact concrete type names may change, but the architectural split should remain as described here.
