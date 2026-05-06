// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.filter.layout;

import com.klikli_dev.codedefinedgui.gui.layout.LayoutSpec;

public final class ListFilterLayout {
    private ListFilterLayout() {
    }

    public static LayoutSpec create(PlayerInventoryLayoutFragment playerInventory) {
        return LayoutSpec.create(root -> {
            root.group("main", main -> {
                main.group("top_bar", topBar -> topBar.node("background").at(0, 0).size(214, 15));
                main.group("filter_area", area -> {
                    area.at(0, 12);
                    area.node("panel_bg").at(3, 0).size(208, 87);
                    area.group("slots", slots -> {
                        slots.at(25, 12);
                        for (int row = 0; row < 2; row++) {
                            for (int col = 0; col < 9; col++) {
                                slots.node("slot_" + (row * 9 + col)).at(col * 18, row * 18).size(18, 18);
                            }
                        }
                    });
                    area.node("horizontal_separator").at(3, 52).size(208, 1);
                    area.node("vertical_separator").at(145, 52).size(1, 35);
                    area.node("deny").at(18, 63).size(18, 18);
                    area.node("allow").at(36, 63).size(18, 18);
                    area.node("respect_data").at(60, 63).size(18, 18);
                    area.node("ignore_data").at(78, 63).size(18, 18);
                    area.node("deny_indicator").at(18, 57).size(18, 6);
                    area.node("allow_indicator").at(36, 57).size(18, 6);
                    area.node("respect_data_indicator").at(60, 57).size(18, 6);
                    area.node("ignore_data_indicator").at(78, 57).size(18, 6);
                    area.node("reset").at(152, 63).size(18, 18);
                    area.node("confirm").at(181, 63).size(18, 18);
                });
            });

            root.group("player_inventory", inv -> {
                inv.at(27, 123);
                playerInventory.define(inv);
            });
        });
    }
}
