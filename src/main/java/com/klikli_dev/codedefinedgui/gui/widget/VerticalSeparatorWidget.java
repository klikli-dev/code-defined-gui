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
    private static final int DEFAULT_LEFT_COLOR = 0xFF555555;
    private static final int DEFAULT_RIGHT_COLOR = 0xFFFFFFFF;

    private final boolean doubleLine;
    private final int leftColor;
    private final int rightColor;

    public VerticalSeparatorWidget(int x, int y, int height) {
        this(x, y, height, DEFAULT_LEFT_COLOR, DEFAULT_RIGHT_COLOR);
    }

    public VerticalSeparatorWidget(int x, int y, int height, int color) {
        this(x, y, height, false, color, color);
    }

    public VerticalSeparatorWidget(int x, int y, int height, int leftColor, int rightColor) {
        this(x, y, height, true, leftColor, rightColor);
    }

    private VerticalSeparatorWidget(int x, int y, int height, boolean doubleLine, int leftColor, int rightColor) {
        super(x, y, doubleLine ? 2 : 1, height, Component.empty());
        this.doubleLine = doubleLine;
        this.leftColor = leftColor;
        this.rightColor = rightColor;
        this.active = false;
    }

    @Override
    protected void extractWidgetRenderState(@NonNull GuiGraphicsExtractor guiGraphicsExtractor, int mouseX, int mouseY, float partialTick) {
        int left = this.getX();
        int top = this.getY();
        int bottom = top + this.getHeight();

        guiGraphicsExtractor.fill(left, top, left + 1, bottom, this.leftColor);
        if (this.doubleLine) {
            guiGraphicsExtractor.fill(left + 1, top, left + this.getWidth(), bottom, this.rightColor);
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
    }
}
