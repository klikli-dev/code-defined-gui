// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

public class FrameWidget extends AbstractWidget {
    private static final int DEFAULT_COLOR = 0xFFFFFFFF;

    private final int color;

    public FrameWidget(int x, int y, int width, int height) {
        this(x, y, width, height, DEFAULT_COLOR);
    }

    public FrameWidget(AbstractWidget widget) {
        this(widget, DEFAULT_COLOR);
    }

    public FrameWidget(AbstractWidget widget, int color) {
        this(widget.getX(), widget.getY(), widget.getWidth(), widget.getHeight(), color);
    }

    public FrameWidget(int x, int y, int width, int height, int color) {
        super(x, y, width, height, Component.empty());
        this.color = color;
        this.active = false;
    }

    @Override
    protected void extractWidgetRenderState(@NonNull GuiGraphicsExtractor guiGraphicsExtractor, int mouseX, int mouseY, float a) {
        int left = this.getX();
        int top = this.getY();
        int right = left + this.getWidth();
        int bottom = top + this.getHeight();

        guiGraphicsExtractor.fill(left, top, right, top + 1, this.color);
        guiGraphicsExtractor.fill(left, bottom - 1, right, bottom, this.color);
        guiGraphicsExtractor.fill(left, top + 1, left + 1, bottom - 1, this.color);
        guiGraphicsExtractor.fill(right - 1, top + 1, right, bottom - 1, this.color);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
    }
}
