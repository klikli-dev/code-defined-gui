// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.filter.layout;

import com.klikli_dev.codedefinedgui.gui.layout.LayoutSpec;

public final class AttributeFilterLayout {
    private AttributeFilterLayout() {
    }

    public static LayoutSpec create(PlayerInventoryLayoutFragment playerInventory) {
        return LayoutSpec.create(root -> {
            root.group("main", main -> {
                main.group("top_bar", topBar -> topBar.node("background").at(0, 0).size(241, 15));
                main.group("filter_area", area -> {
                    area.at(0, 12);
                    area.node("panel_bg").at(3, 0).size(235, 75);
                    area.node("reference").at(19, 12).size(18, 18);
                    area.node("selection").at(42, 11).size(137, 18);
                    area.node("add_button").at(190, 11).size(18, 18);
                    area.node("add_inverted_button").at(208, 11).size(18, 18);
                    area.node("horizontal_separator").at(3, 36).size(235, 1);
                    area.node("summary_widget").at(18, 43).size(24, 24);
                    area.node("summary_slot").at(22, 47).size(18, 18);
                    area.node("match_any").at(47, 49).size(18, 18);
                    area.node("match_all").at(65, 49).size(18, 18);
                    area.node("deny").at(83, 49).size(18, 18);
                    area.node("match_any_indicator").at(47, 43).size(18, 6);
                    area.node("match_all_indicator").at(65, 43).size(18, 6);
                    area.node("deny_indicator").at(83, 43).size(18, 6);
                    area.node("vertical_separator").at(202, 36).size(1, 39);
                    area.node("reset").at(179, 49).size(18, 18);
                    area.node("confirm").at(208, 49).size(18, 18);
                });
            });

            root.group("player_inventory", inv -> {
                inv.at(40, 109);
                playerInventory.define(inv);
            });
        });
    }
}
