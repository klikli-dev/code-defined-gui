// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.api.widget;

import com.klikli_dev.codedefinedgui.api.style.BuiltinGuiParts;
import com.klikli_dev.codedefinedgui.api.style.GuiPartKey;
import com.klikli_dev.codedefinedgui.api.style.StyleContext;
import com.klikli_dev.codedefinedgui.api.texture.GuiSprite;
import com.klikli_dev.codedefinedgui.api.texture.GuiSprites;
import net.minecraft.client.gui.GuiGraphicsExtractor;

/**
 * An on/off switch of 26x12 pixels with a hover variant. It is not a widget of its own: the owner draws it, e.g. a row of a
 * {@link ScrollListWidget}, and handles the click for the whole row. This keeps the switch in one place for every list.
 * <p>
 * Styled with {@link BuiltinGuiParts#TOGGLE_SWITCH} and {@link BuiltinGuiParts#TOGGLE_SWITCH_HOVER}, both with {@code ON_SPRITE} and
 * {@code OFF_SPRITE}.
 */
public final class ToggleSwitch {

    public static final int WIDTH = 26;
    public static final int HEIGHT = 12;

    private ToggleSwitch() {
    }

    /**
     * @param graphics the graphics to draw on
     * @param style the style to take the sprites from
     * @param x left edge
     * @param y top edge
     * @param on whether the switch is on
     * @param hovered whether the mouse hovers the switch, the hover sprite is drawn then
     */
    public static void extract(GuiGraphicsExtractor graphics, StyleContext style, int x, int y, boolean on, boolean hovered) {
        GuiSprite sprite;
        if (hovered) {
            sprite = on
                    ? style.onSprite(BuiltinGuiParts.TOGGLE_SWITCH_HOVER, GuiSprites.TOGGLE_ON_HOVER)
                    : style.offSprite(BuiltinGuiParts.TOGGLE_SWITCH_HOVER, GuiSprites.TOGGLE_OFF_HOVER);
        } else {
            sprite = on
                    ? style.onSprite(BuiltinGuiParts.TOGGLE_SWITCH, GuiSprites.TOGGLE_ON)
                    : style.offSprite(BuiltinGuiParts.TOGGLE_SWITCH, GuiSprites.TOGGLE_OFF);
        }

        sprite.extractRenderState(graphics, x, y, WIDTH, HEIGHT);
    }
}
