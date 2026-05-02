# Repository Atlas: code-defined-gui

## Responsibility
NeoForge mod repository for Code Defined GUI, a library-style Minecraft mod that ships editable filter items, a custom GUI widget framework, and supporting datagen, networking, and example integrations.

## Design
- Uses standard NeoForge split entrypoints: `CodeDefinedGui` for common setup and `CodeDefinedGuiClient` for client-only screen bindings.
- Centers the domain on filter abstractions in `src/main/java/com/klikli_dev/codedefinedgui/filter/`, with menu editing backends separated from client screen rendering in `gui/`.
- Persists filter state through NeoForge data components registered in `registry/DataComponentRegistry.java`.
- Generates localization and item model assets through the Gradle + datagen pipeline defined in `build.gradle` and `datagen/`.

## Flow
1. Gradle loads generated resources from `src/generated/resources/` alongside source-owned assets declared in the build.
2. `src/main/java/com/klikli_dev/codedefinedgui/CodeDefinedGui.java` registers items, menu types, data components, networking, datagen hooks, and commands.
3. Filter items open menu backends under `filter/`; those menus own draft state, ghost-slot storage, and slot layout metadata.
4. `src/main/java/com/klikli_dev/codedefinedgui/CodeDefinedGuiClient.java` binds menu types to screens under `gui/filter/`, where `AbstractFilterScreen` hosts a `GuiRootWidget`, generic widgets from `gui/widget/`, and filter-specific controls from `gui/filter/widget/`.
5. Datagen providers under `datagen/` emit generated language and model JSON consumed as runtime resources.

## Integration
- Entry points: `src/main/java/com/klikli_dev/codedefinedgui/CodeDefinedGui.java`, `src/main/java/com/klikli_dev/codedefinedgui/CodeDefinedGuiClient.java`
- Build/config: `build.gradle`, `settings.gradle`, `gradle.properties`, `src/main/templates/META-INF/neoforge.mods.toml`
- Runtime assets: `src/main/resources/assets/codedefinedgui/`, `src/generated/resources/assets/codedefinedgui/`
- Detailed maps:

| Directory | Responsibility | Detailed Map |
| --- | --- | --- |
| `src/` | Source tree for Java code, source-owned resources, and generated assets. | `src/codemap.md` |
| `src/main/java/com/klikli_dev/codedefinedgui/` | Main mod package containing bootstrap, filter domain, GUI system, networking, registries, commands, datagen, and examples. | `src/main/java/com/klikli_dev/codedefinedgui/codemap.md` |
| `src/main/java/com/klikli_dev/codedefinedgui/filter/` | Filter domain model, state persistence, menu editing backends, and concrete filter implementations. | `src/main/java/com/klikli_dev/codedefinedgui/filter/codemap.md` |
| `src/main/java/com/klikli_dev/codedefinedgui/gui/` | Client UI layer: host contracts, filter screens, generic widget primitives, filter-specific widgets, and sprite wrappers. | `src/main/java/com/klikli_dev/codedefinedgui/gui/codemap.md` |
| `src/main/java/com/klikli_dev/codedefinedgui/registry/` | Deferred registries for items, menu types, and data components. | `src/main/java/com/klikli_dev/codedefinedgui/registry/codemap.md` |
| `src/main/java/com/klikli_dev/codedefinedgui/network/` | Payload abstraction, registration, and dispatch helpers. | `src/main/java/com/klikli_dev/codedefinedgui/network/codemap.md` |
| `src/main/java/com/klikli_dev/codedefinedgui/datagen/` | Datagen hooks and providers for generated language/model assets. | `src/main/java/com/klikli_dev/codedefinedgui/datagen/codemap.md` |
| `src/main/java/com/klikli_dev/codedefinedgui/example/` | End-to-end example command, payload, and demo screen. | `src/main/java/com/klikli_dev/codedefinedgui/example/codemap.md` |
