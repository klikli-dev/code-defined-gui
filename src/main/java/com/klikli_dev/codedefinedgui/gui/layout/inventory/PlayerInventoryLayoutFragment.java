// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.layout.inventory;

import com.klikli_dev.codedefinedgui.gui.layout.LayoutFragment;
import com.klikli_dev.codedefinedgui.gui.layout.LayoutGroupBuilder;

public final class PlayerInventoryLayoutFragment implements LayoutFragment {
    private static final int SLOT_SIZE = 18;
    private static final int PLAYER_MAIN_ROWS = 3;
    private static final int PLAYER_MAIN_COLUMNS = 9;
    private static final int HOTBAR_Y = 58;
    private static final int BACKGROUND_PADDING = 8;

    private PlayerInventoryLayoutFragment() {
    }

    public static PlayerInventoryLayoutFragment create() {
        return new PlayerInventoryLayoutFragment();
    }

    @Override
    public void define(LayoutGroupBuilder root) {
        root.node("background").at(-BACKGROUND_PADDING, -BACKGROUND_PADDING).size(176, 90);
        root.group("main", main -> {
            for (int row = 0; row < PLAYER_MAIN_ROWS; row++) {
                for (int col = 0; col < PLAYER_MAIN_COLUMNS; col++) {
                    main.node("slot_" + (row * PLAYER_MAIN_COLUMNS + col)).at(col * SLOT_SIZE, row * SLOT_SIZE).size(SLOT_SIZE, SLOT_SIZE);
                }
            }
        }).group("hotbar", hotbar -> {
            hotbar.at(0, HOTBAR_Y);
            for (int col = 0; col < PLAYER_MAIN_COLUMNS; col++) {
                hotbar.node("slot_" + col).at(col * SLOT_SIZE, 0).size(SLOT_SIZE, SLOT_SIZE);
            }
        });
    }
}
