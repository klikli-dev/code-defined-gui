// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.example;

import com.klikli_dev.codedefinedgui.api.layout.LayoutResolverRegistry;
import com.klikli_dev.codedefinedgui.api.layout.LayoutScreenView;
import com.klikli_dev.codedefinedgui.api.layout.LayoutSpec;
import com.klikli_dev.codedefinedgui.api.layout.ScreenLayoutController;
import com.klikli_dev.codedefinedgui.api.screen.GuiHost;
import com.klikli_dev.codedefinedgui.api.screen.GuiRootWidget;
import com.klikli_dev.codedefinedgui.api.style.BuiltinGuiParts;
import com.klikli_dev.codedefinedgui.api.style.BuiltinGuiStyles;
import com.klikli_dev.codedefinedgui.api.style.GuiStyleContext;
import com.klikli_dev.codedefinedgui.api.style.GuiStyleRegistry;
import com.klikli_dev.codedefinedgui.api.texture.GuiSprites;
import com.klikli_dev.codedefinedgui.api.widget.GuiBackgroundWidget;
import com.klikli_dev.codedefinedgui.api.widget.GuiSpriteWidget;
import com.klikli_dev.codedefinedgui.api.widget.GuiTextWidget;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ExampleScreen extends AbstractContainerScreen<ExampleMenu> implements GuiHost, LayoutScreenView {
    private final GuiRootWidget root;
    private final ScreenLayoutController layoutController;

    public ExampleScreen(ExampleMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 176, 166);
        this.root = new GuiRootWidget(this);
        this.layoutController = new ScreenLayoutController(this, this, this.root, new GuiStyleContext(GuiStyleRegistry.get(BuiltinGuiStyles.DEFAULT)));
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(this.root);
        this.root.clearChildren();
        this.layoutController.init();
        this.root.syncWithHost();
    }

    @Override
    public LayoutSpec layoutSpec() {
        return this.menu.layoutSpec();
    }

    @Override
    public void registerResolvers(LayoutResolverRegistry registry) {
        registry.resolve("main.background", ctx -> ctx.addWidget(new GuiBackgroundWidget(this, ctx.node().x(), ctx.node().y(), ctx.node().widthOrThrow(), ctx.node().heightOrThrow())));
        registry.resolve("main.title", ctx -> ctx.addWidget(new GuiTextWidget(ctx.node().x(), ctx.node().y(), () -> this.title, () -> ctx.style().textColor(BuiltinGuiParts.PLAYER_INVENTORY_LABEL, 0xFF404040), false)));
        registry.add("main.input", 10, ctx -> ctx.addWidget(new GuiSpriteWidget(ctx.node().x() - 1, ctx.node().y() - 1, ctx.style().sprite(BuiltinGuiParts.PLAYER_SLOT, GuiSprites.INVENTORY_SLOT))));
        registry.add("main.output", 10, ctx -> ctx.addWidget(new GuiSpriteWidget(ctx.node().x() - 1, ctx.node().y() - 1, ctx.style().sprite(BuiltinGuiParts.PLAYER_SLOT, GuiSprites.INVENTORY_SLOT))));
        registry.resolve("main.hint", ctx -> ctx.addWidget(new GuiTextWidget(ctx.node().x(), ctx.node().y(), () -> Component.literal("Reference example: layout-first menu + screen composition"), () -> 0xFF6A6A6A, false)));
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

    @Override
    public void removeGuiWidget(AbstractWidget widget) {
        this.removeWidget(widget);
    }
}
