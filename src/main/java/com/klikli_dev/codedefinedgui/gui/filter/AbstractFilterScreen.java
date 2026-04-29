// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.filter;

import com.klikli_dev.codedefinedgui.filter.core.FilterMenu;
import com.klikli_dev.codedefinedgui.gui.core.GuiHost;
import com.klikli_dev.codedefinedgui.gui.core.GuiRootWidget;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprite;
import com.klikli_dev.codedefinedgui.gui.widget.GuiBackgroundWidget;
import com.klikli_dev.codedefinedgui.gui.widget.GuiSpriteWidget;
import com.klikli_dev.codedefinedgui.gui.widget.IconButtonBackgroundSprites;
import com.klikli_dev.codedefinedgui.gui.widget.IconButtonWidget;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprites;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public abstract class AbstractFilterScreen<M extends FilterMenu> extends AbstractContainerScreen<M> implements GuiHost {
    protected final GuiRootWidget root;
    protected IconButtonWidget resetButton;
    protected IconButtonWidget confirmButton;

    protected AbstractFilterScreen(M menu, Inventory playerInventory, Component title, int imageWidth, int imageHeight) {
        super(menu, playerInventory, title, imageWidth, imageHeight);
        this.root = new GuiRootWidget(this);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelX = this.playerInventoryLabelX();
        this.inventoryLabelY = this.playerInventoryLabelY();
        this.addRenderableWidget(this.root);
        this.root.clearChildren();
        this.addBackgroundWidgets();
        this.addPlayerInventoryBackgroundWidgets();
        this.addStaticWidgets();
        this.addScreenWidgets();
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
    protected void extractLabels(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        graphics.text(this.font, this.title, this.titleLabelX, this.titleLabelY, this.titleColor(), false);
        graphics.text(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 0x404040, false);
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

    protected int centeredPlayerInventoryLeft() {
        return this.leftPos + (this.imageWidth - 176) / 2;
    }

    protected int playerInventoryTop() {
        return this.topPos + this.imageHeight - 107;
    }

    /**
     * Adds the player inventory background widgets.
     * <p>
     * Override to replace or suppress the default background while keeping the inventory slot widgets.
     */
    protected void addPlayerInventoryBackgroundWidgets() {
        this.root.addChild(new GuiBackgroundWidget(
                this,
                this.centeredPlayerInventoryLeft(),
                this.playerInventoryBackgroundTop(),
                this.playerInventoryBackgroundWidth(),
                this.playerInventoryBackgroundHeight(),
                this.playerInventoryBackgroundSprite()
        ));
    }

    protected final void addStaticWidgets() {
        this.addPlayerInventorySlotWidgets();
        this.addFilterSlotWidgets();
    }

    private void addPlayerInventorySlotWidgets() {
        int inventoryLeft = this.centeredPlayerInventoryLeft();
        int inventoryTop = this.playerInventoryTop();
        GuiSprite inventorySlotSprite = this.playerInventorySlotSprite();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.root.addChild(new GuiSpriteWidget(inventoryLeft + this.playerInventorySlotXOffset() + col * 18, inventoryTop + this.playerInventoryMainSlotYOffset() + row * 18, inventorySlotSprite));
            }
        }

        for (int col = 0; col < 9; col++) {
            this.root.addChild(new GuiSpriteWidget(inventoryLeft + this.playerInventorySlotXOffset() + col * 18, inventoryTop + this.playerInventoryHotbarSlotYOffset(), inventorySlotSprite));
        }
    }

    /**
     * Adds optional screen background widgets before inventory and filter slot widgets are added.
     */
    protected void addBackgroundWidgets() {
    }

    /**
     * Adds the visual widgets for the filter's own slots.
     * <p>
     * Player inventory slot widgets are always added by the base screen.
     */
    protected abstract void addFilterSlotWidgets();

    /**
     * Adds interactive screen widgets such as buttons, indicators and custom controls.
     */
    protected abstract void addScreenWidgets();

    protected abstract void refreshWidgetState();

    protected IconButtonBackgroundSprites buttonBackgroundSprites() {
        return IconButtonBackgroundSprites.DEFAULT;
    }

    protected GuiSprite inventorySlotSprite() {
        return GuiSprites.INVENTORY_SLOT;
    }

    protected GuiSprite playerInventoryBackgroundSprite() {
        return GuiSprites.GUI_BACKGROUND;
    }

    protected GuiSprite playerInventorySlotSprite() {
        return GuiSprites.INVENTORY_SLOT;
    }

    protected int playerInventoryBackgroundWidth() {
        return 176;
    }

    protected int playerInventoryBackgroundTop() {
        return this.playerInventoryTop();
    }

    protected int playerInventoryBackgroundHeight() {
        return 108;
    }

    protected int playerInventorySlotXOffset() {
        return 7;
    }

    protected int playerInventoryMainSlotYOffset() {
        return 18;
    }

    protected int playerInventoryHotbarSlotYOffset() {
        return 76;
    }

    protected int playerInventoryLabelX() {
        return (this.imageWidth - 176) / 2 + 8;
    }

    protected int playerInventoryLabelY() {
        return this.imageHeight - 94;
    }

    protected abstract int titleColor();
}
