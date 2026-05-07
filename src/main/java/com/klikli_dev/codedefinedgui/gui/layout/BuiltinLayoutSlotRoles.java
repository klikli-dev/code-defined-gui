// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.api.layout;

import com.klikli_dev.codedefinedgui.api.layout.SlotRoleKey;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import net.minecraft.resources.Identifier;

public final class BuiltinLayoutSlotRoles {
    public static final SlotRoleKey PLAYER_MAIN = key("player_main");
    public static final SlotRoleKey PLAYER_HOTBAR = key("player_hotbar");

    private BuiltinLayoutSlotRoles() {
    }

    private static SlotRoleKey key(String path) {
        return SlotRoleKey.of(Identifier.fromNamespaceAndPath(CodeDefinedGui.MODID, path));
    }
}
