## ADDED Requirements

### Requirement: GUI textures are defined as objects with default render size and tint
The system SHALL provide a GUI texture wrapper class that represents a sprite resource together with its default render width, default render height, and an optional tint value that defaults to `-1`.

#### Scenario: Texture definition provides default render size
- **WHEN** code uses a GUI texture definition for rendering
- **THEN** the definition MUST expose the sprite identifier and default render dimensions as object data

#### Scenario: Texture definition uses default tint when unspecified
- **WHEN** a GUI texture definition is created without an explicit tint
- **THEN** the definition MUST use `-1` as its tint value

### Requirement: Built-in GUI textures are exposed through GuiSprites
The system SHALL provide a plural constants holder named `GuiSprites` that exposes built-in GUI texture definitions as reusable object instances rather than enum values.

#### Scenario: Built-in sprite definitions are available from GuiSprites
- **WHEN** code needs the packaged background, inventory slot, crafting result slot, or crafting arrow texture definitions
- **THEN** it MUST be able to reference them through `GuiSprites`

### Requirement: Texture definitions support sized copies
The system SHALL allow a GUI texture definition to produce a copy with different render dimensions while preserving the underlying sprite resource.

#### Scenario: Background texture can be copied with a custom size
- **WHEN** code creates a sized copy of the built-in background texture definition
- **THEN** the copy MUST retain the same sprite resource, preserve the original tint unless explicitly changed, and expose the overridden render dimensions

### Requirement: Nine-slice metadata remains resource-driven
The system SHALL rely on Minecraft sprite metadata for 9-slice behavior and MUST NOT require separate 9-slice metadata to be duplicated in the GUI texture wrapper class.

#### Scenario: Background texture definition omits duplicated 9-slice settings
- **WHEN** the built-in background texture definition is declared
- **THEN** it MUST not require code-level 9-slice metadata fields to describe behavior already defined by the sprite resource metadata
