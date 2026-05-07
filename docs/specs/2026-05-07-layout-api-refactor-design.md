<!--
SPDX-FileCopyrightText: 2026 klikli-dev

SPDX-License-Identifier: MIT
-->

# Layout API and architecture refactor design

- Status: approved in conversation
- Date: 2026-05-07
- Scope: breaking refactor of package structure, public API surface, examples, registries, and docs

## Summary

Refactor Code Defined GUI from a mod that contains reusable GUI pieces into a curated, layout-driven GUI framework for downstream modders.

The primary supported story becomes:

1. define a layout spec
2. implement a small view interface
3. delegate runtime plumbing to a controller composition object
4. use reusable widgets inside that layout-driven system

The refactor is intentionally breaking. No compatibility shims or deprecated bridge layer will be added.

## Goals

1. Define a clear, documented API surface for downstream modders.
2. Reorganize packages around product boundaries instead of historical implementation groupings.
3. Make the layout-driven framework the primary public usage model.
4. Keep premade filters as an officially supported higher-level feature built on the API.
5. Move example code out of the perceived public runtime surface while keeping it in the main module.
6. Reduce downstream boilerplate using interface plus composition, not inheritance.
7. Prune overly low-level, stale, or accidental public abstractions.
8. Harden registries with duplicate protection and predictable lifecycle rules.
9. Bring all docs in line with the actual supported architecture and functionality.

## Non-goals

1. Preserve source compatibility with existing package names.
2. Split the project into multiple Gradle modules in this refactor.
3. Add new end-user features unrelated to API curation.
4. Implement optional nice-to-have items such as typed layout handles or layout caching in this change.

## Product architecture

The codebase will be reorganized into four top-level product areas.

### `com.klikli_dev.codedefinedgui.api`

Stable modder-facing surface.

Subpackages:

- `api.layout`
  - layout specs and builders
  - resolved layout access types
  - layout screen and menu integration contracts
  - layout controllers
  - intentionally public menu binding and layout resolution extension points
- `api.widget`
  - generic reusable widgets that are useful outside premade filters
- `api.style`
  - style keys, properties, registry, builtin generic parts and styles
- `api.texture`
  - sprite references and texture helpers
- `api.screen`
  - only kept if small screen-facing contracts remain clearer there than in `api.layout`

Design rule: if a downstream modder is expected to import it directly for generic GUI work, it belongs in `api`.

### `com.klikli_dev.codedefinedgui.premade`

Official higher-level shipped features built on top of the API.

Initial supported area:

- `premade.filter`
  - filter definitions
  - filter state and menu logic
  - filter screens
  - filter-specific widgets
  - filter-specific style/layout helpers
  - filter storage helpers
  - attribute and list filter implementations

Design rule: premade features are supported, but they are not the generic GUI core.

### `com.klikli_dev.codedefinedgui.internal`

Unstable implementation details.

Includes:

- mod bootstrap
- client bootstrap
- NeoForge wiring
- internal registries and registration glue
- network bootstrap and message plumbing not meant as library API
- commands
- implementation-only helpers

Design rule: downstream mods should never need to import from `internal`.

### `com.klikli_dev.codedefinedgui.example`

Example and reference code kept in the main module but clearly documented as non-API.

Design rule: examples may demonstrate intended usage, but must not be treated as part of the supported library surface.

## Primary usage model

The library will be documented and structured as a layout-driven GUI framework that also ships with handy widgets.

This changes the emphasis from manual fixed-position widget composition to layout-first composition.

Manual widget positioning remains possible where useful, but it becomes a secondary technique inside the broader layout system rather than the main story told by the documentation.

## Runtime integration model

No abstract base screen or menu classes will be introduced as the main public pattern.

The primary runtime composition model is:

- `LayoutScreenView` + `LayoutScreenController`
- `LayoutMenuView` + `LayoutMenuController` where menu-side support is needed

### `LayoutScreenView`

Small screen-side contract implemented by the consuming screen. It exposes only the information the controller actually needs, such as layout spec access, screen dimensions or hooks, and widget binding callbacks.

### `LayoutScreenController`

Composition object owned by the consuming screen. It handles:

- root widget lifecycle
- layout resolution
- resolved node lookup
- widget rebuild flow
- syncing runtime bounds to the current screen state
- delegating binding hooks back to the view

### `LayoutMenuView`

Small menu-side contract for layout-backed menu binding where menu slot wiring or menu context is needed.

### `LayoutMenuController`

Composition object that handles menu-side layout plumbing and binding coordination.

### Design constraints

1. Controllers own plumbing, not gameplay logic.
2. Views stay minimal and explicit.
3. Inheritance is not required for adoption.
4. The happy path should require a small amount of field setup and lifecycle delegation, not bespoke plumbing in every screen.

## API boundary policy

The refactor defines a strict supported-surface rule.

### Public and supported

Only types intentionally used by downstream mods for generic layout GUIs or officially supported premade features remain public and documented.

### Internalized

Types that only support internal plumbing, NeoForge wiring, internal rendering orchestration, or narrow implementation details are moved under `internal` and removed from the supported public story.

### Reduced visibility

Where possible, helper types that do not need cross-package visibility should become package-private.

### Deleted

Obsolete or redundant abstractions should be removed outright instead of preserved under a new package.

## Pruning rules

The refactor should specifically remove or internalize abstractions that fail any of these tests:

1. A downstream modder would not reasonably know when to use it.
2. It exists only to support framework internals.
3. It overlaps with a clearer public abstraction.
4. It is stale, unused, or effectively single-purpose.

Likely candidates include low-level renderer or binder plumbing interfaces, stale slot-role helper types that do not belong in the extension story, and narrowly scoped support types exposed only because of the current package layout.

The final decision for each type should favor a smaller, sharper API even if that means a few current public classes disappear entirely.

## Package reorganization rules

The reorganization is guided by product role, not by technical implementation detail alone.

### Generic GUI core moves to `api`

Current `gui.core`, `gui.layout`, `gui.style`, `gui.texture`, and reusable parts of `gui.widget` will be moved and curated into `api` packages.

### Filter code moves to `premade.filter`

Current `filter.*` and filter-specific UI packages such as `gui.filter.*` move under `premade.filter.*` with cleaner internal subdivision.

### Bootstrap and registries move to `internal`

Current root bootstrap classes, registry wiring, commands, and networking setup move under `internal`, even if the physical mod entrypoint classes must remain discoverable by NeoForge resource configuration.

### Examples stay examples

Current `example.*` remains in a dedicated `example` namespace and is explicitly documented as non-API.

## Registry hardening

All extension registries exposed as part of the supported surface must follow consistent rules.

### Required behavior

1. Reject duplicate registrations instead of silently overwriting.
2. Provide clear exception messages that identify the conflicting key.
3. Support a defined registration window during bootstrap.
4. Freeze after bootstrap where mutability is not part of the feature design.
5. Document registration lifecycle and expected call timing.

### Immediate target

The attribute registry behavior should be aligned with the stricter style registry approach, rather than allowing silent replacement.

## Documentation plan

Documentation will be updated to match the new architecture and to better support adoption.

### Files to update

- `README.md`
- `docs/README.md`
- `docs/using-the-library.md`
- `docs/styling-premade-guis.md`

### Files to add

- architecture overview
- API surface and stability policy
- layout-driven quick start
- premade filters guide
- examples guide
- migration notes for the breaking package refactor

### Documentation rules

1. Layout-driven usage is presented first.
2. Generic widgets are described as tools within the layout framework, not as the main mental model.
3. `example` is labeled as reference code, not supported API.
4. `internal` is never presented as an import target.
5. Premade filters are documented as an official higher-level feature.

## Breaking change policy

This refactor is intentionally breaking.

### Consequences

1. Existing imports will change.
2. Existing extension points may move, narrow, or disappear.
3. Existing docs and examples must be rewritten to match the new public story.
4. No temporary compatibility layer will be added.

### Rationale

A curated, smaller, stable API surface is more valuable than preserving accidental package structure during a library-shaping refactor.

## Validation plan

The implementation phase should validate the refactor with at least:

1. successful Java compilation via the Gradle wrapper
2. doc review for package references and outdated guidance
3. verification that example code still demonstrates the intended layout-driven flow
4. targeted review that premade filters still operate as an official feature on top of the API

## Implementation outcomes expected from the refactor

After implementation:

1. downstream modders can identify the supported API by package name alone
2. layout-driven integration is the clearest and lowest-friction path
3. examples are clearly separate from the library surface
4. premade filters remain supported without polluting the generic API
5. registry behavior is predictable and safe
6. docs accurately describe the current architecture and extension story
