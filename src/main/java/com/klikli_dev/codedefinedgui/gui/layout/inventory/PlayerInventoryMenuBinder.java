// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.layout.inventory;

import com.klikli_dev.codedefinedgui.gui.layout.BuiltinLayoutSlotRoles;
import com.klikli_dev.codedefinedgui.gui.layout.LayoutMenuBinder;
import com.klikli_dev.codedefinedgui.gui.layout.LayoutMenuBinderHost;
import com.klikli_dev.codedefinedgui.gui.layout.LayoutNodeView;
import com.klikli_dev.codedefinedgui.gui.layout.MenuBindingRegistry;
import com.klikli_dev.codedefinedgui.gui.style.BuiltinGuiParts;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public final class PlayerInventoryMenuBinder implements LayoutMenuBinder {
    @Override
    public void bind(MenuBindingRegistry registry, LayoutMenuBinderHost host) {
        Inventory inventory = host.playerInventory();
        registry.bind("main.slot_0", ctx -> {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 9; col++) {
                    int slot = col + row * 9 + 9;
                    String nodeId = "slot_" + (row * 9 + col);
                    LayoutNodeView node = row == 0 && col == 0 ? ctx.node() : ctx.node(nodeId);
                    host.addLayoutSlot(new Slot(inventory, slot, node.x(), node.y()), BuiltinLayoutSlotRoles.PLAYER_MAIN, BuiltinGuiParts.PLAYER_SLOT, "player_inventory.main." + nodeId);
                }
            }

            for (int col = 0; col < 9; col++) {
                String nodeId = "hotbar.slot_" + col;
                LayoutNodeView node = ctx.node(nodeId);
                host.addLayoutSlot(new Slot(inventory, col, node.x(), node.y()), BuiltinLayoutSlotRoles.PLAYER_HOTBAR, BuiltinGuiParts.PLAYER_SLOT, "player_inventory." + nodeId);
            }
        });
    }
}
