<!--
SPDX-FileCopyrightText: 2026 klikli-dev

SPDX-License-Identifier: MIT
-->

# Migration notes

This refactor is intentionally breaking. There is no compatibility bridge for the old package layout.

## Main package moves

- generic layout types moved to `com.klikli_dev.codedefinedgui.api.layout`
- generic screen host/root types moved to `com.klikli_dev.codedefinedgui.api.screen`
- generic style types moved to `com.klikli_dev.codedefinedgui.api.style`
- generic texture types moved to `com.klikli_dev.codedefinedgui.api.texture`
- reusable widgets moved to `com.klikli_dev.codedefinedgui.api.widget`
- premade filter code moved under `com.klikli_dev.codedefinedgui.premade.filter.*`
- bootstrap, networking, commands, registries, and framework plumbing moved under `com.klikli_dev.codedefinedgui.internal.*`

## Notable type changes

- `GuiLayoutKey` now lives in `api.layout`, not style-related packages
- style selection on filter items is now `guiStyleKey(ItemStack stack, GuiLayoutKey layout)`
- `ItemAttributes` now rejects duplicate registrations and freezes during bootstrap
- `ItemAttributes.get(Identifier id)` now returns `ItemAttributeType` directly

## Documentation model change

The old docs described CDG primarily as a widget-first system.

The new public story is layout-first:

1. define a `LayoutSpec`
2. bind menu slots to nodes
3. resolve screen widgets against nodes
4. apply visuals through `GuiStyleRegistry`

Manual widget composition is still possible, but it is now a secondary technique.

## Premade filter moves

If you previously imported from old `filter.*` or `gui.filter.*` packages, move to the new `premade.filter.*` namespaces.

In particular, built-in filter layouts and parts are now under:

- `com.klikli_dev.codedefinedgui.premade.filter.core.layout.BuiltinFilterLayouts`
- `com.klikli_dev.codedefinedgui.premade.filter.core.layout.BuiltinFilterParts`

## Internal and reference code

Do not migrate downstream code to `internal.*` packages.

Some debug/reference classes that used to feel like examples now live under internal namespaces instead, such as `internal.screen.TestScreen`. They are not supported API.

The dedicated reference path for users is now `com.klikli_dev.codedefinedgui.example.*`.

## Recommended migration checklist

1. update imports to `api.*` and `premade.filter.*`
2. stop importing any `internal.*` classes
3. update filter item style overrides to the new `guiStyleKey(ItemStack, GuiLayoutKey)` signature
4. review custom attribute registration timing for the new freeze behavior
5. retest client screen registration and menu opening flows
