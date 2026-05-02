# src/main/java/com/klikli_dev/codedefinedgui/gui/

## Responsibility
Owns the client UI framework used by filter screens and demo screens.

## Design
- `core/` defines the host/root widget coordination layer and GUI-relative positioning helpers.
- `filter/` contains menu-backed filter screens, slot-skin rendering helpers, and a filter-specific widget subpackage.
- `texture/` wraps sprite resource identifiers.
- `widget/` contains reusable renderable controls and decorations only.

## Flow
1. The client binds a menu type to a screen class.
2. Screens create a `GuiRootWidget` through `GuiHost`, then populate it with reusable widgets and filter-local controls.
3. Widgets render using textures referenced through `GuiSprite`/`GuiSprites`.

## Integration
- Child maps: `core/codemap.md`, `filter/codemap.md`, `texture/codemap.md`, `widget/codemap.md`
- Driven by `../CodeDefinedGuiClient.java` and backed by `../filter/`
