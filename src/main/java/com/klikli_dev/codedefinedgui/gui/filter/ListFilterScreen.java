// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.filter;

import com.klikli_dev.codedefinedgui.CodeDefinedGuiConstants;
import com.klikli_dev.codedefinedgui.filter.core.layout.BuiltinFilterParts;
import com.klikli_dev.codedefinedgui.filter.list.ListFilterMenu;
import com.klikli_dev.codedefinedgui.gui.filter.widget.FilterIndicatorWidget;
import com.klikli_dev.codedefinedgui.gui.style.GuiStyleProperties;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprites;
import com.klikli_dev.codedefinedgui.gui.widget.GuiBackgroundWidget;
import com.klikli_dev.codedefinedgui.gui.widget.HorizontalSeparatorWidget;
import com.klikli_dev.codedefinedgui.gui.widget.IconButtonWidget;
import com.klikli_dev.codedefinedgui.gui.widget.VerticalSeparatorWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ListFilterScreen<M extends ListFilterMenu> extends AbstractFilterScreen<M> {
    private IconButtonWidget allowButton;
    private IconButtonWidget denyButton;
    private IconButtonWidget respectDataButton;
    private IconButtonWidget ignoreDataButton;
    private FilterIndicatorWidget allowIndicator;
    private FilterIndicatorWidget denyIndicator;
    private FilterIndicatorWidget respectDataIndicator;
    private FilterIndicatorWidget ignoreDataIndicator;

    public ListFilterScreen(M menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 214, 211);
        this.titleLabelX = 103 - this.font.width(title) / 2;
        this.titleLabelY = 6;
    }

    @Override
    protected void addBackgroundWidgets() {
        this.addRootChild(new GuiBackgroundWidget(this, this.guiX(3), this.guiY(12), this.imageWidth() - 6, 87, this.partSprite(BuiltinFilterParts.LIST_PANEL, GuiSprites.GUI_BACKGROUND)));
        this.addRootChild(new GuiBackgroundWidget(this, this.guiX(0), this.guiY(0), this.imageWidth(), 15, this.partSprite(BuiltinFilterParts.LIST_TOP_BAR, GuiSprites.GUI_BACKGROUND)));
    }

    @Override
    protected void addScreenWidgets() {
        this.resetButton = this.addResetButton(this.guiX(152), this.guiY(75), ListFilterMenu.BUTTON_RESET);
        this.confirmButton = this.addConfirmButton(this.guiX(181), this.guiY(75));

        this.denyButton = this.addIconButton(this.guiX(18), this.guiY(75), GuiSprites.FILTER_ICON_DENY_LIST, Component.translatable(CodeDefinedGuiConstants.I18n.Filter.List.Mode.DENY), () -> this.pressButton(ListFilterMenu.BUTTON_DENY))
                .withTooltip(Component.translatable(CodeDefinedGuiConstants.I18n.Filter.List.Mode.DENY_TOOLTIP), Component.translatable(CodeDefinedGuiConstants.I18n.Filter.List.Mode.DENY_TOOLTIP_SHIFT));
        this.allowButton = this.addIconButton(this.guiX(36), this.guiY(75), GuiSprites.FILTER_ICON_ALLOW_LIST, Component.translatable(CodeDefinedGuiConstants.I18n.Filter.List.Mode.ALLOW), () -> this.pressButton(ListFilterMenu.BUTTON_ALLOW))
                .withTooltip(Component.translatable(CodeDefinedGuiConstants.I18n.Filter.List.Mode.ALLOW_TOOLTIP), Component.translatable(CodeDefinedGuiConstants.I18n.Filter.List.Mode.ALLOW_TOOLTIP_SHIFT));
        this.respectDataButton = this.addIconButton(this.guiX(60), this.guiY(75), GuiSprites.FILTER_ICON_RESPECT_DATA_COMPONENTS, Component.translatable(CodeDefinedGuiConstants.I18n.Filter.List.RESPECT_DATA), () -> this.pressButton(ListFilterMenu.BUTTON_RESPECT_DATA))
                .withTooltip(Component.translatable(CodeDefinedGuiConstants.I18n.Filter.List.RESPECT_DATA_TOOLTIP), Component.translatable(CodeDefinedGuiConstants.I18n.Filter.List.RESPECT_DATA_TOOLTIP_SHIFT));
        this.ignoreDataButton = this.addIconButton(this.guiX(78), this.guiY(75), GuiSprites.FILTER_ICON_IGNORE_DATA_COMPONENTS, Component.translatable(CodeDefinedGuiConstants.I18n.Filter.List.IGNORE_DATA), () -> this.pressButton(ListFilterMenu.BUTTON_IGNORE_DATA))
                .withTooltip(Component.translatable(CodeDefinedGuiConstants.I18n.Filter.List.IGNORE_DATA_TOOLTIP), Component.translatable(CodeDefinedGuiConstants.I18n.Filter.List.IGNORE_DATA_TOOLTIP_SHIFT));

        this.denyIndicator = this.addFilterIndicator(this.guiX(18), this.guiY(69));
        this.allowIndicator = this.addFilterIndicator(this.guiX(36), this.guiY(69));
        this.respectDataIndicator = this.addFilterIndicator(this.guiX(60), this.guiY(69));
        this.ignoreDataIndicator = this.addFilterIndicator(this.guiX(78), this.guiY(69));

        this.addRootChild(new HorizontalSeparatorWidget(this.guiX(3), this.guiY(64), this.imageWidth() - 6, this.style().get(BuiltinFilterParts.LIST_HORIZONTAL_SEPARATOR, GuiStyleProperties.COLOR, 0xFF000000)));
        this.addRootChild(new VerticalSeparatorWidget(this.guiX(145), this.guiY(64), 35, this.style().get(BuiltinFilterParts.LIST_VERTICAL_SEPARATOR, GuiStyleProperties.COLOR, 0xFF000000)));
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
        return this.style().get(BuiltinFilterParts.LIST_TITLE, GuiStyleProperties.TEXT_COLOR, 0x303030);
    }

    @Override
    protected int confirmButtonId() {
        return ListFilterMenu.BUTTON_CONFIRM;
    }

    @Override
    protected int cancelButtonId() {
        return ListFilterMenu.BUTTON_CANCEL;
    }
}
