// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui;

import com.klikli_dev.codedefinedgui.filter.attribute.AttributeCandidate;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.client.input.MouseButtonEvent;

public class AttributeSelectionWidget extends AbstractWidget {
    private final Supplier<List<AttributeCandidate>> candidates;
    private final IntSupplier selectedIndex;
    private final IntConsumer onChange;

    public AttributeSelectionWidget(int x, int y, int width, int height, Supplier<List<AttributeCandidate>> candidates, IntSupplier selectedIndex, IntConsumer onChange) {
        super(x, y, width, height, Component.empty());
        this.candidates = candidates;
        this.selectedIndex = selectedIndex;
        this.onChange = onChange;
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, GuiTextures.GUI_BACKGROUND.sprite(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
        List<AttributeCandidate> entries = this.candidates.get();
        Component text = entries.isEmpty()
                ? Component.translatable("codedefinedgui.filter.attribute.no_reference")
                : entries.get(Math.max(0, Math.min(this.selectedIndex.getAsInt(), entries.size() - 1))).label();
        graphics.text(Minecraft.getInstance().font, text, this.getX() + 6, this.getY() + (this.getHeight() - 8) / 2, 0xF3EBDE, false);
    }

    @Override
    public void onClick(MouseButtonEvent event, boolean doubleClick) {
        List<AttributeCandidate> entries = this.candidates.get();
        if (entries.isEmpty()) {
            return;
        }

        this.onChange.accept((this.selectedIndex.getAsInt() + 1) % entries.size());
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        this.defaultButtonNarrationText(output);
    }
}
