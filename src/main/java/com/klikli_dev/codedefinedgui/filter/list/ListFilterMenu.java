// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.list;

import com.klikli_dev.codedefinedgui.filter.core.FilterMenu;
import com.klikli_dev.codedefinedgui.infrastructure.registry.DataComponentRegistry;
import com.klikli_dev.codedefinedgui.infrastructure.registry.MenuTypeRegistry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ListFilterMenu extends FilterMenu {
    public static final int BUTTON_RESET = 0;
    public static final int BUTTON_ALLOW = 1;
    public static final int BUTTON_DENY = 2;
    public static final int BUTTON_RESPECT_DATA = 3;
    public static final int BUTTON_IGNORE_DATA = 4;
    public static final int BUTTON_CONFIRM = 5;
    public static final int BUTTON_CANCEL = 6;
    private static final int FILTER_SLOTS = 18;

    private final DataSlot mode = DataSlot.standalone();
    private final DataSlot respectDataComponents = DataSlot.standalone();

    public ListFilterMenu(int containerId, Inventory inventory, RegistryFriendlyByteBuf buffer) {
        this(MenuTypeRegistry.LIST_FILTER.get(), containerId, inventory, buffer.readEnum(InteractionHand.class));
    }

    public ListFilterMenu(int containerId, Inventory inventory, InteractionHand hand) {
        this(MenuTypeRegistry.LIST_FILTER.get(), containerId, inventory, hand);
    }

    protected ListFilterMenu(MenuType<?> menuType, int containerId, Inventory inventory, InteractionHand hand) {
        super(menuType, containerId, inventory, hand, FILTER_SLOTS, DataComponentRegistry.LIST_FILTER_CONTENTS.get());

        ListFilterState state = ListFilterStateAccessor.INSTANCE.read(this.filterStack());
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
                this.clearGhostContents();
                return true;
            }
            case BUTTON_ALLOW -> {
                this.mode.set(ListFilterMode.ALLOW.ordinal());
                return true;
            }
            case BUTTON_DENY -> {
                this.mode.set(ListFilterMode.DENY.ordinal());
                return true;
            }
            case BUTTON_RESPECT_DATA -> {
                this.respectDataComponents.set(1);
                return true;
            }
            case BUTTON_IGNORE_DATA -> {
                this.respectDataComponents.set(0);
                return true;
            }
            case BUTTON_CONFIRM -> {
                this.commitDraft();
                return true;
            }
            case BUTTON_CANCEL -> {
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

            for (int slot = 0; slot < FILTER_SLOTS; slot++) {
                ItemStack existing = this.ghostStack(slot);
                if (!existing.isEmpty() && ItemStack.isSameItemSameComponents(existing, stackToInsert)) {
                    return ItemStack.EMPTY;
                }

                if (existing.isEmpty()) {
                    this.setGhostStack(slot, stackToInsert);
                    return ItemStack.EMPTY;
                }
            }

            return ItemStack.EMPTY;
        }

        int ghostSlot = index - 36;
        if (ghostSlot >= 0 && ghostSlot < FILTER_SLOTS) {
            this.clearGhostStack(ghostSlot);
        }

        return ItemStack.EMPTY;
    }

    @Override
    protected int playerInventoryX() {
        return 27;
    }

    @Override
    protected int playerInventoryY() {
        return 123;
    }

    @Override
    protected void addFilterSlots() {
        int x = 25;
        int y = 24;
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 9; col++) {
                this.addGhostSlot(col + row * 9, x + col * 18, y + row * 18);
            }
        }
    }

    private void commitDraft() {
        ListFilterStateAccessor.INSTANCE.write(this.filterStack(), new ListFilterState(
                this.ghostStorage.contents(),
                this.isDenyList() ? ListFilterMode.DENY : ListFilterMode.ALLOW,
                this.respectDataComponents()
        ));
    }
}
