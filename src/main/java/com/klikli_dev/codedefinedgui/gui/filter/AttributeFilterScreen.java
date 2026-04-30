// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.filter;

import com.klikli_dev.codedefinedgui.filter.attribute.AttributeCandidate;
import com.klikli_dev.codedefinedgui.filter.attribute.AttributeFilterDefinition;
import com.klikli_dev.codedefinedgui.filter.attribute.AttributeFilterMenu;
import com.klikli_dev.codedefinedgui.filter.attribute.AttributeFilterMode;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprite;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprites;
import com.klikli_dev.codedefinedgui.gui.widget.AttributeRuleSummaryWidget;
import com.klikli_dev.codedefinedgui.gui.widget.AttributeSelectionWidget;
import com.klikli_dev.codedefinedgui.gui.widget.FilterIndicatorWidget;
import com.klikli_dev.codedefinedgui.gui.widget.GuiBackgroundWidget;
import com.klikli_dev.codedefinedgui.gui.widget.GuiSpriteWidget;
import com.klikli_dev.codedefinedgui.gui.widget.IconButtonWidget;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class AttributeFilterScreen<M extends AttributeFilterMenu> extends AbstractFilterScreen<M> {
    private static final int REFERENCE_SLOT_X = 18;
    private static final int ATTRIBUTE_SELECTION_X = 42;
    private static final int ADD_BUTTON_X = 190;
    private static final int ADD_INVERTED_BUTTON_X = 208;

    private boolean closingHandled;
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
        this.root.addChild(new GuiBackgroundWidget(this, this.leftPos, this.topPos, this.imageWidth, 85));
    }

    @Override
    protected void addFilterSlotWidgets() {
        GuiSprite filterSlotSprite = this.filterSlotSprite();
        this.root.addChild(new GuiSpriteWidget(this.leftPos + REFERENCE_SLOT_X, this.topPos + 23, filterSlotSprite));
        this.root.addChild(new GuiSpriteWidget(this.leftPos + 21, this.topPos + 58, filterSlotSprite));
    }

    @Override
    protected void addScreenWidgets() {
        var buttonBackgroundSprites = this.buttonBackgroundSprites();
        this.resetButton = this.root.addChild(new IconButtonWidget(
                this.leftPos + 179,
                this.topPos + 61,
                GuiSprites.FILTER_ICON_RESET,
                buttonBackgroundSprites,
                Component.translatable("codedefinedgui.filter.button.reset"),
                () -> this.pressButton(AttributeFilterMenu.BUTTON_RESET)
        ).withTooltip(Component.translatable("codedefinedgui.filter.button.reset.tooltip")));
        this.confirmButton = this.root.addChild(new IconButtonWidget(
                this.leftPos + 208,
                this.topPos + 61,
                GuiSprites.FILTER_ICON_CONFIRM,
                buttonBackgroundSprites,
                Component.translatable("codedefinedgui.filter.button.done"),
                () -> this.closeScreen(true)
        ).withTooltip(Component.translatable("codedefinedgui.filter.button.done.tooltip")));

        this.matchAnyButton = this.root.addChild(new IconButtonWidget(this.leftPos + 47, this.topPos + 61, GuiSprites.FILTER_ICON_MATCH_ANY, buttonBackgroundSprites, Component.translatable("codedefinedgui.filter.attribute.mode.match_any"), () -> this.pressButton(AttributeFilterMenu.BUTTON_MATCH_ANY))
                .withTooltip(Component.translatable("codedefinedgui.filter.attribute.mode.match_any.tooltip"), Component.translatable("codedefinedgui.filter.attribute.mode.match_any.tooltip.shift")));
        this.matchAllButton = this.root.addChild(new IconButtonWidget(this.leftPos + 65, this.topPos + 61, GuiSprites.FILTER_ICON_MATCH_ALL, buttonBackgroundSprites, Component.translatable("codedefinedgui.filter.attribute.mode.match_all"), () -> this.pressButton(AttributeFilterMenu.BUTTON_MATCH_ALL))
                .withTooltip(Component.translatable("codedefinedgui.filter.attribute.mode.match_all.tooltip"), Component.translatable("codedefinedgui.filter.attribute.mode.match_all.tooltip.shift")));
        this.denyButton = this.root.addChild(new IconButtonWidget(this.leftPos + 83, this.topPos + 61, GuiSprites.FILTER_ICON_DENY_ALT, buttonBackgroundSprites, Component.translatable("codedefinedgui.filter.attribute.mode.deny"), () -> this.pressButton(AttributeFilterMenu.BUTTON_DENY))
                .withTooltip(Component.translatable("codedefinedgui.filter.attribute.mode.deny.tooltip"), Component.translatable("codedefinedgui.filter.attribute.mode.deny.tooltip.shift")));
        this.matchAnyIndicator = this.root.addChild(new FilterIndicatorWidget(this.leftPos + 47, this.topPos + 55));
        this.matchAllIndicator = this.root.addChild(new FilterIndicatorWidget(this.leftPos + 65, this.topPos + 55));
        this.denyIndicator = this.root.addChild(new FilterIndicatorWidget(this.leftPos + 83, this.topPos + 55));

        this.selectionWidget = this.root.addChild(new AttributeSelectionWidget(
                this.leftPos + ATTRIBUTE_SELECTION_X,
                this.topPos + 23,
                137,
                18,
                this.attributeSelectionSprite(),
                this::candidates,
                this.menu::selectedCandidateIndex,
                this::changeSelection
        ).withTitle(Component.translatable("codedefinedgui.filter.attribute.available")));
        this.addButton = this.root.addChild(new IconButtonWidget(this.leftPos + ADD_BUTTON_X, this.topPos + 23, GuiSprites.FILTER_ICON_ADD, buttonBackgroundSprites, Component.translatable("codedefinedgui.filter.attribute.add"), () -> this.pressButton(AttributeFilterMenu.BUTTON_ADD_SELECTED))
                .withTooltip(Component.translatable("codedefinedgui.filter.attribute.add.tooltip")));
        this.addInvertedButton = this.root.addChild(new IconButtonWidget(this.leftPos + ADD_INVERTED_BUTTON_X, this.topPos + 23, GuiSprites.FILTER_ICON_ADD_INVERTED, buttonBackgroundSprites, Component.translatable("codedefinedgui.filter.attribute.add_inverted"), () -> this.pressButton(AttributeFilterMenu.BUTTON_ADD_SELECTED_INVERTED))
                .withTooltip(Component.translatable("codedefinedgui.filter.attribute.add_inverted.tooltip")));
        this.summaryWidget = this.root.addChild(new AttributeRuleSummaryWidget(this.leftPos + 18, this.topPos + 55, this.attributeSummarySprite(), () -> this.menu.state().rules().size(), this.menu::summaryStack));
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
        return 0x592424;
    }

    protected GuiSprite filterSlotSprite() {
        return this.inventorySlotSprite();
    }

    protected GuiSprite attributeSelectionSprite() {
        return GuiSprites.ATTRIBUTE_FILTER_SELECTION;
    }

    protected GuiSprite attributeSummarySprite() {
        return GuiSprites.ATTRIBUTE_FILTER_SUMMARY;
    }

    @Override
    public void onClose() {
        this.closeScreen(false);
    }

    @Override
    protected void extractTooltip(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        if (this.summaryWidget != null && this.summaryWidget.isHovered() && this.minecraft != null && this.minecraft.player != null) {
            List<Component> tooltip = new ArrayList<>(AttributeFilterDefinition.INSTANCE.summary(this.menu.state(), this.minecraft.player.registryAccess()));
            if (tooltip.isEmpty()) {
                tooltip.add(Component.translatable("codedefinedgui.filter.attribute.no_rules"));
            }
            graphics.setComponentTooltipForNextFrame(this.font, tooltip, mouseX, mouseY);
            return;
        }

        super.extractTooltip(graphics, mouseX, mouseY);
    }

    private List<AttributeCandidate> candidates() {
        return this.menu.candidates();
    }

    private void pressButton(int buttonId) {
        if (this.menu.clickMenuButton(this.minecraft.player, buttonId)) {
            this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, buttonId);
        }
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

    private void closeScreen(boolean confirm) {
        if (this.closingHandled) {
            return;
        }

        this.closingHandled = true;
        this.pressButton(confirm ? AttributeFilterMenu.BUTTON_CONFIRM : AttributeFilterMenu.BUTTON_CANCEL);
        super.onClose();
    }
}
