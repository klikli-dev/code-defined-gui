// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.premade.filter.layout;

import com.klikli_dev.codedefinedgui.api.layout.LayoutNodeBuilder;
import com.klikli_dev.codedefinedgui.api.layout.LayoutSpec;
import com.klikli_dev.codedefinedgui.internal.layout.inventory.PlayerInventorySection;

public final class ListFilterLayout {
    private ListFilterLayout() {
    }

    public static LayoutSpec create(PlayerInventorySection playerInventory) {
        return LayoutSpec.create(root -> {
            root.group("main", main -> {
                main.group("top_bar", topBar -> {
                    LayoutNodeBuilder background = topBar.node("background").at(0, 0).size(214, 15);
                    topBar.node("title").at(background.x(), background.y() + 4).size(background.widthOrThrow(), 8);
                });
                main.group("filter_area", area -> {
                    area.at(0, 12);
                    LayoutNodeBuilder panel = area.node("panel_bg").at(3, 0).size(208, 87);
                    area.group("slots", slots -> {
                        slots.at(panel.x() + 22, panel.y() + 12);
                        for (int row = 0; row < 2; row++) {
                            for (int col = 0; col < 9; col++) {
                                slots.node("slot_" + (row * 9 + col)).at(col * 18, row * 18).size(18, 18);
                            }
                        }
                    });
                    LayoutNodeBuilder horizontalSeparator = area.node("horizontal_separator").at(panel.x(), 52).size(panel.widthOrThrow(), 1);
                    area.node("vertical_separator").at(145, horizontalSeparator.y()).size(1, panel.maxY() - horizontalSeparator.y());
                    LayoutNodeBuilder deny = area.node("deny").at(18, 63).size(18, 18);
                    LayoutNodeBuilder allow = area.node("allow").at(deny.maxX(), deny.y()).size(18, 18);
                    LayoutNodeBuilder respectData = area.node("respect_data").at(allow.maxX() + 6, deny.y()).size(18, 18);
                    LayoutNodeBuilder ignoreData = area.node("ignore_data").at(respectData.maxX(), deny.y()).size(18, 18);
                    area.node("deny_indicator").at(deny.x(), deny.y() - 6).size(18, 6);
                    area.node("allow_indicator").at(allow.x(), allow.y() - 6).size(18, 6);
                    area.node("respect_data_indicator").at(respectData.x(), respectData.y() - 6).size(18, 6);
                    area.node("ignore_data_indicator").at(ignoreData.x(), ignoreData.y() - 6).size(18, 6);
                    LayoutNodeBuilder confirm = area.node("confirm").at(panel.maxX() - 30, deny.y()).size(18, 18);
                    area.node("reset").at(confirm.x() - 29, confirm.y()).size(18, 18);
                });
            });

            root.group("player_inventory", inv -> {
                inv.at(27, 123);
                playerInventory.define(inv);
            });
        });
    }
}




