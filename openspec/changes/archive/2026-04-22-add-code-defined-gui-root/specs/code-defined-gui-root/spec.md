## ADDED Requirements

### Requirement: Root widget supports screen-wide code-defined GUI hosting
The system SHALL provide a root `AbstractWidget` that spans the full host screen and can be attached to both `Screen` and `AbstractContainerScreen` hosts through a shared host contract.

#### Scenario: Root widget works with a plain screen host
- **WHEN** a plain `Screen` implements the required host interface and creates the root widget
- **THEN** the root widget MUST accept that host and render as a screen-wide widget

#### Scenario: Root widget works with a container screen host
- **WHEN** an `AbstractContainerScreen` implements the required host interface and creates the root widget
- **THEN** the root widget MUST accept that host and render as a screen-wide widget

### Requirement: Host contract exposes GUI placement metrics
The system SHALL define a single host interface that exposes `leftPos`, `topPos`, `width`, `height`, `imageWidth`, and `imageHeight` so the root widget and its children can query current screen geometry.

#### Scenario: Root widget reads updated host metrics
- **WHEN** host metric values change during screen initialization or resize
- **THEN** the root widget MUST be able to read the updated values from the host interface without requiring a new metric API

### Requirement: Root widget manages child widget registration with the host screen
The root widget SHALL provide child management that automatically registers and deregisters child `AbstractWidget` instances with the host screen so they participate in the screen's normal rendering, input, focus, and narration flows.

#### Scenario: Adding a child registers it with the host screen
- **WHEN** a child widget is added to the root widget
- **THEN** the child MUST be registered with the host screen's widget lists

#### Scenario: Removing a child deregisters it from the host screen
- **WHEN** a child widget is removed from the root widget
- **THEN** the child MUST be deregistered from the host screen's widget lists

### Requirement: Root widget remains correct across re-init and resize
The root widget SHALL support host reinitialization and resize by resynchronizing itself with the host and rebuilding child registration without duplicating child widgets in the screen's widget lists.

#### Scenario: Re-init rebuilds registered children cleanly
- **WHEN** the host screen runs its initialization logic again after creation
- **THEN** the root widget MUST re-register its current child widgets exactly once each

#### Scenario: Resize preserves root widget behavior
- **WHEN** the host screen is resized and reinitialized
- **THEN** the root widget MUST continue using the host's current screen and image metrics for subsequent behavior
