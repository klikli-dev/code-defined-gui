// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.widget;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

public class VerticalSeparatorWidget extends AbstractWidget {
    private static final int DEFAULT_COLOR = 0xFF000000;

    private final int color;

    public VerticalSeparatorWidget(int x, int y, int height) {
        this(x, y, height, DEFAULT_COLOR);
    }

    public VerticalSeparatorWidget(int x, int y, int height, int color) {
        super(x, y, 1, height, Component.empty());
        this.color = color;
        this.active = false;
    }

    public VerticalSeparatorWidget(int x, int y, int height, int leftColor, int rightColor) {
        this(x, y, height, leftColor);
    }

    @Override
    protected void extractWidgetRenderState(@NonNull GuiGraphicsExtractor guiGraphicsExtractor, int mouseX, int mouseY, float partialTick) {
        int left = this.getX();
        int top = this.getY();
        int bottom = top + this.getHeight();

        guiGraphicsExtractor.fill(left, top, left + this.getWidth(), bottom, this.color);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
    }
}
