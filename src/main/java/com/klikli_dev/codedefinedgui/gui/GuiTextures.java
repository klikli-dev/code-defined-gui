// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import net.minecraft.resources.Identifier;

public final class GuiTextures {
    public static final GuiTexture GUI_BACKGROUND = new GuiTexture(sprite("gui_background"), 14, 14);
    public static final GuiTexture INVENTORY_SLOT = new GuiTexture(sprite("inventory_slot"), 18, 18);
    public static final GuiTexture CRAFTING_RESULT_SLOT = new GuiTexture(sprite("crafting_result_slot"), 26, 26);
    public static final GuiTexture CRAFTING_ARROW = new GuiTexture(sprite("crafting_arrow"), 22, 15);

    private GuiTextures() {
    }

    private static Identifier sprite(String path) {
        return Identifier.fromNamespaceAndPath(CodeDefinedGui.MODID, path);
    }
}
