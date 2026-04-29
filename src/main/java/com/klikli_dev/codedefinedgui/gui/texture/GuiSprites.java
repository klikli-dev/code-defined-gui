// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.texture;

import com.klikli_dev.codedefinedgui.infrastructure.CodeDefinedGui;
import net.minecraft.resources.Identifier;

public final class GuiSprites {
    public static final GuiSprite GUI_BACKGROUND = new GuiSprite(sprite("core/gui_background"), 14, 14);
    public static final GuiSprite INVENTORY_SLOT = new GuiSprite(sprite("core/inventory_slot"), 18, 18);
    public static final GuiSprite CRAFTING_RESULT_SLOT = new GuiSprite(sprite("core/crafting_result_slot"), 26, 26);
    public static final GuiSprite CRAFTING_ARROW = new GuiSprite(sprite("core/crafting_arrow"), 22, 15);
    public static final GuiSprite ATTRIBUTE_FILTER_SELECTION = new GuiSprite(sprite("filter/attribute_filter_selection"), 137, 18);
    public static final GuiSprite ATTRIBUTE_FILTER_SUMMARY = new GuiSprite(sprite("filter/attribute_filter_summary"), 24, 24);
    public static final GuiSprite FILTER_BUTTON = new GuiSprite(sprite("widget/button"), 18, 18);
    public static final GuiSprite FILTER_BUTTON_HOVER = new GuiSprite(sprite("widget/button_hover"), 18, 18);
    public static final GuiSprite FILTER_BUTTON_DOWN = new GuiSprite(sprite("widget/button_down"), 18, 18);
    public static final GuiSprite FILTER_INDICATOR_OFF = new GuiSprite(sprite("widget/indicator"), 18, 6);
    public static final GuiSprite FILTER_INDICATOR_ON = new GuiSprite(sprite("widget/indicator_green"), 18, 6);
    public static final GuiSprite FILTER_ICON_RESET = new GuiSprite(sprite("widget/trash"), 16, 16);
    public static final GuiSprite FILTER_ICON_CONFIRM = new GuiSprite(sprite("widget/confirm"), 16, 16);
    public static final GuiSprite FILTER_ICON_ALLOW_LIST = new GuiSprite(sprite("filter/accept_list"), 16, 16);
    public static final GuiSprite FILTER_ICON_DENY_LIST = new GuiSprite(sprite("filter/deny_list"), 16, 16);
    public static final GuiSprite FILTER_ICON_MATCH_ANY = new GuiSprite(sprite("filter/accept_list_or"), 16, 16);
    public static final GuiSprite FILTER_ICON_MATCH_ALL = new GuiSprite(sprite("filter/accept_list_and"), 16, 16);
    public static final GuiSprite FILTER_ICON_DENY_ALT = new GuiSprite(sprite("filter/deny_list_alt"), 16, 16);
    public static final GuiSprite FILTER_ICON_RESPECT_DATA_COMPONENTS = new GuiSprite(sprite("filter/respect_data_components"), 16, 16);
    public static final GuiSprite FILTER_ICON_IGNORE_DATA_COMPONENTS = new GuiSprite(sprite("filter/ignore_data_components"), 16, 16);
    public static final GuiSprite FILTER_ICON_ADD = new GuiSprite(sprite("widget/add"), 16, 16);
    public static final GuiSprite FILTER_ICON_ADD_INVERTED = new GuiSprite(sprite("widget/add_inverted"), 16, 16);

    private GuiSprites() {
    }

    private static Identifier sprite(String path) {
        return Identifier.fromNamespaceAndPath(CodeDefinedGui.MODID, path);
    }
}
