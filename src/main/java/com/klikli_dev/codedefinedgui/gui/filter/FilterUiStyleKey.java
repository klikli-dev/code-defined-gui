// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.filter;

import java.util.Objects;
import net.minecraft.resources.Identifier;

public record FilterUiStyleKey(Identifier id) {
    public FilterUiStyleKey {
        Objects.requireNonNull(id, "id");
    }

    public static FilterUiStyleKey of(Identifier id) {
        return new FilterUiStyleKey(id);
    }
}
