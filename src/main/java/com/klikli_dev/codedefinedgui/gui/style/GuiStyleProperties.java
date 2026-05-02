// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.style;

import com.klikli_dev.codedefinedgui.gui.texture.GuiSprite;

public final class GuiStyleProperties {
    public static final GuiStyleProperty<GuiSprite> SPRITE = property("sprite");
    public static final GuiStyleProperty<GuiSprite> HOVER_SPRITE = property("hover_sprite");
    public static final GuiStyleProperty<GuiSprite> PRESSED_SPRITE = property("pressed_sprite");
    public static final GuiStyleProperty<GuiSprite> ON_SPRITE = property("on_sprite");
    public static final GuiStyleProperty<GuiSprite> OFF_SPRITE = property("off_sprite");
    public static final GuiStyleProperty<Integer> OFFSET_X = property("offset_x");
    public static final GuiStyleProperty<Integer> OFFSET_Y = property("offset_y");
    public static final GuiStyleProperty<Integer> TEXT_COLOR = property("text_color");
    public static final GuiStyleProperty<Integer> COLOR = property("color");

    private GuiStyleProperties() {
    }

    private static <T> GuiStyleProperty<T> property(String name) {
        return GuiStyleProperty.of(name);
    }
}
