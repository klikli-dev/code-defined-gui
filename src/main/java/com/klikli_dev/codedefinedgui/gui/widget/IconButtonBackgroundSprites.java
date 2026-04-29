// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.widget;

import com.klikli_dev.codedefinedgui.gui.texture.GuiSprite;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprites;
import java.util.Objects;

public record IconButtonBackgroundSprites(GuiSprite normal, GuiSprite pressed, GuiSprite hovered) {
    public static final IconButtonBackgroundSprites DEFAULT = new IconButtonBackgroundSprites(
            GuiSprites.FILTER_BUTTON,
            GuiSprites.FILTER_BUTTON_DOWN,
            GuiSprites.FILTER_BUTTON_HOVER
    );

    public IconButtonBackgroundSprites {
        Objects.requireNonNull(normal);
        Objects.requireNonNull(pressed);
        Objects.requireNonNull(hovered);

        if (normal.width() != pressed.width() || normal.width() != hovered.width() || normal.height() != pressed.height() || normal.height() != hovered.height()) {
            throw new IllegalArgumentException("Icon button background sprites must share the same dimensions");
        }
    }
}
