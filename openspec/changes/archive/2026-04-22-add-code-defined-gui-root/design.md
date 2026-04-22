## Context

The current GUI code is centered around a single `TestScreen` that computes `leftPos`, `topPos`, `imageWidth`, and `imageHeight` in the screen itself and renders a background sprite directly. The change needs to preserve that familiar positioning model while moving composition into reusable widgets that can work with both plain `Screen` implementations and `AbstractContainerScreen` implementations.

Minecraft screen input, focus, narration, and mouse dispatch already flow through each screen's widget lists, so a root widget that wants child widgets to behave like native screen widgets cannot keep them fully private. The design therefore needs a small host contract, explicit child lifecycle coordination, and clean resync behavior on init and resize.

The repo already contains sprite assets for `gui_background`, `inventory_slot`, `crafting_result_slot`, and `crafting_arrow`, and `gui_background` already has a `.mcmeta` file for 9-slice behavior. The implementation should build around those existing resources rather than inventing a parallel texture metadata system.

## Goals / Non-Goals

**Goals:**

- Provide a reusable root `AbstractWidget` that spans the full screen and can compose GUI widgets in code.
- Support both `Screen` and `AbstractContainerScreen` through a single host interface that exposes all required screen metrics.
- Keep child widgets as `AbstractWidget` instances so they can use normal Minecraft screen input, focus, and rendering behavior.
- Automatically register and deregister root-managed children with the host screen during init and re-init.
- Introduce a `GuiTexture` style wrapper with default render sizing and a `GuiTextures` constants holder for built-in sprites.
- Add a minimal first component set centered on background and crafting/inventory slot visuals.

**Non-Goals:**

- Add clipping or scissor management.
- Build a full layout engine, anchors, or constraint system.
- Add non-widget render-only component types.
- Duplicate Minecraft's 9-slice metadata in code.

## Decisions

### Use a single host interface instead of individual suppliers

The host screen will implement a dedicated interface exposing `leftPos`, `topPos`, `width`, `height`, `imageWidth`, and `imageHeight`, plus screen registration hooks needed by the root widget. This keeps the API cohesive and readable, and it allows either direct screen implementations or lightweight anonymous adapters when needed.

Alternative considered: separate suppliers/callbacks for each metric. This was rejected because it fragments the API, makes construction noisy, and makes later lifecycle additions harder to keep consistent.

### Make the root widget screen-wide and make children use screen-space coordinates

The root widget itself will cover the full screen. Child widgets will also use screen-space coordinates so the root can register them directly with the host screen without translating every input path through a local coordinate system.

Alternative considered: root-relative child coordinates. This was initially attractive for composition, but it complicates auto-registration with the host screen and would require extra translation logic for rendering and input. The decision favors simpler lifecycle and interoperability with Minecraft's widget system.

### Keep every component as an `AbstractWidget`

All initial components and the root container integration will be built around `AbstractWidget`. This keeps the component model aligned with Minecraft's existing focus, hover, narration, and event dispatch expectations.

Alternative considered: mixing render-only components with widgets. This was rejected for the first version because it would create two component lifecycles and weaken the goal of simple host registration.

### Separate texture wrappers from GUI components

`GuiTexture` objects will describe a sprite identifier, default render size, and an optional tint value that defaults to `-1`, while GUI components remain separate widget classes that decide how to use those textures. This keeps rendering assets reusable without turning the texture wrapper layer into a component framework.

Alternative considered: embedding component behavior or 9-slice parameters directly in texture definitions. This was rejected because 9-slice metadata is already provided by Minecraft resource metadata and the user explicitly wants widgets/components to remain separate from texture wrappers.

### Let the background component default to host image dimensions but allow overrides

The 9-slice background widget will default to the host's `imageWidth` and `imageHeight`, which matches the familiar centered inventory-screen model. It will also allow explicit position and size overrides so multiple backgrounds can be stacked or offset in one screen.

Alternative considered: requiring all background dimensions to be specified manually. This was rejected because it would make the most common case verbose and would not reflect the intended default behavior.

### Rebuild child registration during init and resize

The root widget will own registration bookkeeping so it can unregister old children and re-register the current set when the host screen reinitializes. This avoids duplicate widgets in the screen lists and keeps resize behavior deterministic.

Alternative considered: one-time registration only. This was rejected because Minecraft screens commonly recreate or reset widget state on init, and the feature needs to stay correct across resize/re-init.

## Risks / Trade-offs

- Child widgets stored in both the root and the host screen can drift out of sync if registration bookkeeping is incomplete. → Centralize add/remove logic in the root widget and make re-sync idempotent.
- A host interface tied too closely to current screen classes may make future extension awkward. → Keep the interface focused on stable GUI metrics and registration operations, not concrete screen internals.
- Screen-space child coordinates are simpler for registration but less convenient for nested composition. → Keep the initial API minimal and defer nested layout abstractions until a concrete need appears.
- Default background sizing based on `imageWidth`/`imageHeight` assumes the host provides meaningful values. → Define those values as required contract data in the spec and design examples around both plain and container screens.
