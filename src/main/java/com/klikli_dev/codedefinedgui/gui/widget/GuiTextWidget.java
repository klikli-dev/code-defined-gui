// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.api.widget;

import java.util.function.IntSupplier;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public class GuiTextWidget extends AbstractWidget {
    private final Supplier<Component> textSupplier;
    private final IntSupplier colorSupplier;
    private final boolean shadow;

    public GuiTextWidget(int x, int y, Supplier<Component> textSupplier, IntSupplier colorSupplier, boolean shadow) {
        super(x, y, 0, 0, Component.empty());
        this.textSupplier = textSupplier;
        this.colorSupplier = colorSupplier;
        this.shadow = shadow;
        this.active = false;
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) {
            return;
        }

        int color = this.colorSupplier.getAsInt();
        if ((color & 0xFF000000) == 0) {
            color |= 0xFF000000;
        }

        graphics.text(minecraft.font, this.textSupplier.get(), this.getX(), this.getY(), color, this.shadow);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
    }
}

