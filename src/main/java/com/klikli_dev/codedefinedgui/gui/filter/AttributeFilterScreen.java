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
import com.klikli_dev.codedefinedgui.gui.layout.LayoutResolverRegistry;
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
    public void registerResolvers(LayoutResolverRegistry registry) {
        super.registerResolvers(registry);
        registry.resolve("main.filter_area.panel_bg", -100, ctx -> ctx.addWidget(new GuiBackgroundWidget(this, ctx.node().x(), ctx.node().y(), ctx.node().widthOrThrow(), ctx.node().heightOrThrow(), this.partSprite(BuiltinFilterParts.ATTRIBUTE_PANEL, GuiSprites.GUI_BACKGROUND))));
        registry.resolve("main.top_bar.background", 100, ctx -> ctx.addWidget(new GuiBackgroundWidget(this, ctx.node().x(), ctx.node().y(), ctx.node().widthOrThrow(), ctx.node().heightOrThrow(), this.partSprite(BuiltinFilterParts.ATTRIBUTE_TOP_BAR, GuiSprites.GUI_BACKGROUND))));
        registry.resolve("main.filter_area.reset", ctx -> this.resetButton = this.addResetButton(ctx.node().x(), ctx.node().y(), AttributeFilterMenu.BUTTON_RESET));
        registry.resolve("main.filter_area.confirm", ctx -> this.confirmButton = this.addConfirmButton(ctx.node().x(), ctx.node().y()));
        registry.resolve("main.filter_area.match_any", ctx -> {
            this.matchAnyButton = this.addIconButton(ctx.node().x(), ctx.node().y(), GuiSprites.FILTER_ICON_MATCH_ANY, Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.Mode.MATCH_ANY), () -> this.pressButton(AttributeFilterMenu.BUTTON_MATCH_ANY))
                    .withTooltip(Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.Mode.MATCH_ANY_TOOLTIP), Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.Mode.MATCH_ANY_TOOLTIP_SHIFT));
        });
        registry.resolve("main.filter_area.match_all", ctx -> {
            this.matchAllButton = this.addIconButton(ctx.node().x(), ctx.node().y(), GuiSprites.FILTER_ICON_MATCH_ALL, Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.Mode.MATCH_ALL), () -> this.pressButton(AttributeFilterMenu.BUTTON_MATCH_ALL))
                    .withTooltip(Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.Mode.MATCH_ALL_TOOLTIP), Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.Mode.MATCH_ALL_TOOLTIP_SHIFT));
        });
        registry.resolve("main.filter_area.deny", ctx -> {
            this.denyButton = this.addIconButton(ctx.node().x(), ctx.node().y(), GuiSprites.FILTER_ICON_DENY_ALT, Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.Mode.DENY), () -> this.pressButton(AttributeFilterMenu.BUTTON_DENY))
                    .withTooltip(Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.Mode.DENY_TOOLTIP), Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.Mode.DENY_TOOLTIP_SHIFT));
        });
        registry.resolve("main.filter_area.match_any_indicator", ctx -> this.matchAnyIndicator = this.addFilterIndicator(ctx.node().x(), ctx.node().y()));
        registry.resolve("main.filter_area.match_all_indicator", ctx -> this.matchAllIndicator = this.addFilterIndicator(ctx.node().x(), ctx.node().y()));
        registry.resolve("main.filter_area.deny_indicator", ctx -> this.denyIndicator = this.addFilterIndicator(ctx.node().x(), ctx.node().y()));
        registry.resolve("main.filter_area.selection", ctx -> this.selectionWidget = this.root.addChild(new AttributeSelectionWidget(
                ctx.node().x(),
                ctx.node().y(),
                ctx.node().widthOrThrow(),
                ctx.node().heightOrThrow(),
                this.partSprite(BuiltinFilterParts.ATTRIBUTE_SELECTION, GuiSprites.ATTRIBUTE_FILTER_SELECTION),
                this::candidates,
                this.menu::selectedCandidateIndex,
                this::changeSelection
        ).withTitle(Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.AVAILABLE))));
        registry.resolve("main.filter_area.add_button", ctx -> {
            this.addButton = this.addIconButton(ctx.node().x(), ctx.node().y(), GuiSprites.FILTER_ICON_ADD, Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.ADD), () -> this.pressButton(AttributeFilterMenu.BUTTON_ADD_SELECTED))
                    .withTooltip(Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.ADD_TOOLTIP));
        });
        registry.resolve("main.filter_area.add_inverted_button", ctx -> {
            this.addInvertedButton = this.addIconButton(ctx.node().x(), ctx.node().y(), GuiSprites.FILTER_ICON_ADD_INVERTED, Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.ADD_INVERTED), () -> this.pressButton(AttributeFilterMenu.BUTTON_ADD_SELECTED_INVERTED))
                    .withTooltip(Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.ADD_INVERTED_TOOLTIP));
        });
        registry.resolve("main.filter_area.summary_widget", ctx -> this.summaryWidget = this.root.addChild(new AttributeRuleSummaryWidget(ctx.node().x(), ctx.node().y(), this.partSprite(BuiltinFilterParts.ATTRIBUTE_SUMMARY, GuiSprites.ATTRIBUTE_FILTER_SUMMARY), () -> this.menu.state().rules().size(), this.menu::summaryStack)));
        registry.resolve("main.filter_area.horizontal_separator", ctx -> this.addRootChild(new HorizontalSeparatorWidget(ctx.node().x(), ctx.node().y(), ctx.node().widthOrThrow(), this.style().get(BuiltinFilterParts.ATTRIBUTE_HORIZONTAL_SEPARATOR, GuiStyleProperties.COLOR, 0xFF000000))));
        registry.resolve("main.filter_area.vertical_separator", ctx -> this.addRootChild(new VerticalSeparatorWidget(ctx.node().x(), ctx.node().y(), ctx.node().heightOrThrow(), this.style().get(BuiltinFilterParts.ATTRIBUTE_VERTICAL_SEPARATOR, GuiStyleProperties.COLOR, 0xFF000000))));
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
