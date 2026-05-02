// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.core.layout;

import java.util.Objects;
import net.minecraft.resources.Identifier;

public record SlotRoleKey(Identifier id) {
    public SlotRoleKey {
        Objects.requireNonNull(id, "id");
    }

    public static SlotRoleKey of(Identifier id) {
        return new SlotRoleKey(id);
    }
}
