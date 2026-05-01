// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.widget;

import com.klikli_dev.codedefinedgui.gui.texture.GuiSprite;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.client.input.MouseButtonEvent;

public class IconButtonWidget extends AbstractWidget {
    private static final int WIDTH = 18;
    private static final int HEIGHT = 18;

    private final GuiSprite icon;
    private final Runnable onPress;
    private final IconButtonBackgroundSprites backgroundSprites;
    private Component tooltip;
    private Component shiftTooltip;
    private boolean lastShiftDown;
    private boolean tooltipInitialized;

    public IconButtonWidget(int x, int y, GuiSprite icon, Component message, Runnable onPress) {
        this(x, y, icon, IconButtonBackgroundSprites.DEFAULT, message, onPress);
    }

    public IconButtonWidget(int x, int y, GuiSprite icon, IconButtonBackgroundSprites backgroundSprites, Component message, Runnable onPress) {
        super(x, y, WIDTH, HEIGHT, message);
        this.icon = icon;
        this.backgroundSprites = backgroundSprites;
        this.onPress = Objects.requireNonNull(onPress);
    }

    public IconButtonWidget withTooltip(Component tooltip) {
        this.tooltip = tooltip;
        this.shiftTooltip = null;
        this.refreshTooltip();
        return this;
    }

    public IconButtonWidget withTooltip(Component tooltip, Component shiftTooltip) {
        this.tooltip = tooltip;
        this.shiftTooltip = shiftTooltip;
        this.refreshTooltip();
        return this;
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        this.refreshTooltip();
        GuiSprite background = this.active && this.isMouseOver(mouseX, mouseY) ? this.backgroundSprites.hovered() : this.backgroundSprites.normal();
        background.extractRenderState(graphics, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        int iconX = this.getX() + (this.getWidth() - this.icon.width()) / 2;
        int iconY = this.getY() + (this.getHeight() - this.icon.height()) / 2;
        this.icon.extractRenderState(graphics, iconX, iconY, this.icon.width(), this.icon.height());
        if (!this.active) {
            graphics.fill(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), 0x88000000);
        }
    }

    @Override
    public void onClick(MouseButtonEvent event, boolean doubleClick) {
        this.onPress.run();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        this.defaultButtonNarrationText(output);
    }

    private void refreshTooltip() {
        if (this.tooltip == null) {
            return;
        }

        boolean shiftDown = Minecraft.getInstance().hasShiftDown();
        if (this.tooltipInitialized && shiftDown == this.lastShiftDown) {
            return;
        }

        this.tooltipInitialized = true;
        this.lastShiftDown = shiftDown;
        Component message = this.tooltip;
        if (this.shiftTooltip != null) {
            message = shiftDown
                    ? Component.empty().append(this.tooltip).append("\n").append(this.shiftTooltip)
                    : Component.empty().append(this.tooltip).append("\n").append(Component.translatable("codedefinedgui.tooltip.show_extended"));
        }

        this.setTooltip(Tooltip.create(message));
    }
}
