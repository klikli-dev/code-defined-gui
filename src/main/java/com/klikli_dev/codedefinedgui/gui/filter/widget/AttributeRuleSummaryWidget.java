// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.filter.widget;

import com.klikli_dev.codedefinedgui.gui.texture.GuiSprite;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprites;
import java.util.Objects;
import java.util.function.IntSupplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class AttributeRuleSummaryWidget extends AbstractWidget {
    private final GuiSprite sprite;
    private final IntSupplier ruleCount;
    private final Supplier<ItemStack> stack;

    public AttributeRuleSummaryWidget(int x, int y, IntSupplier ruleCount, Supplier<ItemStack> stack) {
        this(x, y, GuiSprites.ATTRIBUTE_FILTER_SUMMARY, ruleCount, stack);
    }

    public AttributeRuleSummaryWidget(int x, int y, GuiSprite sprite, IntSupplier ruleCount, Supplier<ItemStack> stack) {
        super(x, y, sprite.width(), sprite.height(), Component.empty());
        this.sprite = Objects.requireNonNull(sprite);
        this.ruleCount = ruleCount;
        this.stack = stack;
        this.active = false;
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        this.sprite.extractRenderState(graphics, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        ItemStack itemStack = this.stack.get();
        if (!itemStack.isEmpty()) {
            graphics.item(itemStack, this.getX() + 4, this.getY() + 4);
        }
        int count = this.ruleCount.getAsInt();
        if (count > 0) {
            graphics.text(Minecraft.getInstance().font, Component.literal(Integer.toString(count)), this.getX() + 9, this.getY() + 9, 0xFFFFFF, false);
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
    }
}
