// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.texture;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public record GuiSprite(@NonNull Identifier sprite, int width, int height, int tint) {
    public GuiSprite(@NonNull Identifier sprite, int width, int height) {
        this(sprite, width, height, -1);
    }

    public GuiSprite sized(int width, int height) {
        return new GuiSprite(this.sprite, width, height, this.tint);
    }

    public GuiSprite tinted(int tint) {
        return new GuiSprite(this.sprite, this.width, this.height, tint);
    }

    public void extractRenderState(@NonNull GuiGraphicsExtractor guiGraphicsExtractor, int x, int y) {
        this.extractRenderState(guiGraphicsExtractor, x, y, this.width, this.height);
    }

    public void extractRenderState(@NonNull GuiGraphicsExtractor guiGraphicsExtractor, int x, int y, int width, int height) {
        guiGraphicsExtractor.blitSprite(RenderPipelines.GUI_TEXTURED, this.sprite, x, y, width, height, this.tint);
    }
}
