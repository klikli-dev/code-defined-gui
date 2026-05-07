// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.style;

public interface StyleContext {
    <T> T get(GuiPartKey part, GuiStyleProperty<T> property, T fallback);
}
