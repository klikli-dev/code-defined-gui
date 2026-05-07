// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.filter;

import com.klikli_dev.codedefinedgui.CodeDefinedGuiConstants;
import com.klikli_dev.codedefinedgui.filter.core.layout.BuiltinFilterParts;
import com.klikli_dev.codedefinedgui.filter.list.ListFilterMenu;
import com.klikli_dev.codedefinedgui.gui.filter.widget.FilterIndicatorWidget;
import com.klikli_dev.codedefinedgui.api.layout.LayoutResolverRegistry;
import com.klikli_dev.codedefinedgui.gui.style.GuiStyleProperties;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprites;
import com.klikli_dev.codedefinedgui.gui.widget.GuiBackgroundWidget;
import com.klikli_dev.codedefinedgui.gui.widget.GuiTextWidget;
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
    }

    @Override
    public void registerResolvers(LayoutResolverRegistry registry) {
        super.registerResolvers(registry);
        registry.resolve("main.filter_area.panel_bg", -100, ctx -> ctx.addWidget(new GuiBackgroundWidget(this, ctx.node().x(), ctx.node().y(), ctx.node().widthOrThrow(), ctx.node().heightOrThrow(), ctx.style().sprite(BuiltinFilterParts.LIST_PANEL, GuiSprites.GUI_BACKGROUND))));
        registry.resolve("main.top_bar.background", 100, ctx -> ctx.addWidget(new GuiBackgroundWidget(this, ctx.node().x(), ctx.node().y(), ctx.node().widthOrThrow(), ctx.node().heightOrThrow(), ctx.style().sprite(BuiltinFilterParts.LIST_TOP_BAR, GuiSprites.GUI_BACKGROUND))));
        registry.resolve("main.top_bar.title", 200, ctx -> {
            int titleX = ctx.node().x() + (ctx.node().widthOrThrow() - this.font.width(this.title)) / 2;
            ctx.addWidget(new GuiTextWidget(titleX, ctx.node().y(), () -> this.title, () -> ctx.style().textColor(BuiltinFilterParts.LIST_TITLE, 0xFF000000), false));
        });
        registry.resolve("main.filter_area.reset", ctx -> this.resetButton = this.addResetButton(ctx, ListFilterMenu.BUTTON_RESET));
        registry.resolve("main.filter_area.confirm", ctx -> this.confirmButton = this.addConfirmButton(ctx));
        registry.resolve("main.filter_area.deny", ctx -> {
            this.denyButton = this.addIconButton(ctx, GuiSprites.FILTER_ICON_DENY_LIST, Component.translatable(CodeDefinedGuiConstants.I18n.Filter.List.Mode.DENY), () -> this.pressButton(ListFilterMenu.BUTTON_DENY))
                    .withTooltip(Component.translatable(CodeDefinedGuiConstants.I18n.Filter.List.Mode.DENY_TOOLTIP), Component.translatable(CodeDefinedGuiConstants.I18n.Filter.List.Mode.DENY_TOOLTIP_SHIFT));
        });
        registry.resolve("main.filter_area.allow", ctx -> {
            this.allowButton = this.addIconButton(ctx, GuiSprites.FILTER_ICON_ALLOW_LIST, Component.translatable(CodeDefinedGuiConstants.I18n.Filter.List.Mode.ALLOW), () -> this.pressButton(ListFilterMenu.BUTTON_ALLOW))
                    .withTooltip(Component.translatable(CodeDefinedGuiConstants.I18n.Filter.List.Mode.ALLOW_TOOLTIP), Component.translatable(CodeDefinedGuiConstants.I18n.Filter.List.Mode.ALLOW_TOOLTIP_SHIFT));
        });
        registry.resolve("main.filter_area.respect_data", ctx -> {
            this.respectDataButton = this.addIconButton(ctx, GuiSprites.FILTER_ICON_RESPECT_DATA_COMPONENTS, Component.translatable(CodeDefinedGuiConstants.I18n.Filter.List.RESPECT_DATA), () -> this.pressButton(ListFilterMenu.BUTTON_RESPECT_DATA))
                    .withTooltip(Component.translatable(CodeDefinedGuiConstants.I18n.Filter.List.RESPECT_DATA_TOOLTIP), Component.translatable(CodeDefinedGuiConstants.I18n.Filter.List.RESPECT_DATA_TOOLTIP_SHIFT));
        });
        registry.resolve("main.filter_area.ignore_data", ctx -> {
            this.ignoreDataButton = this.addIconButton(ctx, GuiSprites.FILTER_ICON_IGNORE_DATA_COMPONENTS, Component.translatable(CodeDefinedGuiConstants.I18n.Filter.List.IGNORE_DATA), () -> this.pressButton(ListFilterMenu.BUTTON_IGNORE_DATA))
                    .withTooltip(Component.translatable(CodeDefinedGuiConstants.I18n.Filter.List.IGNORE_DATA_TOOLTIP), Component.translatable(CodeDefinedGuiConstants.I18n.Filter.List.IGNORE_DATA_TOOLTIP_SHIFT));
        });
        registry.resolve("main.filter_area.deny_indicator", ctx -> this.denyIndicator = this.addFilterIndicator(ctx));
        registry.resolve("main.filter_area.allow_indicator", ctx -> this.allowIndicator = this.addFilterIndicator(ctx));
        registry.resolve("main.filter_area.respect_data_indicator", ctx -> this.respectDataIndicator = this.addFilterIndicator(ctx));
        registry.resolve("main.filter_area.ignore_data_indicator", ctx -> this.ignoreDataIndicator = this.addFilterIndicator(ctx));
        registry.resolve("main.filter_area.horizontal_separator", ctx -> this.addRootChild(new HorizontalSeparatorWidget(ctx.node().x(), ctx.node().y(), ctx.node().widthOrThrow(), ctx.style().color(BuiltinFilterParts.LIST_HORIZONTAL_SEPARATOR, 0xFF000000))));
        registry.resolve("main.filter_area.vertical_separator", ctx -> this.addRootChild(new VerticalSeparatorWidget(ctx.node().x(), ctx.node().y(), ctx.node().heightOrThrow(), ctx.style().color(BuiltinFilterParts.LIST_VERTICAL_SEPARATOR, 0xFF000000))));
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
    protected int confirmButtonId() {
        return ListFilterMenu.BUTTON_CONFIRM;
    }

    @Override
    protected int cancelButtonId() {
        return ListFilterMenu.BUTTON_CANCEL;
    }
}
