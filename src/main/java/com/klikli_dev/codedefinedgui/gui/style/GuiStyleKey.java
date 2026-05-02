// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.style;

import java.util.Objects;
import net.minecraft.resources.Identifier;

public record GuiStyleKey(Identifier id) {
    public GuiStyleKey {
        Objects.requireNonNull(id, "id");
    }

    public static GuiStyleKey of(Identifier id) {
        return new GuiStyleKey(id);
    }
}
