// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.style;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import net.minecraft.resources.Identifier;

public final class BuiltinGuiParts {
    public static final GuiPartKey PLAYER_SLOT = key("player_slot");
    public static final GuiPartKey PLAYER_INVENTORY_BACKGROUND = key("player_inventory_background");

    private BuiltinGuiParts() {
    }

    private static GuiPartKey key(String path) {
        return GuiPartKey.of(Identifier.fromNamespaceAndPath(CodeDefinedGui.MODID, "gui/" + path));
    }
}
