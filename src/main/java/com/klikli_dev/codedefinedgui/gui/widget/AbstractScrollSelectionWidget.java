// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.widget;

import com.klikli_dev.codedefinedgui.gui.texture.GuiSprite;
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
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public abstract class AbstractScrollSelectionWidget<T> extends AbstractWidget {
    private static final int DEFAULT_HEADER_RGB = 0x5391E1;
    private static final int DEFAULT_TEXT_COLOR = 0xFFF3EBDE;
    private static final int DEFAULT_VISIBLE_TOOLTIP_ENTRIES = 8;

    private final GuiSprite sprite;
    private final Supplier<List<T>> entries;
    private final IntSupplier selectedIndex;
    private final IntConsumer onChange;
    private Component title = Component.empty();

    protected AbstractScrollSelectionWidget(int x, int y, int width, int height, GuiSprite sprite,
                                            Supplier<List<T>> entries, IntSupplier selectedIndex,
                                            IntConsumer onChange) {
        super(x, y, width, height, Component.empty());
        this.sprite = Objects.requireNonNull(sprite);
        this.entries = Objects.requireNonNull(entries);
        this.selectedIndex = Objects.requireNonNull(selectedIndex);
        this.onChange = Objects.requireNonNull(onChange);
    }

    public AbstractScrollSelectionWidget<T> withTitle(Component title) {
        this.title = title == null ? Component.empty() : title;
        this.updateTooltip();
        return this;
    }

    protected abstract Component entryLabel(T entry);

    protected Component emptySelectionLabel() {
        return this.emptyEntriesText();
    }

    protected abstract Component emptyEntriesText();

    protected abstract Component scrollHintText();

    protected int entryTextColor() {
        return DEFAULT_TEXT_COLOR;
    }

    protected int headerColor() {
        return DEFAULT_HEADER_RGB;
    }

    protected int visibleTooltipEntries() {
        return DEFAULT_VISIBLE_TOOLTIP_ENTRIES;
    }

    protected Supplier<List<T>> entriesSupplier() {
        return this.entries;
    }

    protected int selectedEntryIndex() {
        return this.selectedIndex.getAsInt();
    }

    protected void changeSelection(int nextIndex) {
        this.onChange.accept(nextIndex);
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        this.sprite.extractRenderState(graphics, this.getX(), this.getY(), this.getWidth(), this.getHeight());

        List<T> entries = this.entries.get();
        Component text = entries.isEmpty()
                ? this.emptySelectionLabel()
                : this.entryLabel(entries.get(this.clampedSelectedIndex(entries)));
        int textLeft = this.getX() + 6;
        int textTop = this.getY() + (this.getHeight() - 8) / 2;
        int textRight = this.getX() + this.getWidth() - 6;
        graphics.enableScissor(textLeft, this.getY() + 1, textRight, this.getY() + this.getHeight() - 1);
        graphics.text(Minecraft.getInstance().font, text, textLeft, textTop, this.entryTextColor(), false);
        graphics.disableScissor();
    }

    @Override
    public void onClick(MouseButtonEvent event, boolean doubleClick) {
        List<T> entries = this.entries.get();
        if (entries.isEmpty()) {
            return;
        }

        this.changeSelection((this.clampedSelectedIndex(entries) + 1) % entries.size());
        this.updateTooltip();
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        List<T> entries = this.entries.get();
        if (entries.isEmpty() || !this.isMouseOver(mouseX, mouseY)) {
            return false;
        }

        int direction = scrollY > 0 ? -1 : scrollY < 0 ? 1 : 0;
        if (direction == 0) {
            return false;
        }

        int nextIndex = Math.floorMod(this.clampedSelectedIndex(entries) + direction, entries.size());
        this.changeSelection(nextIndex);
        this.updateTooltip();
        return true;
    }

    public void updateTooltip() {
        List<T> entries = this.entries.get();
        if (entries.isEmpty()) {
            MutableComponent tooltip = this.title.copy()
                    .withStyle(style -> style.withColor(this.headerColor()))
                    .append(Component.literal("\n"))
                    .append(this.emptyEntriesText().copy().withStyle(ChatFormatting.GRAY));
            this.setTooltip(Tooltip.create(tooltip));
            return;
        }

        int visibleTooltipEntries = Math.max(1, this.visibleTooltipEntries());
        int selected = this.clampedSelectedIndex(entries);
        int halfVisible = visibleTooltipEntries / 2;
        int start = Math.max(selected - halfVisible, 0);
        int end = Math.min(start + visibleTooltipEntries, entries.size());
        if (end - start > visibleTooltipEntries) {
            start = end - visibleTooltipEntries;
        }

        MutableComponent tooltip = this.title.copy().withStyle(style -> style.withColor(this.headerColor()));
        if (start > 0) {
            tooltip.append(Component.literal("\n")).append(Component.literal("> ...").withStyle(ChatFormatting.GRAY));
        }

        for (int i = start; i < end; i++) {
            Component line = Component.empty()
                    .append(i == selected ? "-> " : "> ")
                    .append(this.entryLabel(entries.get(i)))
                    .withStyle(i == selected ? ChatFormatting.WHITE : ChatFormatting.GRAY);
            tooltip.append(Component.literal("\n")).append(line);
        }

        if (end < entries.size()) {
            tooltip.append(Component.literal("\n")).append(Component.literal("> ...").withStyle(ChatFormatting.GRAY));
        }

        tooltip.append(Component.literal("\n")).append(this.scrollHintText().copy().withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
        this.setTooltip(Tooltip.create(tooltip));
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        this.defaultButtonNarrationText(output);
    }

    private int clampedSelectedIndex(List<T> entries) {
        return Math.max(0, Math.min(this.selectedIndex.getAsInt(), entries.size() - 1));
    }
}
