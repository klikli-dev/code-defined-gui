// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.widget;

import com.klikli_dev.codedefinedgui.gui.core.GuiSyncable;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprite;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

/**
 * Renders a GuiSprite at the given position.
 * Can be used to e.g. render slot backgrounds.
 */
public class GuiSpriteWidget extends AbstractWidget implements GuiSyncable {
    private final GuiSprite sprite;

    public GuiSpriteWidget(int x, int y, GuiSprite sprite) {
        super(x, y, sprite.width(), sprite.height(), Component.empty());
        this.sprite = sprite;
        this.active = false;
    }

    @Override
    protected void extractWidgetRenderState(@NonNull GuiGraphicsExtractor guiGraphicsExtractor, int mouseX, int mouseY, float partialTick) {
        this.sprite.extractRenderState(guiGraphicsExtractor, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
    }

    @Override
    public void syncToHost() {
    }
}
