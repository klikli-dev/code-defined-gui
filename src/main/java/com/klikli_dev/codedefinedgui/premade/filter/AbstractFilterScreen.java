// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.premade.filter;

import com.klikli_dev.codedefinedgui.internal.CodeDefinedGuiConstants;
import com.klikli_dev.codedefinedgui.premade.filter.core.FilterMenu;
import com.klikli_dev.codedefinedgui.premade.filter.core.layout.BuiltinFilterParts;
import com.klikli_dev.codedefinedgui.api.screen.GuiHost;
import com.klikli_dev.codedefinedgui.api.screen.GuiRootWidget;
import com.klikli_dev.codedefinedgui.premade.filter.widget.FilterIndicatorWidget;
import com.klikli_dev.codedefinedgui.api.layout.BuiltinLayoutSlotRoles;
import com.klikli_dev.codedefinedgui.api.layout.LayoutResolveContext;
import com.klikli_dev.codedefinedgui.api.layout.LayoutResolverRegistry;
import com.klikli_dev.codedefinedgui.api.layout.LayoutScreenView;
import com.klikli_dev.codedefinedgui.api.layout.LayoutSlotView;
import com.klikli_dev.codedefinedgui.api.layout.LayoutSpec;
import com.klikli_dev.codedefinedgui.api.layout.ScreenLayoutController;
import com.klikli_dev.codedefinedgui.internal.layout.LayoutScreenRendererHost;
import com.klikli_dev.codedefinedgui.internal.layout.inventory.PlayerInventorySection;
import com.klikli_dev.codedefinedgui.api.style.GuiStyleContext;
import com.klikli_dev.codedefinedgui.api.style.GuiStyleRegistry;
import com.klikli_dev.codedefinedgui.api.texture.GuiSprite;
import com.klikli_dev.codedefinedgui.api.texture.GuiSprites;
import com.klikli_dev.codedefinedgui.api.widget.GuiSpriteWidget;
import com.klikli_dev.codedefinedgui.api.widget.IconButtonBackgroundSprites;
import com.klikli_dev.codedefinedgui.api.widget.IconButtonWidget;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public abstract class AbstractFilterScreen<M extends FilterMenu> extends AbstractContainerScreen<M> implements GuiHost, LayoutScreenView, LayoutScreenRendererHost {
    protected final PlayerInventorySection playerInventorySection;
    protected final GuiRootWidget root;
    private final ScreenLayoutController layoutController;
    protected IconButtonWidget resetButton;
    protected IconButtonWidget confirmButton;
    private boolean closingHandled;

    protected AbstractFilterScreen(M menu, Inventory playerInventory, Component title, int imageWidth, int imageHeight) {
        super(menu, playerInventory, title, imageWidth, imageHeight);
        this.playerInventorySection = PlayerInventorySection.standard();
        this.root = new GuiRootWidget(this);
        this.layoutController = new ScreenLayoutController(this, this, this.root, new GuiStyleContext(GuiStyleRegistry.get(this.menu.styleKey())));
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(this.root);
        this.root.clearChildren();
        this.layoutController.init();
        this.root.syncWithHost();
        this.refreshWidgetState();
    }

    @Override
    public void containerTick() {
        super.containerTick();
        this.refreshWidgetState();
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        for (var listener : this.children()) {
            if (listener.isMouseOver(mouseX, mouseY) && listener.mouseScrolled(mouseX, mouseY, scrollX, scrollY)) {
                this.setFocused(listener);
                return true;
            }
        }

        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public void onClose() {
        this.closeScreen(false);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
    }

    @Override
    public int leftPos() {
        return this.leftPos;
    }

    @Override
    public int topPos() {
        return this.topPos;
    }

    @Override
    public int width() {
        return this.width;
    }

    @Override
    public int height() {
        return this.height;
    }

    @Override
    public int imageWidth() {
        return this.imageWidth;
    }

    @Override
    public int imageHeight() {
        return this.imageHeight;
    }

    @Override
    public <T extends AbstractWidget> T addGuiWidget(T widget) {
        return this.addRenderableWidget(widget);
    }

    public final <T extends AbstractWidget> T addRootChild(T widget) {
        return this.root.addChild(widget);
    }

    @Override
    public void removeGuiWidget(AbstractWidget widget) {
        this.removeWidget(widget);
    }

    @Override
    public LayoutSpec layoutSpec() {
        return this.menu.layoutSpec();
    }

    @Override
    public void registerResolvers(LayoutResolverRegistry registry) {
        this.playerInventorySection.registerResolvers(registry.scope("player_inventory"), this);
        this.registerSlotResolvers(registry);
    }

    @Override
    public java.util.List<LayoutSlotView> layoutSlots() {
        return this.menu.layoutSlots();
    }

    protected void registerSlotResolvers(LayoutResolverRegistry registry) {
        for (LayoutSlotView slotView : this.menu.layoutSlots()) {
            if (slotView.role().equals(BuiltinLayoutSlotRoles.PLAYER_MAIN) || slotView.role().equals(BuiltinLayoutSlotRoles.PLAYER_HOTBAR)) {
                continue;
            }

            if (slotView.nodePath() == null || slotView.nodePath().isEmpty()) {
                continue;
            }

            registry.add(slotView.nodePath(), -25, ctx -> ctx.addWidget(new GuiSpriteWidget(
                    slotView.x() - 1 + this.leftPos(),
                    slotView.y() - 1 + this.topPos(),
                    ctx.style().sprite(slotView.part(), GuiSprites.INVENTORY_SLOT)
            )));
        }
    }

    protected abstract void refreshWidgetState();

    protected final IconButtonWidget addIconButton(LayoutResolveContext ctx, GuiSprite icon, Component message, Runnable onPress) {
        IconButtonWidget widget = new IconButtonWidget(
                ctx.node().x(),
                ctx.node().y(),
                icon,
                ctx.style().iconButtonBackgroundSprites(BuiltinFilterParts.BUTTON, IconButtonBackgroundSprites.DEFAULT),
                message,
                onPress
        );
        ctx.addWidget(widget);
        return widget;
    }

    protected final IconButtonWidget addResetButton(LayoutResolveContext ctx, int buttonId) {
        return this.addIconButton(
                ctx,
                GuiSprites.FILTER_ICON_RESET,
                Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Button.RESET),
                () -> this.pressButton(buttonId)
        ).withTooltip(Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Button.RESET_TOOLTIP));
    }

    protected final IconButtonWidget addConfirmButton(LayoutResolveContext ctx) {
        return this.addIconButton(
                ctx,
                GuiSprites.FILTER_ICON_CONFIRM,
                Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Button.DONE),
                () -> this.closeScreen(true)
        ).withTooltip(Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Button.DONE_TOOLTIP));
    }

    protected final FilterIndicatorWidget addFilterIndicator(LayoutResolveContext ctx) {
        FilterIndicatorWidget widget = new FilterIndicatorWidget(
                ctx.node().x(),
                ctx.node().y(),
                ctx.style().onSprite(BuiltinFilterParts.INDICATOR, GuiSprites.FILTER_INDICATOR_ON),
                ctx.style().offSprite(BuiltinFilterParts.INDICATOR, GuiSprites.FILTER_INDICATOR_OFF)
        );
        ctx.addWidget(widget);
        return widget;
    }

    protected final void pressButton(int buttonId) {
        if (this.minecraft == null || this.minecraft.player == null || this.minecraft.gameMode == null) {
            return;
        }

        if (this.menu.clickMenuButton(this.minecraft.player, buttonId)) {
            this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, buttonId);
        }
    }

    protected final void closeScreen(boolean confirm) {
        if (this.closingHandled) {
            return;
        }

        this.closingHandled = true;
        this.pressButton(confirm ? this.confirmButtonId() : this.cancelButtonId());
        super.onClose();
    }

    protected abstract int confirmButtonId();

    protected abstract int cancelButtonId();
}





