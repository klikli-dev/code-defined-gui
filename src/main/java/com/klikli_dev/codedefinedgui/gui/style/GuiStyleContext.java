// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.api.style;

public record GuiStyleContext(GuiStyle style) implements StyleContext {
    @Override
    public <T> T get(GuiPartKey part, GuiStyleProperty<T> property, T fallback) {
        return this.style.get(part, property, fallback);
    }
}

