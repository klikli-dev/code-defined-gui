// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui;

import com.klikli_dev.codedefinedgui.filter.attribute.AttributeCandidate;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.client.input.MouseButtonEvent;

public class AttributeSelectionWidget extends AbstractWidget {
    private static final int VISIBLE_TOOLTIP_ENTRIES = 8;
    private final Supplier<List<AttributeCandidate>> candidates;
    private final IntSupplier selectedIndex;
    private final IntConsumer onChange;
    private Component title = Component.empty();

    public AttributeSelectionWidget(int x, int y, int width, int height, Supplier<List<AttributeCandidate>> candidates, IntSupplier selectedIndex, IntConsumer onChange) {
        super(x, y, width, height, Component.empty());
        this.candidates = candidates;
        this.selectedIndex = selectedIndex;
        this.onChange = onChange;
        this.updateTooltip();
    }

    public AttributeSelectionWidget withTitle(Component title) {
        this.title = title;
        this.updateTooltip();
        return this;
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, GuiTextures.ATTRIBUTE_FILTER_SELECTION.sprite(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
        List<AttributeCandidate> entries = this.candidates.get();
        Component text = entries.isEmpty()
                ? Component.translatable("codedefinedgui.filter.attribute.no_reference")
                : entries.get(Math.max(0, Math.min(this.selectedIndex.getAsInt(), entries.size() - 1))).label();
        graphics.text(Minecraft.getInstance().font, text, this.getX() + 6, this.getY() + (this.getHeight() - 8) / 2, 0xFFF3EBDE, false);
    }

    @Override
    public void onClick(MouseButtonEvent event, boolean doubleClick) {
        List<AttributeCandidate> entries = this.candidates.get();
        if (entries.isEmpty()) {
            return;
        }

        this.onChange.accept((this.selectedIndex.getAsInt() + 1) % entries.size());
        this.updateTooltip();
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        List<AttributeCandidate> entries = this.candidates.get();
        if (entries.isEmpty() || !this.isMouseOver(mouseX, mouseY)) {
            return false;
        }

        int currentIndex = Math.max(0, Math.min(this.selectedIndex.getAsInt(), entries.size() - 1));
        int direction = scrollY > 0 ? -1 : scrollY < 0 ? 1 : 0;
        if (direction == 0) {
            return false;
        }

        int nextIndex = Math.floorMod(currentIndex + direction, entries.size());
        this.onChange.accept(nextIndex);
        this.updateTooltip();
        return true;
    }

    public void updateTooltip() {
        List<AttributeCandidate> entries = this.candidates.get();
        if (entries.isEmpty()) {
            MutableComponent tooltip = this.title.copy().append(Component.literal("\n")).append(Component.translatable("codedefinedgui.filter.attribute.no_reference")
                    .withStyle(ChatFormatting.GRAY));
            this.setTooltip(Tooltip.create(tooltip));
            return;
        }

        int selected = Math.max(0, Math.min(this.selectedIndex.getAsInt(), entries.size() - 1));
        int halfVisible = VISIBLE_TOOLTIP_ENTRIES / 2;
        int start = Math.max(selected - halfVisible, 0);
        int end = Math.min(start + VISIBLE_TOOLTIP_ENTRIES, entries.size());
        if (end - start > VISIBLE_TOOLTIP_ENTRIES) {
            start = end - VISIBLE_TOOLTIP_ENTRIES;
        }

        MutableComponent tooltip = this.title.copy();
        if (start > 0) {
            tooltip.append(Component.literal("\n")).append(Component.literal("> ...").withStyle(ChatFormatting.GRAY));
        }

        for (int i = start; i < end; i++) {
            Component line = Component.empty().append(i == selected ? "-> " : "> ").append(entries.get(i).label())
                    .withStyle(i == selected ? ChatFormatting.WHITE : ChatFormatting.GRAY);
            tooltip.append(Component.literal("\n")).append(line);
        }

        if (end < entries.size()) {
            tooltip.append(Component.literal("\n")).append(Component.literal("> ...").withStyle(ChatFormatting.GRAY));
        }

        tooltip.append(Component.literal("\n")).append(Component.translatable("codedefinedgui.filter.attribute.scroll_to_select")
                .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
        this.setTooltip(Tooltip.create(tooltip));
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        this.defaultButtonNarrationText(output);
    }
}
