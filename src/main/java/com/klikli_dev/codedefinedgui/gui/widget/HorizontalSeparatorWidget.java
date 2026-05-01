// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.widget;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

public class HorizontalSeparatorWidget extends AbstractWidget {
    private static final int DEFAULT_COLOR = 0xFF000000;

    private final int color;

    public HorizontalSeparatorWidget(int x, int y, int width) {
        this(x, y, width, DEFAULT_COLOR);
    }

    public HorizontalSeparatorWidget(int x, int y, int width, int color) {
        super(x, y, width, 1, Component.empty());
        this.color = color;
        this.active = false;
    }

    @Override
    protected void extractWidgetRenderState(@NonNull GuiGraphicsExtractor guiGraphicsExtractor, int mouseX, int mouseY, float partialTick) {
        int left = this.getX();
        int top = this.getY();
        int right = left + this.getWidth();

        guiGraphicsExtractor.fill(left, top, right, top + this.getHeight(), this.color);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
    }
}
