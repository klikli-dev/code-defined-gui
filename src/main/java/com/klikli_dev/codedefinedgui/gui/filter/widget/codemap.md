# src/main/java/com/klikli_dev/codedefinedgui/gui/filter/widget/

## Responsibility
Contains filter-specific widgets layered on top of the reusable GUI widget library.

## Design
- `FilterIndicatorWidget` renders simple on/off state markers for filter toggles.
- `AttributeSelectionWidget` presents the current attribute candidate and tooltip-driven browsing.
- `AttributeRuleSummaryWidget` summarizes the current attribute ruleset with an item preview.

## Flow
1. Filter screens instantiate these widgets directly from `gui/filter/` with GUI-relative coordinates resolved through `GuiHost`.
2. Widgets render menu-backed filter state and expose small interaction hooks back into the owning screen.

## Integration
- Used by `../AbstractFilterScreen.java`, `../ListFilterScreen.java`, and `../AttributeFilterScreen.java`
- Depends on generic textures in `../../texture/` and generic widget conventions from `../../widget/`
