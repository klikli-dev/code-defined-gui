// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.filter.widget;

import com.klikli_dev.codedefinedgui.CodeDefinedGuiConstants;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprite;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprites;
import com.klikli_dev.codedefinedgui.filter.attribute.AttributeCandidate;
import java.util.List;
import java.util.Objects;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.client.input.MouseButtonEvent;

public class AttributeSelectionWidget extends AbstractWidget {
    private static final int HEADER_RGB = 0x5391E1;
    private static final int VISIBLE_TOOLTIP_ENTRIES = 8;
    private final GuiSprite sprite;
    private final Supplier<List<AttributeCandidate>> candidates;
    private final IntSupplier selectedIndex;
    private final IntConsumer onChange;
    private Component title = Component.empty();

    public AttributeSelectionWidget(int x, int y, int width, int height, Supplier<List<AttributeCandidate>> candidates, IntSupplier selectedIndex, IntConsumer onChange) {
        this(x, y, width, height, GuiSprites.ATTRIBUTE_FILTER_SELECTION, candidates, selectedIndex, onChange);
    }

    public AttributeSelectionWidget(int x, int y, int width, int height, GuiSprite sprite, Supplier<List<AttributeCandidate>> candidates, IntSupplier selectedIndex, IntConsumer onChange) {
        super(x, y, width, height, Component.empty());
        this.sprite = Objects.requireNonNull(sprite);
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
        this.sprite.extractRenderState(graphics, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        List<AttributeCandidate> entries = this.candidates.get();
        Component text = entries.isEmpty()
                ? Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.NO_REFERENCE)
                : entries.get(Math.max(0, Math.min(this.selectedIndex.getAsInt(), entries.size() - 1))).label();
        int textLeft = this.getX() + 6;
        int textTop = this.getY() + (this.getHeight() - 8) / 2;
        int textRight = this.getX() + this.getWidth() - 6;
        graphics.enableScissor(textLeft, this.getY() + 1, textRight, this.getY() + this.getHeight() - 1);
        graphics.text(Minecraft.getInstance().font, text, textLeft, textTop, 0xFFF3EBDE, false);
        graphics.disableScissor();
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
            MutableComponent tooltip = this.title.copy().withStyle(style -> style.withColor(HEADER_RGB)).append(Component.literal("\n")).append(Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.NO_REFERENCE)
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

        MutableComponent tooltip = this.title.copy().withStyle(style -> style.withColor(HEADER_RGB));
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

        tooltip.append(Component.literal("\n")).append(Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.SCROLL_TO_SELECT)
                .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
        this.setTooltip(Tooltip.create(tooltip));
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        this.defaultButtonNarrationText(output);
    }
}
