// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.core.layout;

import java.util.Objects;
import net.minecraft.resources.Identifier;

public record SlotSkinKey(Identifier id) {
    public SlotSkinKey {
        Objects.requireNonNull(id, "id");
    }

    public static SlotSkinKey of(Identifier id) {
        return new SlotSkinKey(id);
    }
}
