// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.style;

import java.util.Objects;

public record GuiStyleProperty<T>(String name) {
    public GuiStyleProperty {
        Objects.requireNonNull(name, "name");
    }

    public static <T> GuiStyleProperty<T> of(String name) {
        return new GuiStyleProperty<>(name);
    }
}
