// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.api.widget;

import com.klikli_dev.codedefinedgui.api.screen.GuiSyncable;
import com.klikli_dev.codedefinedgui.api.texture.GuiSprite;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Renders a GuiSprite at the given position.
 * Can be used to e.g. render slot backgrounds.
 * <p>
 * The sprite is stretched to the widget size, which defaults to the size of the sprite. The widget never reacts to the mouse.
 * Pass a {@link Supplier} to draw a sprite that changes, e.g. a status icon. Hide the widget with {@code visible = false}.
 */
public class GuiSpriteWidget extends AbstractWidget implements GuiSyncable {
    private final Supplier<GuiSprite> sprite;

    public GuiSpriteWidget(int x, int y, GuiSprite sprite) {
        this(x, y, sprite.width(), sprite.height(), sprite);
    }

    public GuiSpriteWidget(int x, int y, int width, int height, GuiSprite sprite) {
        this(x, y, width, height, () -> sprite);
    }

    /**
     * @param sprite the sprite to draw, read each frame
     */
    public GuiSpriteWidget(int x, int y, int width, int height, Supplier<GuiSprite> sprite) {
        super(x, y, width, height, Component.empty());
        this.sprite = Objects.requireNonNull(sprite);
        this.active = false;
    }

    @Override
    protected void extractWidgetRenderState(@NonNull GuiGraphicsExtractor guiGraphicsExtractor, int mouseX, int mouseY, float partialTick) {
        this.sprite.get().extractRenderState(guiGraphicsExtractor, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
    }

    @Override
    public void syncToHost() {
    }
}


