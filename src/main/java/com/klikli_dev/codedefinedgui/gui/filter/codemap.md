# src/main/java/com/klikli_dev/codedefinedgui/gui/filter/

## Responsibility
Implements client screens for editing filter items and rendering their slot layouts.

## Design
- `AbstractFilterScreen` provides shared geometry, widget orchestration, and button plumbing.
- `ListFilterScreen` and `AttributeFilterScreen` specialize the base for their respective menu backends.
- `widget/` contains filter-specific widgets that sit on top of the reusable GUI widget set and are composed directly by the filter screens.
- `SlotSkinRenderer` and `SlotSkinRendererRegistry` translate slot skin metadata into rendered sprites.

## Flow
1. NeoForge opens the screen registered for a menu type.
2. `AbstractFilterScreen` builds the background, slot widgets, player inventory frame, and root widget tree using `GuiHost` coordinates.
3. Concrete screens wire filter-specific controls and indicators from `widget/` plus reusable buttons from `../widget/`.
4. User actions call `menu.clickMenuButton(...)`, which mutates draft menu state and then re-renders.

## Integration
- Backed by `../../filter/core/FilterMenu.java`, `../../filter/list/ListFilterMenu.java`, and `../../filter/attribute/AttributeFilterMenu.java`
- Consumes slot metadata from `../../filter/core/layout/`
- Uses generic widgets from `../widget/`, filter widgets from `widget/`, and sprites from `../texture/`
