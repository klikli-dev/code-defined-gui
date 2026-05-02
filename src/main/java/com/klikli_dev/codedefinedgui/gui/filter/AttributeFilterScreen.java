// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.filter;

import com.klikli_dev.codedefinedgui.CodeDefinedGuiConstants;
import com.klikli_dev.codedefinedgui.filter.attribute.AttributeCandidate;
import com.klikli_dev.codedefinedgui.filter.attribute.AttributeFilterDefinition;
import com.klikli_dev.codedefinedgui.filter.attribute.AttributeFilterMenu;
import com.klikli_dev.codedefinedgui.filter.attribute.AttributeFilterMode;
import com.klikli_dev.codedefinedgui.filter.core.layout.BuiltinFilterParts;
import com.klikli_dev.codedefinedgui.gui.filter.widget.AttributeRuleSummaryWidget;
import com.klikli_dev.codedefinedgui.gui.filter.widget.AttributeSelectionWidget;
import com.klikli_dev.codedefinedgui.gui.filter.widget.FilterIndicatorWidget;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprite;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprites;
import com.klikli_dev.codedefinedgui.gui.style.GuiStyleProperties;
import com.klikli_dev.codedefinedgui.gui.widget.GuiBackgroundWidget;
import com.klikli_dev.codedefinedgui.gui.widget.HorizontalSeparatorWidget;
import com.klikli_dev.codedefinedgui.gui.widget.IconButtonWidget;
import com.klikli_dev.codedefinedgui.gui.widget.VerticalSeparatorWidget;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class AttributeFilterScreen<M extends AttributeFilterMenu> extends AbstractFilterScreen<M> {
    private static final int ATTRIBUTE_SELECTION_X = 42;
    private static final int ADD_BUTTON_X = 190;
    private static final int ADD_INVERTED_BUTTON_X = 208;

    private IconButtonWidget matchAnyButton;
    private IconButtonWidget matchAllButton;
    private IconButtonWidget denyButton;
    private IconButtonWidget addButton;
    private IconButtonWidget addInvertedButton;
    private FilterIndicatorWidget matchAnyIndicator;
    private FilterIndicatorWidget matchAllIndicator;
    private FilterIndicatorWidget denyIndicator;
    private AttributeSelectionWidget selectionWidget;
    private AttributeRuleSummaryWidget summaryWidget;

    public AttributeFilterScreen(M menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 241, 197);
        this.titleLabelX = 116 - this.font.width(title) / 2;
        this.titleLabelY = 6;
    }

    @Override
    protected void addBackgroundWidgets() {
        this.addRootChild(new GuiBackgroundWidget(this, this.guiX(3), this.guiY(12), this.imageWidth() - 6, 75, this.partSprite(BuiltinFilterParts.ATTRIBUTE_PANEL, GuiSprites.GUI_BACKGROUND)));
        this.addRootChild(new GuiBackgroundWidget(this, this.guiX(0), this.guiY(0), this.imageWidth(), 15, this.partSprite(BuiltinFilterParts.ATTRIBUTE_TOP_BAR, GuiSprites.GUI_BACKGROUND)));
    }

    @Override
    protected void addScreenWidgets() {
        this.resetButton = this.addResetButton(this.guiX(179), this.guiY(61), AttributeFilterMenu.BUTTON_RESET);
        this.confirmButton = this.addConfirmButton(this.guiX(208), this.guiY(61));

        this.matchAnyButton = this.addIconButton(this.guiX(47), this.guiY(61), GuiSprites.FILTER_ICON_MATCH_ANY, Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.Mode.MATCH_ANY), () -> this.pressButton(AttributeFilterMenu.BUTTON_MATCH_ANY))
                .withTooltip(Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.Mode.MATCH_ANY_TOOLTIP), Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.Mode.MATCH_ANY_TOOLTIP_SHIFT));
        this.matchAllButton = this.addIconButton(this.guiX(65), this.guiY(61), GuiSprites.FILTER_ICON_MATCH_ALL, Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.Mode.MATCH_ALL), () -> this.pressButton(AttributeFilterMenu.BUTTON_MATCH_ALL))
                .withTooltip(Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.Mode.MATCH_ALL_TOOLTIP), Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.Mode.MATCH_ALL_TOOLTIP_SHIFT));
        this.denyButton = this.addIconButton(this.guiX(83), this.guiY(61), GuiSprites.FILTER_ICON_DENY_ALT, Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.Mode.DENY), () -> this.pressButton(AttributeFilterMenu.BUTTON_DENY))
                .withTooltip(Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.Mode.DENY_TOOLTIP), Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.Mode.DENY_TOOLTIP_SHIFT));
        this.matchAnyIndicator = this.addFilterIndicator(this.guiX(47), this.guiY(55));
        this.matchAllIndicator = this.addFilterIndicator(this.guiX(65), this.guiY(55));
        this.denyIndicator = this.addFilterIndicator(this.guiX(83), this.guiY(55));

        this.selectionWidget = this.root.addChild(new AttributeSelectionWidget(
                this.guiX(ATTRIBUTE_SELECTION_X),
                this.guiY(23),
                137,
                18,
                this.partSprite(BuiltinFilterParts.ATTRIBUTE_SELECTION, GuiSprites.ATTRIBUTE_FILTER_SELECTION),
                this::candidates,
                this.menu::selectedCandidateIndex,
                this::changeSelection
        ).withTitle(Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.AVAILABLE)));
        this.addButton = this.addIconButton(this.guiX(ADD_BUTTON_X), this.guiY(23), GuiSprites.FILTER_ICON_ADD, Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.ADD), () -> this.pressButton(AttributeFilterMenu.BUTTON_ADD_SELECTED))
                .withTooltip(Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.ADD_TOOLTIP));
        this.addInvertedButton = this.addIconButton(this.guiX(ADD_INVERTED_BUTTON_X), this.guiY(23), GuiSprites.FILTER_ICON_ADD_INVERTED, Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.ADD_INVERTED), () -> this.pressButton(AttributeFilterMenu.BUTTON_ADD_SELECTED_INVERTED))
                .withTooltip(Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.ADD_INVERTED_TOOLTIP));
        this.summaryWidget = this.root.addChild(new AttributeRuleSummaryWidget(this.guiX(18), this.guiY(55), this.partSprite(BuiltinFilterParts.ATTRIBUTE_SUMMARY, GuiSprites.ATTRIBUTE_FILTER_SUMMARY), () -> this.menu.state().rules().size(), this.menu::summaryStack));

        this.addRootChild(new HorizontalSeparatorWidget(this.guiX(3), this.guiY(48), this.imageWidth() - 6, this.style().get(BuiltinFilterParts.ATTRIBUTE_HORIZONTAL_SEPARATOR, GuiStyleProperties.COLOR, 0xFF000000)));
        this.addRootChild(new VerticalSeparatorWidget(this.guiX(202), this.guiY(48), 39, this.style().get(BuiltinFilterParts.ATTRIBUTE_VERTICAL_SEPARATOR, GuiStyleProperties.COLOR, 0xFF000000)));
    }

    @Override
    protected void refreshWidgetState() {
        AttributeFilterMode mode = this.menu.mode();
        this.matchAnyButton.active = mode != AttributeFilterMode.MATCH_ANY;
        this.matchAllButton.active = mode != AttributeFilterMode.MATCH_ALL;
        this.denyButton.active = mode != AttributeFilterMode.DENY;
        this.matchAnyIndicator.setOn(mode == AttributeFilterMode.MATCH_ANY);
        this.matchAllIndicator.setOn(mode == AttributeFilterMode.MATCH_ALL);
        this.denyIndicator.setOn(mode == AttributeFilterMode.DENY);
        boolean hasCandidate = this.menu.selectedCandidate().isPresent();
        boolean canAdd = hasCandidate && !this.menu.addLocked();
        this.addButton.active = canAdd;
        this.addInvertedButton.active = canAdd;
        this.selectionWidget.updateTooltip();
    }

    @Override
    protected int titleColor() {
        return this.style().get(BuiltinFilterParts.ATTRIBUTE_TITLE, GuiStyleProperties.TEXT_COLOR, 0x592424);
    }

    @Override
    protected int confirmButtonId() {
        return AttributeFilterMenu.BUTTON_CONFIRM;
    }

    @Override
    protected int cancelButtonId() {
        return AttributeFilterMenu.BUTTON_CANCEL;
    }

    @Override
    protected void extractTooltip(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        if (this.summaryWidget != null && this.summaryWidget.isHovered() && this.minecraft != null && this.minecraft.player != null) {
            List<Component> tooltip = new ArrayList<>(this.menu.summary(this.minecraft.player.registryAccess()));
            if (tooltip.isEmpty()) {
                tooltip.add(Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.NO_RULES));
            }
            graphics.setComponentTooltipForNextFrame(this.font, tooltip, mouseX, mouseY);
            return;
        }

        super.extractTooltip(graphics, mouseX, mouseY);
    }

    private List<AttributeCandidate> candidates() {
        return this.menu.candidates();
    }

    private void changeSelection(int nextIndex) {
        List<AttributeCandidate> candidates = this.candidates();
        if (candidates.isEmpty()) {
            return;
        }

        int currentIndex = Math.max(0, Math.min(this.menu.selectedCandidateIndex(), candidates.size() - 1));
        if (nextIndex == Math.floorMod(currentIndex - 1, candidates.size())) {
            this.pressButton(AttributeFilterMenu.BUTTON_PREVIOUS_CANDIDATE);
            return;
        }

        if (nextIndex != currentIndex) {
            this.pressButton(AttributeFilterMenu.BUTTON_NEXT_CANDIDATE);
        }

        this.selectionWidget.updateTooltip();
    }
}
