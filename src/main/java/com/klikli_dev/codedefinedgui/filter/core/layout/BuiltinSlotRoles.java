// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.core.layout;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import net.minecraft.resources.Identifier;

public final class BuiltinSlotRoles {
    public static final SlotRoleKey PLAYER_MAIN = key("player_main");
    public static final SlotRoleKey PLAYER_HOTBAR = key("player_hotbar");
    public static final SlotRoleKey FILTER_GRID = key("filter_grid");
    public static final SlotRoleKey FILTER_REFERENCE = key("filter_reference");
    public static final SlotRoleKey FILTER_SUMMARY = key("filter_summary");

    private BuiltinSlotRoles() {
    }

    private static SlotRoleKey key(String path) {
        return SlotRoleKey.of(Identifier.fromNamespaceAndPath(CodeDefinedGui.MODID, path));
    }
}
