## ADDED Requirements

### Requirement: GUI components are widget-based
The system SHALL implement the initial code-defined GUI component set as `AbstractWidget` subclasses so they can be managed by the root widget and host screen without a separate component lifecycle.

#### Scenario: Root widget accepts initial components as children
- **WHEN** a background, inventory slot, crafting result slot, or crafting arrow component is created
- **THEN** each component MUST be addable to the root widget as an `AbstractWidget`

### Requirement: Background component supports default and overridden sizing
The system SHALL provide a 9-slice background component that defaults to the host's `imageWidth` and `imageHeight` for rendering, while also allowing explicit screen-space position and size overrides.

#### Scenario: Background uses host image size by default
- **WHEN** a background component is created without an explicit size override
- **THEN** it MUST render using the host's current `imageWidth` and `imageHeight`

#### Scenario: Background allows explicit position and size overrides
- **WHEN** a background component is created with explicit position and size values
- **THEN** it MUST render at the provided screen-space coordinates and dimensions instead of the host defaults

### Requirement: Built-in slot-style components use packaged sprites
The system SHALL provide `AbstractWidget` components for inventory slot, crafting result slot, and crafting arrow visuals using the built-in packaged sprite resources.

#### Scenario: Inventory slot component renders its packaged sprite
- **WHEN** an inventory slot component is rendered
- **THEN** it MUST use the packaged inventory slot sprite definition

#### Scenario: Crafting result slot component renders its packaged sprite
- **WHEN** a crafting result slot component is rendered
- **THEN** it MUST use the packaged crafting result slot sprite definition

#### Scenario: Crafting arrow component renders its packaged sprite
- **WHEN** a crafting arrow component is rendered
- **THEN** it MUST use the packaged crafting arrow sprite definition

### Requirement: Components use screen-space positioning
The system SHALL position the initial GUI components in screen-space coordinates so they can be registered directly with the host screen and remain aligned with root-managed lifecycle behavior.

#### Scenario: Child component placement matches screen-space coordinates
- **WHEN** a child component is given an x/y position
- **THEN** it MUST render and receive input at those screen-space coordinates
