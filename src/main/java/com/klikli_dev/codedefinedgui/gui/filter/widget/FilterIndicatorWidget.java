// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.filter.widget;

import com.klikli_dev.codedefinedgui.gui.texture.GuiSprite;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprites;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public class FilterIndicatorWidget extends AbstractWidget {
    private final GuiSprite onSprite;
    private final GuiSprite offSprite;
    private boolean on;

    public FilterIndicatorWidget(int x, int y) {
        this(x, y, GuiSprites.FILTER_INDICATOR_ON, GuiSprites.FILTER_INDICATOR_OFF);
    }

    public FilterIndicatorWidget(int x, int y, GuiSprite onSprite, GuiSprite offSprite) {
        super(x, y, offSprite.width(), offSprite.height(), Component.empty());
        this.onSprite = onSprite;
        this.offSprite = offSprite;
        this.active = false;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        GuiSprite texture = this.on ? this.onSprite : this.offSprite;
        texture.extractRenderState(graphics, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
    }
}
