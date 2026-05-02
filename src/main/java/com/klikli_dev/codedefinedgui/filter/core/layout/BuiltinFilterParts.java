// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.core.layout;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import com.klikli_dev.codedefinedgui.gui.style.GuiPartKey;
import net.minecraft.resources.Identifier;

public final class BuiltinFilterParts {
    public static final GuiPartKey PLAYER_SLOT = key("player_slot");
    public static final GuiPartKey FILTER_SLOT = key("filter_slot");
    public static final GuiPartKey PLAYER_INVENTORY_BACKGROUND = key("player_inventory_background");
    public static final GuiPartKey BUTTON = key("button");
    public static final GuiPartKey INDICATOR = key("indicator");
    public static final GuiPartKey LIST_TOP_BAR = key("list_top_bar");
    public static final GuiPartKey LIST_PANEL = key("list_panel");
    public static final GuiPartKey LIST_HORIZONTAL_SEPARATOR = key("list_horizontal_separator");
    public static final GuiPartKey LIST_VERTICAL_SEPARATOR = key("list_vertical_separator");
    public static final GuiPartKey LIST_TITLE = key("list_title");
    public static final GuiPartKey ATTRIBUTE_TOP_BAR = key("attribute_top_bar");
    public static final GuiPartKey ATTRIBUTE_PANEL = key("attribute_panel");
    public static final GuiPartKey ATTRIBUTE_HORIZONTAL_SEPARATOR = key("attribute_horizontal_separator");
    public static final GuiPartKey ATTRIBUTE_VERTICAL_SEPARATOR = key("attribute_vertical_separator");
    public static final GuiPartKey ATTRIBUTE_TITLE = key("attribute_title");
    public static final GuiPartKey ATTRIBUTE_SELECTION = key("attribute_selection");
    public static final GuiPartKey ATTRIBUTE_SUMMARY = key("attribute_summary");

    private BuiltinFilterParts() {
    }

    public static GuiPartKey slotPart(SlotRoleKey role) {
        if (role.equals(BuiltinSlotRoles.PLAYER_MAIN) || role.equals(BuiltinSlotRoles.PLAYER_HOTBAR)) {
            return PLAYER_SLOT;
        }

        return FILTER_SLOT;
    }

    private static GuiPartKey key(String path) {
        return GuiPartKey.of(Identifier.fromNamespaceAndPath(CodeDefinedGui.MODID, "filter/" + path));
    }
}
