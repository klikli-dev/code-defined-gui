// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.menu;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractFilterMenu extends AbstractContainerMenu {
    private static final int PLAYER_SLOT_COUNT = 36;

    protected final Player player;
    protected final InteractionHand hand;
    protected final ItemStack filterStack;
    protected final SimpleContainer ghostInventory;

    protected AbstractFilterMenu(MenuType<?> menuType, int containerId, Inventory inventory, RegistryFriendlyByteBuf buffer, int ghostSlots) {
        this(menuType, containerId, inventory, buffer.readEnum(InteractionHand.class), ghostSlots);
    }

    protected AbstractFilterMenu(MenuType<?> menuType, int containerId, Inventory inventory, InteractionHand hand, int ghostSlots) {
        super(menuType, containerId);
        this.player = inventory.player;
        this.hand = hand;
        this.filterStack = inventory.player.getItemInHand(hand);
        this.ghostInventory = new SimpleContainer(ghostSlots) {
            @Override
            public void setChanged() {
                super.setChanged();
                AbstractFilterMenu.this.onGhostInventoryChanged();
            }
        };

        this.addStandardInventorySlots(inventory, this.playerInventoryX(), this.playerInventoryY());
        this.loadFromFilterStack();
        this.addFilterSlots();
    }

    public ItemStack filterStack() {
        return this.filterStack;
    }

    public SimpleContainer ghostInventory() {
        return this.ghostInventory;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return player.isAlive() && player.getItemInHand(this.hand) == this.filterStack;
    }

    @Override
    public boolean canTakeItemForPickAll(@NotNull ItemStack stack, net.minecraft.world.inventory.Slot slotIn) {
        return slotIn.container != this.ghostInventory;
    }

    @Override
    public boolean canDragTo(net.minecraft.world.inventory.Slot slotIn) {
        return slotIn.container != this.ghostInventory;
    }

    @Override
    public void clicked(int slotId, int dragType, @NotNull ContainerInput clickTypeIn, @NotNull Player player) {
        if (slotId < PLAYER_SLOT_COUNT) {
            super.clicked(slotId, dragType, clickTypeIn, player);
            return;
        }

        int ghostSlot = slotId - PLAYER_SLOT_COUNT;
        if (!this.isGhostSlotInteractive(ghostSlot) || clickTypeIn == ContainerInput.THROW) {
            return;
        }

        ItemStack carried = this.getCarried();
        if (clickTypeIn == ContainerInput.CLONE) {
            if (player.isCreative() && carried.isEmpty()) {
                ItemStack stackInSlot = this.ghostInventory.getItem(ghostSlot);
                if (!stackInSlot.isEmpty()) {
                    this.setCarried(stackInSlot.copyWithCount(stackInSlot.getMaxStackSize()));
                }
            }
            return;
        }

        this.ghostInventory.setItem(ghostSlot, carried.isEmpty() ? ItemStack.EMPTY : carried.copyWithCount(1));
    }

    @Override
    public void slotsChanged(Container container) {
        super.slotsChanged(container);
        if (container == this.ghostInventory && !this.player.level().isClientSide()) {
            this.saveToFilterStack();
        }
    }

    protected void onGhostInventoryChanged() {
        if (!this.player.level().isClientSide()) {
            this.saveToFilterStack();
        }
    }

    protected boolean isGhostSlotInteractive(int ghostSlot) {
        return ghostSlot >= 0 && ghostSlot < this.ghostInventory.getContainerSize();
    }

    protected abstract int playerInventoryX();

    protected abstract int playerInventoryY();

    protected abstract void addFilterSlots();

    protected abstract void loadFromFilterStack();

    protected abstract void saveToFilterStack();
}
