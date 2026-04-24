// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public record GuiTexture(@NonNull Identifier sprite, int width, int height, int tint) {
    public GuiTexture(@NonNull Identifier sprite, int width, int height) {
        this(sprite, width, height, -1);
    }

    public GuiTexture sized(int width, int height) {
        return new GuiTexture(this.sprite, width, height, this.tint);
    }

    public GuiTexture tinted(int tint) {
        return new GuiTexture(this.sprite, this.width, this.height, tint);
    }

    public void extractRenderState(@NonNull GuiGraphicsExtractor guiGraphicsExtractor, int x, int y) {
        guiGraphicsExtractor.blitSprite(RenderPipelines.GUI_TEXTURED, this.sprite, x, y, this.width, this.height, this.tint);
    }
}
