# src/main/java/com/klikli_dev/codedefinedgui/gui/widget/

## Responsibility
Contains reusable custom widgets and decorative UI elements shared across screens.

## Design
- Rendering primitives: `GuiSpriteWidget`, `GuiBackgroundWidget`, `FrameWidget`, separators.
- Interactive controls: `IconButtonWidget` with `IconButtonBackgroundSprites`.
- Filter-specific widgets live under `../filter/widget/` so the generic widget package stays library-oriented.

## Flow
Screens compose these widgets under `GuiRootWidget`, then pair them with screen-local filter widgets or demo-specific layout code.

## Integration
- Consumed by `../filter/` screens and `../../example/screen/TestScreen.java`
- Renders textures referenced by `../texture/GuiSprites.java`
