// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.list;

import com.klikli_dev.codedefinedgui.filter.menu.AbstractFilterMenu;
import com.klikli_dev.codedefinedgui.registry.CDGMenuTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import org.jetbrains.annotations.NotNull;

public class ListFilterMenu extends AbstractFilterMenu {
    public static final int BUTTON_RESET = 0;
    public static final int BUTTON_ALLOW = 1;
    public static final int BUTTON_DENY = 2;
    public static final int BUTTON_RESPECT_DATA = 3;
    public static final int BUTTON_IGNORE_DATA = 4;
    private static final int FILTER_SLOTS = 18;

    private final DataSlot mode = DataSlot.standalone();
    private final DataSlot respectDataComponents = DataSlot.standalone();

    public ListFilterMenu(int containerId, Inventory inventory, RegistryFriendlyByteBuf buffer) {
        this(containerId, inventory, buffer.readEnum(InteractionHand.class));
    }

    public ListFilterMenu(int containerId, Inventory inventory, InteractionHand hand) {
        super(CDGMenuTypes.LIST_FILTER.get(), containerId, inventory, hand, FILTER_SLOTS);

        ListFilterState state = ListFilterStateAccessor.INSTANCE.read(this.filterStack);
        this.mode.set(state.mode().ordinal());
        this.respectDataComponents.set(state.respectDataComponents() ? 1 : 0);
        this.addDataSlot(this.mode);
        this.addDataSlot(this.respectDataComponents);
    }

    public boolean isDenyList() {
        return this.mode.get() == ListFilterMode.DENY.ordinal();
    }

    public boolean respectDataComponents() {
        return this.respectDataComponents.get() == 1;
    }

    @Override
    public boolean clickMenuButton(Player player, int buttonId) {
        switch (buttonId) {
            case BUTTON_RESET -> {
                this.ghostInventory.clearContent();
                return true;
            }
            case BUTTON_ALLOW -> {
                this.mode.set(ListFilterMode.ALLOW.ordinal());
                this.saveToFilterStack();
                return true;
            }
            case BUTTON_DENY -> {
                this.mode.set(ListFilterMode.DENY.ordinal());
                this.saveToFilterStack();
                return true;
            }
            case BUTTON_RESPECT_DATA -> {
                this.respectDataComponents.set(1);
                this.saveToFilterStack();
                return true;
            }
            case BUTTON_IGNORE_DATA -> {
                this.respectDataComponents.set(0);
                this.saveToFilterStack();
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        if (index < 36) {
            ItemStack stackToInsert = this.slots.get(index).getItem();
            if (stackToInsert.isEmpty()) {
                return ItemStack.EMPTY;
            }

            for (int slot = 0; slot < this.ghostInventory.getContainerSize(); slot++) {
                ItemStack existing = this.ghostInventory.getItem(slot);
                if (!existing.isEmpty() && ItemStack.isSameItemSameComponents(existing, stackToInsert)) {
                    return ItemStack.EMPTY;
                }

                if (existing.isEmpty()) {
                    this.ghostInventory.setItem(slot, stackToInsert.copyWithCount(1));
                    return ItemStack.EMPTY;
                }
            }

            return ItemStack.EMPTY;
        }

        int ghostSlot = index - 36;
        if (ghostSlot >= 0 && ghostSlot < this.ghostInventory.getContainerSize()) {
            this.ghostInventory.setItem(ghostSlot, ItemStack.EMPTY);
        }

        return ItemStack.EMPTY;
    }

    @Override
    protected int playerInventoryX() {
        return 27;
    }

    @Override
    protected int playerInventoryY() {
        return 121;
    }

    @Override
    protected void addFilterSlots() {
        int x = 23;
        int y = 22;
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(this.ghostInventory, col + row * 9, x + col * 18, y + row * 18) {
                    @Override
                    public boolean mayPickup(Player player) {
                        return false;
                    }
                });
            }
        }
    }

    @Override
    protected void loadFromFilterStack() {
        ListFilterState state = ListFilterStateAccessor.INSTANCE.read(this.filterStack);
        NonNullList<ItemStack> items = NonNullList.withSize(FILTER_SLOTS, ItemStack.EMPTY);
        state.entries().copyInto(items);
        for (int slot = 0; slot < FILTER_SLOTS; slot++) {
            this.ghostInventory.setItem(slot, items.get(slot));
        }
    }

    @Override
    protected void saveToFilterStack() {
        NonNullList<ItemStack> items = NonNullList.withSize(FILTER_SLOTS, ItemStack.EMPTY);
        for (int slot = 0; slot < FILTER_SLOTS; slot++) {
            items.set(slot, this.ghostInventory.getItem(slot));
        }

        ListFilterStateAccessor.INSTANCE.write(this.filterStack, new ListFilterState(
                ItemContainerContents.fromItems(items),
                this.isDenyList() ? ListFilterMode.DENY : ListFilterMode.ALLOW,
                this.respectDataComponents()
        ));
    }
}
