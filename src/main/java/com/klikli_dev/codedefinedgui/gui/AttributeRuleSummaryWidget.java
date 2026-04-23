// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui;

import java.util.function.IntSupplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;

public class AttributeRuleSummaryWidget extends AbstractWidget {
    private final IntSupplier ruleCount;

    public AttributeRuleSummaryWidget(int x, int y, IntSupplier ruleCount) {
        super(x, y, GuiTextures.INVENTORY_SLOT.width(), GuiTextures.INVENTORY_SLOT.height(), Component.empty());
        this.ruleCount = ruleCount;
        this.active = false;
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, GuiTextures.INVENTORY_SLOT.sprite(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
        int count = this.ruleCount.getAsInt();
        if (count > 0) {
            graphics.text(Minecraft.getInstance().font, Component.literal(Integer.toString(count)), this.getX() + 5, this.getY() + 5, 0xFFFFFF, false);
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
    }
}
