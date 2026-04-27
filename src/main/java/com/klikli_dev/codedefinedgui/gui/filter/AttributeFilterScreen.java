// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.filter;

import com.klikli_dev.codedefinedgui.filter.attribute.AttributeCandidate;
import com.klikli_dev.codedefinedgui.filter.attribute.AttributeFilterDefinition;
import com.klikli_dev.codedefinedgui.filter.attribute.AttributeFilterMenu;
import com.klikli_dev.codedefinedgui.filter.attribute.AttributeFilterMode;
import com.klikli_dev.codedefinedgui.gui.AttributeRuleSummaryWidget;
import com.klikli_dev.codedefinedgui.gui.AttributeSelectionWidget;
import com.klikli_dev.codedefinedgui.gui.FilterIndicatorWidget;
import com.klikli_dev.codedefinedgui.gui.GuiBackgroundWidget;
import com.klikli_dev.codedefinedgui.gui.GuiTextures;
import com.klikli_dev.codedefinedgui.gui.IconButtonWidget;
import com.klikli_dev.codedefinedgui.gui.InventorySlotWidget;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class AttributeFilterScreen extends AbstractFilterScreen<AttributeFilterMenu> {
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

    public AttributeFilterScreen(AttributeFilterMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 241, 197);
        this.titleLabelX = 116 - this.font.width(title) / 2;
        this.titleLabelY = 6;
    }

    @Override
    protected void addBackgroundWidgets() {
        this.root.addChild(new GuiBackgroundWidget(this, this.leftPos, this.topPos, this.imageWidth, 85));
        this.root.addChild(new InventorySlotWidget(this.leftPos + 16, this.topPos + 24));
    }

    @Override
    protected void addScreenWidgets() {
        this.resetButton = this.root.addChild(new IconButtonWidget(
                this.leftPos + 179,
                this.topPos + 61,
                GuiTextures.FILTER_ICON_RESET,
                Component.translatable("codedefinedgui.filter.button.reset"),
                () -> this.pressButton(AttributeFilterMenu.BUTTON_RESET)
        ).withTooltip(Component.translatable("codedefinedgui.filter.button.reset")));
        this.confirmButton = this.root.addChild(new IconButtonWidget(
                this.leftPos + 208,
                this.topPos + 61,
                GuiTextures.FILTER_ICON_CONFIRM,
                Component.translatable("codedefinedgui.filter.button.done"),
                this::onClose
        ).withTooltip(Component.translatable("codedefinedgui.filter.button.done")));

        this.matchAnyButton = this.root.addChild(new IconButtonWidget(this.leftPos + 47, this.topPos + 61, GuiTextures.FILTER_ICON_MATCH_ANY, Component.translatable("codedefinedgui.filter.attribute.mode.match_any"), () -> this.pressButton(AttributeFilterMenu.BUTTON_MATCH_ANY)));
        this.matchAllButton = this.root.addChild(new IconButtonWidget(this.leftPos + 65, this.topPos + 61, GuiTextures.FILTER_ICON_MATCH_ALL, Component.translatable("codedefinedgui.filter.attribute.mode.match_all"), () -> this.pressButton(AttributeFilterMenu.BUTTON_MATCH_ALL)));
        this.denyButton = this.root.addChild(new IconButtonWidget(this.leftPos + 83, this.topPos + 61, GuiTextures.FILTER_ICON_DENY_ALT, Component.translatable("codedefinedgui.filter.attribute.mode.deny"), () -> this.pressButton(AttributeFilterMenu.BUTTON_DENY)));
        this.matchAnyIndicator = this.root.addChild(new FilterIndicatorWidget(this.leftPos + 47, this.topPos + 55));
        this.matchAllIndicator = this.root.addChild(new FilterIndicatorWidget(this.leftPos + 65, this.topPos + 55));
        this.denyIndicator = this.root.addChild(new FilterIndicatorWidget(this.leftPos + 83, this.topPos + 55));

        this.selectionWidget = this.root.addChild(new AttributeSelectionWidget(
                this.leftPos + 39,
                this.topPos + 23,
                137,
                18,
                this::candidates,
                this.menu::selectedCandidateIndex,
                ignored -> this.pressButton(AttributeFilterMenu.BUTTON_NEXT_CANDIDATE)
        ));
        this.addButton = this.root.addChild(new IconButtonWidget(this.leftPos + 182, this.topPos + 23, GuiTextures.FILTER_ICON_ADD, Component.translatable("codedefinedgui.filter.attribute.add"), () -> this.pressButton(AttributeFilterMenu.BUTTON_ADD_SELECTED)));
        this.addInvertedButton = this.root.addChild(new IconButtonWidget(this.leftPos + 200, this.topPos + 23, GuiTextures.FILTER_ICON_ADD_INVERTED, Component.translatable("codedefinedgui.filter.attribute.add_inverted"), () -> this.pressButton(AttributeFilterMenu.BUTTON_ADD_SELECTED_INVERTED)));
        this.summaryWidget = this.root.addChild(new AttributeRuleSummaryWidget(this.leftPos + 18, this.topPos + 55, () -> this.menu.state().rules().size()));
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
        this.addButton.active = hasCandidate;
        this.addInvertedButton.active = hasCandidate;
    }

    @Override
    protected int titleColor() {
        return 0x592424;
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
}
