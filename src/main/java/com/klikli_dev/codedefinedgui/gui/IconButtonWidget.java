// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui;

import java.util.Objects;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.client.input.MouseButtonEvent;

public class IconButtonWidget extends AbstractWidget {
    private final GuiTexture icon;
    private final Runnable onPress;

    public IconButtonWidget(int x, int y, GuiTexture icon, Component message, Runnable onPress) {
        super(x, y, GuiTextures.FILTER_BUTTON.width(), GuiTextures.FILTER_BUTTON.height(), message);
        this.icon = icon;
        this.onPress = Objects.requireNonNull(onPress);
    }

    public IconButtonWidget withTooltip(Component tooltip) {
        this.setTooltip(Tooltip.create(tooltip));
        return this;
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        GuiTexture background = !this.active ? GuiTextures.FILTER_BUTTON_DOWN : this.isHoveredOrFocused() ? GuiTextures.FILTER_BUTTON_HOVER : GuiTextures.FILTER_BUTTON;
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, background.sprite(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.icon.sprite(), this.getX() + 1, this.getY() + 1, this.icon.width(), this.icon.height());
    }

    @Override
    public void onClick(MouseButtonEvent event, boolean doubleClick) {
        this.onPress.run();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        this.defaultButtonNarrationText(output);
    }
}
