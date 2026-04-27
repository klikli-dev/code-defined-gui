// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

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
    protected void extractWidgetRenderState(@NonNull GuiGraphicsExtractor guiGraphicsExtractor, int mouseX, int mouseY, float a) {
        this.texture.extractRenderState(guiGraphicsExtractor, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
    }

    @Override
    public void syncToHost() {
    }
}
