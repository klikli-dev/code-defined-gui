// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.core.layout;

import java.util.Objects;
import net.minecraft.resources.Identifier;

public record FilterSlotRoleKey(Identifier id) {
    public FilterSlotRoleKey {
        Objects.requireNonNull(id, "id");
    }

    public static FilterSlotRoleKey of(Identifier id) {
        return new FilterSlotRoleKey(id);
    }
}
