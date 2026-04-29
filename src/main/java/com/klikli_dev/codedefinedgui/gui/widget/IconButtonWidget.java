// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.widget;

import com.klikli_dev.codedefinedgui.gui.texture.GuiSprite;
import java.util.Objects;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.client.input.MouseButtonEvent;

public class IconButtonWidget extends AbstractWidget {
    private static final int WIDTH = 18;
    private static final int HEIGHT = 18;

    private final GuiSprite icon;
    private final Runnable onPress;
    private final IconButtonBackgroundSprites backgroundSprites;

    public IconButtonWidget(int x, int y, GuiSprite icon, Component message, Runnable onPress) {
        this(x, y, icon, IconButtonBackgroundSprites.DEFAULT, message, onPress);
    }

    public IconButtonWidget(int x, int y, GuiSprite icon, IconButtonBackgroundSprites backgroundSprites, Component message, Runnable onPress) {
        super(x, y, WIDTH, HEIGHT, message);
        this.icon = icon;
        this.backgroundSprites = backgroundSprites;
        this.onPress = Objects.requireNonNull(onPress);
    }

    public IconButtonWidget withTooltip(Component tooltip) {
        this.setTooltip(Tooltip.create(tooltip));
        return this;
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        GuiSprite background = !this.active ? this.backgroundSprites.pressed() : this.isHoveredOrFocused() ? this.backgroundSprites.hovered() : this.backgroundSprites.normal();
        background.extractRenderState(graphics, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        int iconX = this.getX() + (this.getWidth() - this.icon.width()) / 2;
        int iconY = this.getY() + (this.getHeight() - this.icon.height()) / 2;
        this.icon.extractRenderState(graphics, iconX, iconY, this.icon.width(), this.icon.height());
    }

    @Override
    public void onClick(MouseButtonEvent event, boolean doubleClick) {
        this.onPress.run();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        this.defaultButtonNarrationText(output);
    }
}
