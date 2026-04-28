// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.widget;

import com.klikli_dev.codedefinedgui.gui.texture.GuiTexture;
import com.klikli_dev.codedefinedgui.gui.texture.GuiTextures;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;

public class FilterIndicatorWidget extends AbstractWidget {
    private boolean on;

    public FilterIndicatorWidget(int x, int y) {
        super(x, y, GuiTextures.FILTER_INDICATOR_OFF.width(), GuiTextures.FILTER_INDICATOR_OFF.height(), Component.empty());
        this.active = false;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        GuiTexture texture = this.on ? GuiTextures.FILTER_INDICATOR_ON : GuiTextures.FILTER_INDICATOR_OFF;
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, texture.sprite(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
    }
}
