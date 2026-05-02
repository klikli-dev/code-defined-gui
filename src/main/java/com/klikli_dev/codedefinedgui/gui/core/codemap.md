# src/main/java/com/klikli_dev/codedefinedgui/gui/core/

## Responsibility
Provides the composition root and synchronization contracts for custom GUI widgets.

## Design
- `GuiHost` abstracts the screen surface a widget tree attaches to and provides GUI-relative coordinate helpers for screen-local widget placement.
- `GuiRootWidget` owns widget registration, attachment, and batch synchronization.
- `GuiSyncable` marks widgets that can resync their bounds or state.

## Flow
1. A screen instantiates `GuiRootWidget`.
2. Screens resolve GUI-relative positions through `GuiHost.guiX()` and `GuiHost.guiY()`.
3. Widgets are added to the root.
4. The root propagates host attachment and resync calls to children.

## Integration
- Used by `../filter/AbstractFilterScreen.java` and `../../example/screen/TestScreen.java`
