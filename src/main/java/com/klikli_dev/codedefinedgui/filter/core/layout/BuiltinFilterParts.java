// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.core.layout;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import com.klikli_dev.codedefinedgui.api.layout.BuiltinLayoutSlotRoles;
import com.klikli_dev.codedefinedgui.api.layout.SlotRoleKey;
import com.klikli_dev.codedefinedgui.api.style.BuiltinGuiParts;
import com.klikli_dev.codedefinedgui.api.style.GuiPartKey;
import net.minecraft.resources.Identifier;

public final class BuiltinFilterParts {
    public static final GuiPartKey FILTER_SLOT = key("filter_slot");
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
    public static final GuiPartKey ATTRIBUTE_SELECTION_HEADER = key("attribute_selection_header");
    public static final GuiPartKey ATTRIBUTE_SUMMARY = key("attribute_summary");

    private BuiltinFilterParts() {
    }

    /**
     * Default slot part mapping for built-in filter menus.
     * <p>
     * All filter-owned ghost slots use {@link #FILTER_SLOT}. Player inventory visuals are now
     * provided by generic GUI parts. If a custom screen needs a more specific slot visual, it can
     * pass an explicit part key when adding the slot view instead of relying on this default
     * mapping.
     */
    public static GuiPartKey slotPart(SlotRoleKey role) {
        if (role.equals(BuiltinLayoutSlotRoles.PLAYER_MAIN) || role.equals(BuiltinLayoutSlotRoles.PLAYER_HOTBAR)) {
            return BuiltinGuiParts.PLAYER_SLOT;
        }

        return FILTER_SLOT;
    }

    private static GuiPartKey key(String path) {
        return GuiPartKey.of(Identifier.fromNamespaceAndPath(CodeDefinedGui.MODID, "filter/" + path));
    }
}

