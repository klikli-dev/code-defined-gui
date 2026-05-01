// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.support;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.world.inventory.StackCopySlot;

public class GhostResourceHandlerSlot extends StackCopySlot {
    private final GhostItemStorage storage;

    public GhostResourceHandlerSlot(GhostItemStorage storage, int slot, int xPosition, int yPosition) {
        super(slot, xPosition, yPosition);
        this.storage = storage;
    }

    @Override
    protected ItemStack getStackCopy() {
        return this.storage.getStackInSlotCopy(this.getSlotIndex());
    }

    @Override
    protected void setStackCopy(ItemStack stack) {
        this.storage.setStackInSlot(this.getSlotIndex(), stack);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return !stack.isEmpty();
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
