// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.style;

import com.klikli_dev.codedefinedgui.gui.texture.GuiSprite;
import com.klikli_dev.codedefinedgui.gui.widget.IconButtonBackgroundSprites;

public interface StyleContext {
    <T> T get(GuiPartKey part, GuiStyleProperty<T> property, T fallback);

    default GuiSprite sprite(GuiPartKey part, GuiSprite fallback) {
        return this.get(part, GuiStyleProperties.SPRITE, fallback);
    }

    default GuiSprite hoverSprite(GuiPartKey part, GuiSprite fallback) {
        return this.get(part, GuiStyleProperties.HOVER_SPRITE, fallback);
    }

    default GuiSprite pressedSprite(GuiPartKey part, GuiSprite fallback) {
        return this.get(part, GuiStyleProperties.PRESSED_SPRITE, fallback);
    }

    default GuiSprite onSprite(GuiPartKey part, GuiSprite fallback) {
        return this.get(part, GuiStyleProperties.ON_SPRITE, fallback);
    }

    default GuiSprite offSprite(GuiPartKey part, GuiSprite fallback) {
        return this.get(part, GuiStyleProperties.OFF_SPRITE, fallback);
    }

    default int textColor(GuiPartKey part, int fallback) {
        int color = this.get(part, GuiStyleProperties.TEXT_COLOR, fallback);
        return (color & 0xFF000000) == 0 ? color | 0xFF000000 : color;
    }

    default int color(GuiPartKey part, int fallback) {
        int color = this.get(part, GuiStyleProperties.COLOR, fallback);
        return (color & 0xFF000000) == 0 ? color | 0xFF000000 : color;
    }

    default IconButtonBackgroundSprites iconButtonBackgroundSprites(GuiPartKey part, IconButtonBackgroundSprites fallback) {
        return new IconButtonBackgroundSprites(
                this.sprite(part, fallback.normal()),
                this.pressedSprite(part, fallback.pressed()),
                this.hoverSprite(part, fallback.hovered())
        );
    }
}
