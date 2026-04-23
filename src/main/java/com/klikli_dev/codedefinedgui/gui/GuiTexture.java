// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui;

import net.minecraft.resources.Identifier;

public record GuiTexture(Identifier sprite, int width, int height, int tint) {
    public GuiTexture(Identifier sprite, int width, int height) {
        this(sprite, width, height, -1);
    }

    public GuiTexture sized(int width, int height) {
        return this.sized(width, height, this.tint);
    }

    public GuiTexture sized(int width, int height, int tint) {
        return new GuiTexture(this.sprite, width, height, tint);
    }
}
