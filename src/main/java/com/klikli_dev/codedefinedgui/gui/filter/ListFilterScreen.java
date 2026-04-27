// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.filter;

import com.klikli_dev.codedefinedgui.filter.list.ListFilterMenu;
import com.klikli_dev.codedefinedgui.gui.FilterIndicatorWidget;
import com.klikli_dev.codedefinedgui.gui.GuiBackgroundWidget;
import com.klikli_dev.codedefinedgui.gui.GuiTextures;
import com.klikli_dev.codedefinedgui.gui.IconButtonWidget;
import com.klikli_dev.codedefinedgui.gui.InventorySlotWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ListFilterScreen extends AbstractFilterScreen<ListFilterMenu> {
    private IconButtonWidget allowButton;
    private IconButtonWidget denyButton;
    private IconButtonWidget respectDataButton;
    private IconButtonWidget ignoreDataButton;
    private FilterIndicatorWidget allowIndicator;
    private FilterIndicatorWidget denyIndicator;
    private FilterIndicatorWidget respectDataIndicator;
    private FilterIndicatorWidget ignoreDataIndicator;

    public ListFilterScreen(ListFilterMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 214, 211);
        this.titleLabelX = 103 - this.font.width(title) / 2;
        this.titleLabelY = 6;
    }

    @Override
    protected void addBackgroundWidgets() {
        this.root.addChild(new GuiBackgroundWidget(this, this.leftPos, this.topPos, this.imageWidth, 99));
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 9; col++) {
                this.root.addChild(new InventorySlotWidget(this.leftPos + 25 + col * 18, this.topPos + 24 + row * 18));
            }
        }
    }

    @Override
    protected void addScreenWidgets() {
        this.resetButton = this.root.addChild(new IconButtonWidget(
                this.leftPos + 152,
                this.topPos + 75,
                GuiTextures.FILTER_ICON_RESET,
                Component.translatable("codedefinedgui.filter.button.reset"),
                () -> this.pressButton(ListFilterMenu.BUTTON_RESET)
        ).withTooltip(Component.translatable("codedefinedgui.filter.button.reset")));
        this.confirmButton = this.root.addChild(new IconButtonWidget(
                this.leftPos + 181,
                this.topPos + 75,
                GuiTextures.FILTER_ICON_CONFIRM,
                Component.translatable("codedefinedgui.filter.button.done"),
                this::onClose
        ).withTooltip(Component.translatable("codedefinedgui.filter.button.done")));

        this.denyButton = this.root.addChild(new IconButtonWidget(this.leftPos + 18, this.topPos + 75, GuiTextures.FILTER_ICON_DENY_LIST, Component.translatable("codedefinedgui.filter.list.mode.deny"), () -> this.pressButton(ListFilterMenu.BUTTON_DENY)));
        this.allowButton = this.root.addChild(new IconButtonWidget(this.leftPos + 36, this.topPos + 75, GuiTextures.FILTER_ICON_ALLOW_LIST, Component.translatable("codedefinedgui.filter.list.mode.allow"), () -> this.pressButton(ListFilterMenu.BUTTON_ALLOW)));
        this.respectDataButton = this.root.addChild(new IconButtonWidget(this.leftPos + 60, this.topPos + 75, GuiTextures.FILTER_ICON_RESPECT_DATA_COMPONENTS, Component.translatable("codedefinedgui.filter.list.respect_data"), () -> this.pressButton(ListFilterMenu.BUTTON_RESPECT_DATA)));
        this.ignoreDataButton = this.root.addChild(new IconButtonWidget(this.leftPos + 78, this.topPos + 75, GuiTextures.FILTER_ICON_IGNORE_DATA_COMPONENTS, Component.translatable("codedefinedgui.filter.list.ignore_data"), () -> this.pressButton(ListFilterMenu.BUTTON_IGNORE_DATA)));

        this.denyIndicator = this.root.addChild(new FilterIndicatorWidget(this.leftPos + 18, this.topPos + 69));
        this.allowIndicator = this.root.addChild(new FilterIndicatorWidget(this.leftPos + 36, this.topPos + 69));
        this.respectDataIndicator = this.root.addChild(new FilterIndicatorWidget(this.leftPos + 60, this.topPos + 69));
        this.ignoreDataIndicator = this.root.addChild(new FilterIndicatorWidget(this.leftPos + 78, this.topPos + 69));
    }

    @Override
    protected void refreshWidgetState() {
        boolean deny = this.menu.isDenyList();
        boolean respectData = this.menu.respectDataComponents();
        this.allowButton.active = deny;
        this.denyButton.active = !deny;
        this.respectDataButton.active = !respectData;
        this.ignoreDataButton.active = respectData;
        this.allowIndicator.setOn(!deny);
        this.denyIndicator.setOn(deny);
        this.respectDataIndicator.setOn(respectData);
        this.ignoreDataIndicator.setOn(!respectData);
    }

    @Override
    protected int titleColor() {
        return 0x303030;
    }

    private void pressButton(int buttonId) {
        if (this.menu.clickMenuButton(this.minecraft.player, buttonId)) {
            this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, buttonId);
        }
    }
}
