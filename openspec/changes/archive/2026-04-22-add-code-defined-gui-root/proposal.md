## Why

The mod currently renders a test GUI directly from a screen class, which does not yet provide a reusable code-defined GUI structure for other screens or mods. A minimal root widget, reusable GUI texture wrappers, and a small starter component set are needed so screens can be assembled in code while still following Minecraft's screen lifecycle and positioning model.

## What Changes

- Add a screen-wide root `AbstractWidget` container for code-defined GUIs that can work with both `Screen` and `AbstractContainerScreen` hosts.
- Add a host interface that exposes screen metrics and GUI placement data such as `leftPos`, `topPos`, `width`, `height`, `imageWidth`, and `imageHeight`.
- Add automatic child widget registration and deregistration so root-managed components participate in normal screen input and focus handling.
- Add a minimal initial component set: a 9-slice background, inventory slot, crafting result slot, and crafting arrow component.
- Add an object-oriented GUI texture wrapper class plus a `GuiSprites` constants holder for built-in sprite definitions with default render sizes and an optional tint value.
- Define resize and re-init behavior so the root widget can resync with the host screen and rebuild child registration cleanly.
- Leave clipping and scissoring out of scope for the initial implementation.

## Capabilities

### New Capabilities
- `code-defined-gui-root`: A reusable root widget and host contract for screen-wide code-defined GUI composition, child lifecycle, and re-init behavior.
- `gui-components`: A minimal starter component set built on `AbstractWidget`, including background and slot-style GUI elements.
- `gui-textures`: Object-oriented GUI texture definitions with default render sizing through `GuiSprite` instances exposed by `GuiSprites`.

### Modified Capabilities

None.

## Impact

- Affects client GUI code under `src/main/java/com/klikli_dev/codedefinedgui/gui/`.
- Uses existing GUI sprite assets under `src/main/resources/assets/codedefinedgui/textures/gui/sprites/`.
- Introduces a new reusable API surface for host screens and GUI components.
- Will likely replace or restructure the current ad hoc rendering in `TestScreen`.
