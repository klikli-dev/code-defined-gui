// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.widget;

import com.klikli_dev.codedefinedgui.gui.core.GuiSyncable;
import com.klikli_dev.codedefinedgui.gui.texture.GuiTexture;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

public class TextureWidget extends AbstractWidget implements GuiSyncable {
    private final GuiTexture texture;

    public TextureWidget(int x, int y, GuiTexture texture) {
        super(x, y, texture.width(), texture.height(), Component.empty());
        this.texture = texture;
        this.active = false;
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
