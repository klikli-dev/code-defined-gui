# src/

## Responsibility
Owns the mod's source tree: hand-written Java code and resources in `main/` plus generated assets in `generated/`.

## Design
- Splits authoritative source-owned content under `src/main/` from datagen output under `src/generated/`.
- Keeps Java package structure aligned with mod namespaces under `src/main/java/com/klikli_dev/codedefinedgui/`.

## Flow
1. Developers edit Java and source-owned assets in `src/main/`.
2. Datagen writes language and model JSON into `src/generated/resources/`.
3. Gradle merges `src/main/resources/` and generated resources into the runtime classpath.

## Integration
- Code map entry: `src/main/codemap.md`
- Java packages: `src/main/java/codemap.md`
