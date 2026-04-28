<!--
SPDX-FileCopyrightText: 2026 klikli-dev

SPDX-License-Identifier: MIT
-->

# Package structure refactor design

## Goal

Restructure the codebase into a professional, maintainable, and easy-to-extend layout.

The refactor should aggressively improve package and class organization, remove shallow abstractions, and clarify ownership of domain logic versus infrastructure and UI logic.

## Current problems

- Core mod wiring, registries, networking, and datagen are split across several unrelated top-level packages.
- The filter subsystem mixes domain abstractions, persistence, UI container code, and reusable support utilities.
- The GUI package mixes reusable framework code, filter-specific screens, and example/demo code.
- Several abstractions add little or no value, especially trivial inheritance layers.
- Some classes with related responsibilities are far apart, while unrelated classes share broad packages.

## Design principles

- Organize by feature and responsibility, not by arbitrary technical layer alone.
- Keep domain logic separate from infrastructure glue.
- Prefer concrete reusable classes over shallow inheritance.
- Keep example code public but isolated from production framework code.
- Remove duplicate or mirrored types that do not create meaningful boundaries.
- Make package intent obvious from names alone.

## Target top-level structure

```text
com.klikli_dev.codedefinedgui
  command/
  example/
  filter/
    core/
    support/
    list/
    attribute/
  gui/
    core/
    texture/
    widget/
    filter/
  infrastructure/
    datagen/
    network/
    registry/
```

## Package responsibilities

### `infrastructure`

Contains all NeoForge and Minecraft integration glue.

This includes:

- mod entrypoints
- config wiring
- registries
- networking
- datagen

Classes moving here:

- `CodeDefinedGui`
- `CodeDefinedGuiClient`
- `Config`
- everything under current `datagen`
- everything under current `network`
- everything under current `registry`

Rationale: these classes are integration concerns, not core domain concepts.

### `filter.core`

Contains the shared domain abstractions and shared editor bases used by filter types.

Classes moving here:

- `FilterDefinition`
- `FilterState`
- `FilterStateAccessor`
- `FilterMatchContext`
- `AbstractFilterItem`
- `AbstractFilterMenu`

Planned cleanup:

- rename abstract base classes to clearer role-based names if needed
- keep only responsibilities shared by multiple filter types

### `filter.support`

Contains reusable support classes used while editing filters, but not specific to list or attribute filtering.

Classes moving here:

- `GhostItemStorage`
- `GhostResourceHandlerSlot`

Rationale: these are generic ghost-slot editing utilities rather than filter-domain concepts.

### `filter.list`

Contains all list-filter-specific types.

Classes staying together here:

- `ListFilterDefinition`
- `ListFilterItem`
- `ListFilterMenu`
- `ListFilterState`
- `ListFilterConfig`
- `ListFilterMode`
- `ListFilterStateAccessor`

Planned cleanup:

- merge `ListFilterConfig` into `ListFilterState` if the separate record does not provide a real boundary

### `filter.attribute`

Contains all attribute-filter-specific types.

Classes staying together here:

- `AttributeFilterDefinition`
- `AttributeFilterItem`
- `AttributeFilterMenu`
- `AttributeFilterState`
- `AttributeFilterConfig`
- `AttributeFilterStateAccessor`
- `AttributeFilterMode`
- `AttributeRule`
- `AttributeCandidate`
- `AttributePayloads`
- `ItemAttributes`
- `ItemAttributeType`
- `StandardAttributeType`
- concrete attribute type implementations

Planned cleanup:

- extract candidate and rule-building logic from `AttributeFilterMenu`
- merge `AttributeFilterConfig` into `AttributeFilterState` if the separate record is not justified

### `gui.core`

Contains core GUI composition contracts and host integration.

Classes moving here:

- `GuiHost`
- `GuiRootWidget`
- `GuiSyncable`

### `gui.texture`

Contains sprite and texture description classes.

Classes moving here:

- `GuiTexture`
- `GuiTextures`

### `gui.widget`

Contains reusable concrete widgets.

Classes moving here:

- `FrameWidget`
- `IconButtonWidget`
- `FilterIndicatorWidget`
- `AttributeSelectionWidget`
- `AttributeRuleSummaryWidget`

Planned cleanup:

- remove `AbstractGuiTextureWidget`
- replace it with one reusable concrete texture widget, likely `TextureWidget`
- remove trivial one-line subclasses such as inventory slot and crafting texture widgets unless they carry real behavior or semantic value

Rationale: the current abstract texture widget adds almost no meaningful abstraction and should be replaced by a concrete reusable widget.

### `gui.filter`

Contains filter-specific screen classes.

Classes moving here:

- `AbstractFilterScreen`
- `ListFilterScreen`
- `AttributeFilterScreen`

### `example`

Contains supported example and demo code that remains part of the project, but is clearly not core infrastructure or framework code.

Classes moving here:

- `TestScreen`
- `OpenTestScreenMessage` under a nested `example.network` package or equivalent example-specific area

Rationale: example code should stay public and usable, but should not live beside core framework code.

### `command`

Keeps command entrypoints such as `CdgCommand`, updated to target example namespaced classes.

## Behavioral refactoring

This change is not limited to package moves.

The refactor should also:

1. remove shallow abstractions that add no meaningful behavior
2. reduce duplication between state and config records where possible
3. move domain computations out of menu classes where practical
4. extract attribute candidate and rule assembly into dedicated helper or service classes
5. rename vague base classes where clearer role-based names improve understanding

## Expected outcomes

After the refactor:

- package ownership should be obvious
- related classes should be near each other
- reusable GUI primitives should be separated from feature-specific UI
- example code should be isolated and discoverable
- filter menus should be simpler and more focused
- future filter types should have a clear place to live

## Validation

Minimum validation:

- run `./gradlew.bat compileJava`

Behavior that must remain working:

- opening filter items
- editing list filters
- editing attribute filters
- opening the example test screen

## Implementation notes

- Make the refactor aggressive rather than preserving old internal package structure.
- Favor cleanup during the move instead of performing a purely mechanical package relocation.
- Keep external behavior stable where reasonable, but internal names and package paths may change substantially.
