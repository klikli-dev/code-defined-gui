# src/main/java/com/klikli_dev/codedefinedgui/

## Responsibility
Main mod package containing lifecycle entrypoints, shared constants/config, and all feature subpackages.

## Design
- `CodeDefinedGui.java` is the common `@Mod` bootstrap for registries, datagen, networking, and commands.
- `CodeDefinedGuiClient.java` is the client-only bootstrap for config UI and screen registration.
- Feature code is split into focused subpackages: `filter/`, `gui/`, `registry/`, `network/`, `datagen/`, `command/`, and `example/`.
- The GUI stack separates host/root composition in `gui/core/`, reusable primitives in `gui/widget/`, and filter-only controls in `gui/filter/widget/`.

## Flow
1. `CodeDefinedGui` registers common systems on the mod event bus and NeoForge event bus.
2. `CodeDefinedGuiClient` registers screen factories for menu types.
3. Filter menus provide draft state and slot metadata, while GUI packages provide host contracts, screen composition, reusable widgets, and filter-specific controls.
4. Supporting packages provide payload dispatch, commands, examples, and generated assets.

## Integration
- Root entrypoints: `CodeDefinedGui.java`, `CodeDefinedGuiClient.java`, `Config.java`, `CodeDefinedGuiConstants.java`
- Detailed package maps:
  - `command/codemap.md`
  - `datagen/codemap.md`
  - `example/codemap.md`
  - `filter/codemap.md`
  - `gui/codemap.md`
  - `network/codemap.md`
  - `registry/codemap.md`
