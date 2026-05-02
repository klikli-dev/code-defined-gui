// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.core.layout;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import net.minecraft.resources.Identifier;

public final class BuiltinSlotSkins {
    public static final SlotSkinKey PLAYER_INVENTORY = key("player_inventory");
    public static final SlotSkinKey FILTER = key("filter");

    private BuiltinSlotSkins() {
    }

    private static SlotSkinKey key(String path) {
        return SlotSkinKey.of(Identifier.fromNamespaceAndPath(CodeDefinedGui.MODID, path));
    }
}
