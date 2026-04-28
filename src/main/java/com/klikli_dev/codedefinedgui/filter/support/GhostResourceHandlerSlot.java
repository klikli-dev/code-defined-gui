// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.menu;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;

public class GhostResourceHandlerSlot extends ResourceHandlerSlot {
    public GhostResourceHandlerSlot(GhostItemStorage storage, int slot, int xPosition, int yPosition) {
        super(storage, (index, resource, amount) -> storage.setStackInSlot(index, resource.toStack(amount)), slot, xPosition, yPosition);
    }

    @Override
    public boolean mayPickup(Player player) {
        return false;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }
}
