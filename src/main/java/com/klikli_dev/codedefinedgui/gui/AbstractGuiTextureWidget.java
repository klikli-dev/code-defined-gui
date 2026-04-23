// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;

public abstract class AbstractGuiTextureWidget extends AbstractWidget implements GuiSyncable {
    private final GuiTexture texture;

    protected AbstractGuiTextureWidget(int x, int y, GuiTexture texture) {
        super(x, y, texture.width(), texture.height(), Component.empty());
        this.texture = texture;
        this.active = false;
    }

    protected GuiTexture texture() {
        return this.texture;
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        if (this.texture.tint() == -1) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.texture.sprite(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
        } else {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.texture.sprite(), this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.texture.tint());
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
    }

    @Override
    public void syncToHost() {
    }
}
