## 1. Host And Texture Foundations

- [ ] 1.1 Add a GUI host interface that exposes `leftPos`, `topPos`, `width`, `height`, `imageWidth`, and `imageHeight`, plus the screen registration operations the root widget needs.
- [ ] 1.2 Implement the GUI texture wrapper class with sprite identity, default render size, an optional tint defaulting to `-1`, and a method for producing sized copies.
- [ ] 1.3 Add the `GuiTextures` constants holder for the built-in `gui_background`, `inventory_slot`, `crafting_result_slot`, and `crafting_arrow` sprite definitions.

## 2. Root Widget Lifecycle

- [ ] 2.1 Implement the screen-wide root `AbstractWidget` that accepts the host interface and maintains a managed child widget collection.
- [ ] 2.2 Add child add/remove APIs that automatically register and deregister child widgets with the host screen.
- [ ] 2.3 Implement re-sync logic so the root widget rebuilds child registration correctly during screen init and resize without duplicates.

## 3. Initial GUI Components

- [ ] 3.1 Implement the 9-slice background widget so it defaults to the host `imageWidth` and `imageHeight` but supports explicit screen-space position and size overrides.
- [ ] 3.2 Implement the inventory slot widget using the packaged sprite definition from `GuiTextures`.
- [ ] 3.3 Implement the crafting result slot widget using the packaged sprite definition from `GuiTextures`.
- [ ] 3.4 Implement the crafting arrow widget using the packaged sprite definition from `GuiTextures`.

## 4. Screen Integration And Validation

- [ ] 4.1 Update the existing test GUI screen to implement the host interface and construct the root widget with representative child components.
- [ ] 4.2 Verify the screen works with centered GUI placement data, child registration, and re-init behavior.
- [ ] 4.3 Run the relevant Gradle validation command for the edited GUI code and fix any compilation issues.
