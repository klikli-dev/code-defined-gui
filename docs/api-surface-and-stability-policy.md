<!--
SPDX-FileCopyrightText: 2026 klikli-dev

SPDX-License-Identifier: MIT
-->

# API surface and stability policy

CDG uses package boundaries to communicate support expectations.

## Supported surface

### `com.klikli_dev.codedefinedgui.api.*`

Stable modder-facing generic GUI API.

Use this for layout specs, style sheets, textures, widgets, and screen/menu integration.

### `com.klikli_dev.codedefinedgui.premade.*`

Supported higher-level shipped features built on the generic API.

Today that primarily means `premade.filter.*`.

## Reference-only surface

### `com.klikli_dev.codedefinedgui.example.*`

Reference code that demonstrates intended usage patterns.

Examples are useful to read and copy from, but they are not a stable compatibility contract.

## Internal surface

### `com.klikli_dev.codedefinedgui.internal.*`

Implementation details.

Do not import from `internal.*` in downstream mods.

## Practical rule

- build generic integrations on `api.*`
- build on shipped filter features through `premade.*`
- read `example.*` for reference
- avoid `internal.*`
