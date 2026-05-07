// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.core.layout;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import net.minecraft.resources.Identifier;

public final class BuiltinFilterSlotRoles {
    public static final com.klikli_dev.codedefinedgui.gui.layout.SlotRoleKey FILTER_GRID = key("filter_grid");
    public static final com.klikli_dev.codedefinedgui.gui.layout.SlotRoleKey FILTER_REFERENCE = key("filter_reference");
    public static final com.klikli_dev.codedefinedgui.gui.layout.SlotRoleKey FILTER_SUMMARY = key("filter_summary");

    private BuiltinFilterSlotRoles() {
    }

    private static com.klikli_dev.codedefinedgui.gui.layout.SlotRoleKey key(String path) {
        return com.klikli_dev.codedefinedgui.gui.layout.SlotRoleKey.of(Identifier.fromNamespaceAndPath(CodeDefinedGui.MODID, path));
    }
}
