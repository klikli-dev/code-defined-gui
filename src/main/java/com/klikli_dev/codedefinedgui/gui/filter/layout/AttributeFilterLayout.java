// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.filter.layout;

import com.klikli_dev.codedefinedgui.api.layout.LayoutNodeBuilder;
import com.klikli_dev.codedefinedgui.api.layout.LayoutSpec;
import com.klikli_dev.codedefinedgui.gui.layout.inventory.PlayerInventorySection;

public final class AttributeFilterLayout {
    private AttributeFilterLayout() {
    }

    public static LayoutSpec create(PlayerInventorySection playerInventory) {
        return LayoutSpec.create(root -> {
            root.group("main", main -> {
                main.group("top_bar", topBar -> {
                    LayoutNodeBuilder background = topBar.node("background").at(0, 0).size(241, 15);
                    topBar.node("title").at(background.x(), background.y() + 4).size(background.widthOrThrow(), 8);
                });
                main.group("filter_area", area -> {
                    area.at(0, 12);
                    LayoutNodeBuilder panel = area.node("panel_bg").at(3, 0).size(235, 75);
                    LayoutNodeBuilder reference = area.node("reference").at(panel.x() + 16, panel.y() + 12).size(18, 18);
                    LayoutNodeBuilder addInvertedButton = area.node("add_inverted_button").at(panel.maxX() - 30, panel.y() + 11).size(18, 18);
                    LayoutNodeBuilder addButton = area.node("add_button").at(addInvertedButton.x() - 18, addInvertedButton.y()).size(18, 18);
                    area.node("selection").at(reference.maxX() + 5, panel.y() + 11).size(addButton.x() - (reference.maxX() + 5) - 11, 18);
                    LayoutNodeBuilder horizontalSeparator = area.node("horizontal_separator").at(panel.x(), 36).size(panel.widthOrThrow(), 1);
                    LayoutNodeBuilder summaryWidget = area.node("summary_widget").at(reference.x() - 1, horizontalSeparator.y() + 7).size(24, 24);
                    area.node("summary_slot").at(summaryWidget.x() + 4, summaryWidget.y() + 4).size(18, 18);
                    LayoutNodeBuilder matchAny = area.node("match_any").at(summaryWidget.maxX() + 5, summaryWidget.y() + 6).size(18, 18);
                    LayoutNodeBuilder matchAll = area.node("match_all").at(matchAny.maxX(), matchAny.y()).size(18, 18);
                    LayoutNodeBuilder deny = area.node("deny").at(matchAll.maxX(), matchAll.y()).size(18, 18);
                    area.node("match_any_indicator").at(matchAny.x(), matchAny.y() - 6).size(18, 6);
                    area.node("match_all_indicator").at(matchAll.x(), matchAll.y() - 6).size(18, 6);
                    area.node("deny_indicator").at(deny.x(), deny.y() - 6).size(18, 6);
                    LayoutNodeBuilder confirm = area.node("confirm").at(panel.maxX() - 30, deny.y()).size(18, 18);
                    area.node("reset").at(confirm.x() - 29, confirm.y()).size(18, 18);
                    area.node("vertical_separator").at(confirm.x() - 6, horizontalSeparator.y()).size(1, panel.maxY() - horizontalSeparator.y());
                });
            });

            root.group("player_inventory", inv -> {
                inv.at(40, 109);
                playerInventory.define(inv);
            });
        });
    }
}
