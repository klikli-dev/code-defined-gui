# src/main/

## Responsibility
Contains the authoritative runtime sources for the mod: Java implementation and hand-authored assets.

## Design
- `java/` contains the NeoForge mod code.
- `resources/` contains source-owned runtime assets such as textures and logos.

## Flow
1. NeoForge loads Java classes from `java/`.
2. Resource loading pulls textures and static assets from `resources/`.

## Integration
- Java runtime: `src/main/java/codemap.md`
