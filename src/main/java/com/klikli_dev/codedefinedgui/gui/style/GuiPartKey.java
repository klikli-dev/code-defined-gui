// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.style;

import java.util.Objects;
import net.minecraft.resources.Identifier;

public record GuiPartKey(Identifier id) {
    public GuiPartKey {
        Objects.requireNonNull(id, "id");
    }

    public static GuiPartKey of(Identifier id) {
        return new GuiPartKey(id);
    }
}
