// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.texture;

import com.klikli_dev.codedefinedgui.infrastructure.CodeDefinedGui;
import net.minecraft.resources.Identifier;

public final class GuiTextures {
    public static final GuiTexture GUI_BACKGROUND = new GuiTexture(sprite("core/gui_background"), 14, 14);
    public static final GuiTexture INVENTORY_SLOT = new GuiTexture(sprite("core/inventory_slot"), 18, 18);
    public static final GuiTexture CRAFTING_RESULT_SLOT = new GuiTexture(sprite("core/crafting_result_slot"), 26, 26);
    public static final GuiTexture CRAFTING_ARROW = new GuiTexture(sprite("core/crafting_arrow"), 22, 15);
    public static final GuiTexture ATTRIBUTE_FILTER_SELECTION = new GuiTexture(sprite("filter/attribute_filter_selection"), 137, 18);
    public static final GuiTexture ATTRIBUTE_FILTER_SUMMARY = new GuiTexture(sprite("filter/attribute_filter_summary"), 24, 24);
    public static final GuiTexture FILTER_BUTTON = new GuiTexture(sprite("widget/button"), 18, 18);
    public static final GuiTexture FILTER_BUTTON_HOVER = new GuiTexture(sprite("widget/button_hover"), 18, 18);
    public static final GuiTexture FILTER_BUTTON_DOWN = new GuiTexture(sprite("widget/button_down"), 18, 18);
    public static final GuiTexture FILTER_INDICATOR_OFF = new GuiTexture(sprite("widget/indicator"), 18, 6);
    public static final GuiTexture FILTER_INDICATOR_ON = new GuiTexture(sprite("widget/indicator_green"), 18, 6);
    public static final GuiTexture FILTER_ICON_RESET = new GuiTexture(sprite("widget/trash"), 16, 16);
    public static final GuiTexture FILTER_ICON_CONFIRM = new GuiTexture(sprite("widget/confirm"), 16, 16);
    public static final GuiTexture FILTER_ICON_ALLOW_LIST = new GuiTexture(sprite("filter/accept_list"), 16, 16);
    public static final GuiTexture FILTER_ICON_DENY_LIST = new GuiTexture(sprite("filter/deny_list"), 16, 16);
    public static final GuiTexture FILTER_ICON_MATCH_ANY = new GuiTexture(sprite("filter/accept_list_or"), 16, 16);
    public static final GuiTexture FILTER_ICON_MATCH_ALL = new GuiTexture(sprite("filter/accept_list_and"), 16, 16);
    public static final GuiTexture FILTER_ICON_DENY_ALT = new GuiTexture(sprite("filter/deny_list_alt"), 16, 16);
    public static final GuiTexture FILTER_ICON_RESPECT_DATA_COMPONENTS = new GuiTexture(sprite("filter/respect_data_components"), 16, 16);
    public static final GuiTexture FILTER_ICON_IGNORE_DATA_COMPONENTS = new GuiTexture(sprite("filter/ignore_data_components"), 16, 16);
    public static final GuiTexture FILTER_ICON_ADD = new GuiTexture(sprite("widget/add"), 16, 16);
    public static final GuiTexture FILTER_ICON_ADD_INVERTED = new GuiTexture(sprite("widget/add_inverted"), 16, 16);

    private GuiTextures() {
    }

    private static Identifier sprite(String path) {
        return Identifier.fromNamespaceAndPath(CodeDefinedGui.MODID, path);
    }
}
