// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.api.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;

/**
 * Fallback values and shared behaviour of the widgets in this package. Used when the style has no value for a part.
 */
public final class WidgetDefaults {

    /**
     * Text color of list entries and wrapped text, cream like {@link AbstractScrollSelectionWidget}.
     */
    public static final int TEXT_COLOR = 0xFFF3EBDE;

    /**
     * Hover highlight of a list row, translucent white.
     */
    public static final int ROW_HIGHLIGHT = 0x33FFFFFF;

    /**
     * Multiplied into a sprite of a disabled widget, to dim it.
     */
    public static final int DISABLED_TINT = 0xFF9A9A9A;

    private WidgetDefaults() {
    }

    /**
     * Plays the vanilla button click. Used by the clickable widgets of this package, so they all sound the same.
     */
    public static void playClickSound() {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }
}


