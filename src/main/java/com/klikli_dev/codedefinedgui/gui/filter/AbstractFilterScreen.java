// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.filter;

import com.klikli_dev.codedefinedgui.CodeDefinedGuiConstants;
import com.klikli_dev.codedefinedgui.filter.core.FilterMenu;
import com.klikli_dev.codedefinedgui.filter.core.layout.BuiltinFilterParts;
import com.klikli_dev.codedefinedgui.filter.core.layout.BuiltinSlotRoles;
import com.klikli_dev.codedefinedgui.filter.core.layout.MenuSlotView;
import com.klikli_dev.codedefinedgui.gui.core.GuiHost;
import com.klikli_dev.codedefinedgui.gui.core.GuiRootWidget;
import com.klikli_dev.codedefinedgui.gui.filter.widget.FilterIndicatorWidget;
import com.klikli_dev.codedefinedgui.gui.style.GuiPartKey;
import com.klikli_dev.codedefinedgui.gui.style.GuiStyle;
import com.klikli_dev.codedefinedgui.gui.style.GuiStyleProperties;
import com.klikli_dev.codedefinedgui.gui.style.GuiStyleRegistry;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprite;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprites;
import com.klikli_dev.codedefinedgui.gui.widget.IconButtonBackgroundSprites;
import com.klikli_dev.codedefinedgui.gui.widget.IconButtonWidget;
import com.klikli_dev.codedefinedgui.gui.widget.GuiBackgroundWidget;
import com.klikli_dev.codedefinedgui.gui.widget.GuiSpriteWidget;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public abstract class AbstractFilterScreen<M extends FilterMenu> extends AbstractContainerScreen<M> implements GuiHost {
    private static final int PLAYER_INVENTORY_BACKGROUND_PADDING = 7;
    private static final int PLAYER_INVENTORY_LABEL_X_OFFSET = 8;
    private static final int PLAYER_INVENTORY_LABEL_Y_OFFSET = 13;

    protected final GuiRootWidget root;
    protected IconButtonWidget resetButton;
    protected IconButtonWidget confirmButton;
    private boolean closingHandled;

    protected AbstractFilterScreen(M menu, Inventory playerInventory, Component title, int imageWidth, int imageHeight) {
        super(menu, playerInventory, title, imageWidth, imageHeight);
        this.root = new GuiRootWidget(this);
    }

    @Override
    protected void init() {
        super.init();
        SlotBounds playerInventoryBackgroundBounds = this.playerInventoryBackgroundBounds();
        if (playerInventoryBackgroundBounds != null) {
            this.inventoryLabelX = this.playerInventoryLabelX(playerInventoryBackgroundBounds);
            this.inventoryLabelY = this.playerInventoryLabelY(playerInventoryBackgroundBounds);
        }
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
    public void onClose() {
        this.closeScreen(false);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        graphics.text(this.font, this.title, this.titleLabelX, this.titleLabelY, this.titleColor(), false);
        if (this.playerInventoryBackgroundBounds() != null) {
            graphics.text(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 0x404040, false);
        }
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

    protected int playerInventoryLabelX(SlotBounds bounds) {
        return bounds.minX() + PLAYER_INVENTORY_LABEL_X_OFFSET;
    }

    protected int playerInventoryLabelY(SlotBounds bounds) {
        return bounds.minY() + PLAYER_INVENTORY_LABEL_Y_OFFSET;
    }

    /**
     * Adds the player inventory background widgets.
     * <p>
     * Override to replace or suppress the default background while keeping the inventory slot widgets.
     */
    protected void addPlayerInventoryBackgroundWidgets() {
        SlotBounds bounds = this.playerInventoryBackgroundBounds();
        if (bounds == null) {
            return;
        }

        this.root.addChild(new GuiBackgroundWidget(
                this,
                this.guiX(bounds.minX()),
                this.guiY(bounds.minY()),
                bounds.width(),
                bounds.height(),
                this.partSprite(BuiltinFilterParts.PLAYER_INVENTORY_BACKGROUND, GuiSprites.GUI_BACKGROUND)
        ));
    }

    protected final void addStaticWidgets() {
        this.addSlotWidgets();
    }

    private void addSlotWidgets() {
        for (MenuSlotView slotView : this.menu.slotViews()) {
            GuiSprite sprite = this.slotSprite(slotView);
            int offsetX = this.style().get(slotView.part(), GuiStyleProperties.OFFSET_X, 1);
            int offsetY = this.style().get(slotView.part(), GuiStyleProperties.OFFSET_Y, 1);
            this.root.addChild(new GuiSpriteWidget(
                    this.leftPos() + slotView.x() - offsetX,
                    this.topPos() + slotView.y() - offsetY,
                    sprite
            ));
        }
    }

    /**
     * Adds optional screen background widgets before inventory and filter slot widgets are added.
     */
    protected void addBackgroundWidgets() {
    }

    /**
     * Adds interactive screen widgets such as buttons, indicators and custom controls.
     */
    protected abstract void addScreenWidgets();

    protected abstract void refreshWidgetState();

    protected final IconButtonWidget addIconButton(int x, int y, GuiSprite icon, Component message, Runnable onPress) {
        return this.root.addChild(new IconButtonWidget(x, y, icon, this.buttonBackgroundSprites(BuiltinFilterParts.BUTTON), message, onPress));
    }

    protected final IconButtonWidget addResetButton(int x, int y, int buttonId) {
        return this.addIconButton(
                x,
                y,
                GuiSprites.FILTER_ICON_RESET,
                Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Button.RESET),
                () -> this.pressButton(buttonId)
        ).withTooltip(Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Button.RESET_TOOLTIP));
    }

    protected final IconButtonWidget addConfirmButton(int x, int y) {
        return this.addIconButton(
                x,
                y,
                GuiSprites.FILTER_ICON_CONFIRM,
                Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Button.DONE),
                () -> this.closeScreen(true)
        ).withTooltip(Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Button.DONE_TOOLTIP));
    }

    protected final FilterIndicatorWidget addFilterIndicator(int x, int y) {
        return this.root.addChild(new FilterIndicatorWidget(x, y, this.filterIndicatorOnSprite(BuiltinFilterParts.INDICATOR), this.filterIndicatorOffSprite(BuiltinFilterParts.INDICATOR)));
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

    protected IconButtonBackgroundSprites buttonBackgroundSprites(GuiPartKey part) {
        return new IconButtonBackgroundSprites(
                this.style().get(part, GuiStyleProperties.SPRITE, GuiSprites.FILTER_BUTTON),
                this.style().get(part, GuiStyleProperties.PRESSED_SPRITE, GuiSprites.FILTER_BUTTON_DOWN),
                this.style().get(part, GuiStyleProperties.HOVER_SPRITE, GuiSprites.FILTER_BUTTON_HOVER)
        );
    }

    protected GuiSprite filterIndicatorOnSprite(GuiPartKey part) {
        return this.style().get(part, GuiStyleProperties.ON_SPRITE, GuiSprites.FILTER_INDICATOR_ON);
    }

    protected GuiSprite filterIndicatorOffSprite(GuiPartKey part) {
        return this.style().get(part, GuiStyleProperties.OFF_SPRITE, GuiSprites.FILTER_INDICATOR_OFF);
    }

    protected GuiSprite partSprite(GuiPartKey part, GuiSprite fallback) {
        return this.style().get(part, GuiStyleProperties.SPRITE, fallback);
    }

    private SlotBounds playerInventoryBounds() {
        return this.slotBounds(slotView -> slotView.role().equals(BuiltinSlotRoles.PLAYER_MAIN) || slotView.role().equals(BuiltinSlotRoles.PLAYER_HOTBAR));
    }

    private SlotBounds playerInventoryBackgroundBounds() {
        SlotBounds bounds = this.playerInventoryBounds();
        if (bounds == null) {
            return null;
        }

        return new SlotBounds(
                bounds.minX() - PLAYER_INVENTORY_BACKGROUND_PADDING,
                bounds.minY() - PLAYER_INVENTORY_BACKGROUND_PADDING,
                bounds.width() + PLAYER_INVENTORY_BACKGROUND_PADDING * 2,
                bounds.height() + PLAYER_INVENTORY_BACKGROUND_PADDING * 2
        );
    }

    private SlotBounds slotBounds(java.util.function.Predicate<MenuSlotView> filter) {
        SlotBounds bounds = null;
        for (MenuSlotView slotView : this.menu.slotViews()) {
            if (!filter.test(slotView)) {
                continue;
            }

            GuiSprite sprite = this.slotSprite(slotView);
            int offsetX = this.style().get(slotView.part(), GuiStyleProperties.OFFSET_X, 1);
            int offsetY = this.style().get(slotView.part(), GuiStyleProperties.OFFSET_Y, 1);
            SlotBounds slotBounds = new SlotBounds(slotView.x() - offsetX, slotView.y() - offsetY, sprite.width(), sprite.height());
            bounds = bounds == null ? slotBounds : bounds.union(slotBounds);
        }

        return bounds;
    }

    protected GuiSprite slotSprite(MenuSlotView slotView) {
        return this.partSprite(slotView.part(), GuiSprites.INVENTORY_SLOT);
    }

    protected final GuiStyle style() {
        return GuiStyleRegistry.get(this.menu.styleKey());
    }

    private record SlotBounds(int minX, int minY, int width, int height) {
        private int maxX() {
            return this.minX + this.width;
        }

        private int maxY() {
            return this.minY + this.height;
        }

        private SlotBounds union(SlotBounds other) {
            int minX = Math.min(this.minX, other.minX);
            int minY = Math.min(this.minY, other.minY);
            int maxX = Math.max(this.maxX(), other.maxX());
            int maxY = Math.max(this.maxY(), other.maxY());
            return new SlotBounds(minX, minY, maxX - minX, maxY - minY);
        }
    }

    protected abstract int confirmButtonId();

    protected abstract int cancelButtonId();

    protected abstract int titleColor();
}
