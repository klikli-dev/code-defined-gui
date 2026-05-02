// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.core.layout;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import com.klikli_dev.codedefinedgui.gui.style.GuiLayoutKey;
import net.minecraft.resources.Identifier;

public final class BuiltinFilterLayouts {
    public static final GuiLayoutKey LIST_FILTER = key("list_filter");
    public static final GuiLayoutKey ATTRIBUTE_FILTER = key("attribute_filter");

    private BuiltinFilterLayouts() {
    }

    private static GuiLayoutKey key(String path) {
        return GuiLayoutKey.of(Identifier.fromNamespaceAndPath(CodeDefinedGui.MODID, path));
    }
}
