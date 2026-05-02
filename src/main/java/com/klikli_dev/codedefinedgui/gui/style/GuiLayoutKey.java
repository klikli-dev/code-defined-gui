// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.style;

import java.util.Objects;
import net.minecraft.resources.Identifier;

public record GuiLayoutKey(Identifier id) {
    public GuiLayoutKey {
        Objects.requireNonNull(id, "id");
    }

    public static GuiLayoutKey of(Identifier id) {
        return new GuiLayoutKey(id);
    }
}
