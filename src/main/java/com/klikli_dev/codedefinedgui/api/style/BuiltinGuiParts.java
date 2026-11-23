// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.api.style;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import net.minecraft.resources.Identifier;

public final class BuiltinGuiParts {
    public static final GuiPartKey PLAYER_SLOT = key("player_slot");
    public static final GuiPartKey PLAYER_INVENTORY_BACKGROUND = key("player_inventory_background");
    public static final GuiPartKey PLAYER_INVENTORY_LABEL = key("player_inventory_label");

    /**
     * Tab button of a {@link com.klikli_dev.codedefinedgui.api.widget.tab.TabButtonWidget}: SPRITE, HOVER_SPRITE, ON_SPRITE (selected), DISABLED_TINT.
     */
    public static final GuiPartKey TAB = key("widget/tab");

    /**
     * Icon toggle button of an {@link com.klikli_dev.codedefinedgui.api.widget.IconToggleButtonWidget}: SPRITE, HOVER_SPRITE, ON_SPRITE (selected), DISABLED_SPRITE.
     */
    public static final GuiPartKey ICON_TOGGLE_BUTTON = key("widget/icon_toggle_button");

    /**
     * On/off switch drawn by {@link com.klikli_dev.codedefinedgui.api.widget.ToggleSwitch}: ON_SPRITE, OFF_SPRITE.
     */
    public static final GuiPartKey TOGGLE_SWITCH = key("widget/toggle_switch");

    /**
     * The hovered variant of {@link #TOGGLE_SWITCH}: ON_SPRITE, OFF_SPRITE.
     */
    public static final GuiPartKey TOGGLE_SWITCH_HOVER = key("widget/toggle_switch_hover");

    /**
     * Rows of a {@link com.klikli_dev.codedefinedgui.api.widget.ScrollListWidget}: COLOR (hover highlight), TEXT_COLOR.
     */
    public static final GuiPartKey LIST_ROW = key("widget/list_row");

    /**
     * Scrollbar of a {@link com.klikli_dev.codedefinedgui.api.widget.ScrollListWidget}: SPRITE (track), HANDLE_SPRITE, HANDLE_HOVER_SPRITE.
     */
    public static final GuiPartKey LIST_SCROLLBAR = key("widget/list_scrollbar");

    /**
     * Bar of a {@link com.klikli_dev.codedefinedgui.api.widget.ProgressBarWidget}: SPRITE (background), FILL_SPRITE.
     */
    public static final GuiPartKey PROGRESS_BAR = key("widget/progress_bar");

    /**
     * Text of a {@link com.klikli_dev.codedefinedgui.api.widget.WrappedTextWidget}: TEXT_COLOR.
     */
    public static final GuiPartKey TEXT = key("widget/text");

    private BuiltinGuiParts() {
    }

    private static GuiPartKey key(String path) {
        return GuiPartKey.of(Identifier.fromNamespaceAndPath(CodeDefinedGui.MODID, "gui/" + path));
    }
}
