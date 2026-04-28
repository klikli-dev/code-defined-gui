// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.menu;

import java.util.Objects;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.item.ItemAccessItemHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;

public class GhostItemStorage extends ItemAccessItemHandler {
    public GhostItemStorage(ItemAccess itemAccess, DataComponentType<ItemContainerContents> component, int size) {
        super(itemAccess, component, size);
    }

    @Override
    public boolean isValid(int index, ItemResource resource) {
        return this.itemAccess.getResource().is(this.validItem);
    }

    @Override
    protected int getCapacity(int index, ItemResource resource) {
        return 1;
    }

    public ItemStack getStackInSlotCopy(int slot) {
        Objects.checkIndex(slot, this.size);
        return this.getResource(slot).toStack(this.getAmountAsInt(slot));
    }

    public ItemContainerContents contents() {
        return this.getContents(this.itemAccess.getResource());
    }

    public boolean setStackInSlot(int slot, ItemStack stack) {
        Objects.checkIndex(slot, this.size);
        NonNullList<ItemStack> contents = this.readContents();
        ItemStack normalized = normalizeStack(stack);
        if (ItemStack.matches(contents.get(slot), normalized)) {
            return false;
        }

        contents.set(slot, normalized);
        this.writeContents(contents);
        return true;
    }

    public boolean clearContent() {
        NonNullList<ItemStack> contents = this.readContents();
        boolean changed = false;
        for (int slot = 0; slot < contents.size(); slot++) {
            if (!contents.get(slot).isEmpty()) {
                contents.set(slot, ItemStack.EMPTY);
                changed = true;
            }
        }

        if (!changed) {
            return false;
        }

        this.writeContents(contents);
        return true;
    }

    private NonNullList<ItemStack> readContents() {
        NonNullList<ItemStack> contents = NonNullList.withSize(this.size, ItemStack.EMPTY);
        this.contents().copyInto(contents);
        for (int slot = 0; slot < contents.size(); slot++) {
            contents.set(slot, normalizeStack(contents.get(slot)));
        }
        return contents;
    }

    private void writeContents(NonNullList<ItemStack> contents) {
        ItemResource accessResource = this.itemAccess.getResource();
        if (!accessResource.is(this.validItem)) {
            return;
        }

        NonNullList<ItemStack> normalized = NonNullList.withSize(this.size, ItemStack.EMPTY);
        boolean empty = true;
        for (int slot = 0; slot < this.size; slot++) {
            ItemStack stack = slot < contents.size() ? normalizeStack(contents.get(slot)) : ItemStack.EMPTY;
            normalized.set(slot, stack);
            if (!stack.isEmpty()) {
                empty = false;
            }
        }

        ItemResource updated = empty
                ? accessResource.without(this.component)
                : accessResource.with(this.component, ItemContainerContents.fromItems(normalized));

        try (Transaction transaction = Transaction.openRoot()) {
            this.itemAccess.exchange(updated, this.itemAccess.getAmount(), transaction);
            transaction.commit();
        }
    }

    private static ItemStack normalizeStack(ItemStack stack) {
        return stack.isEmpty() ? ItemStack.EMPTY : stack.copyWithCount(1);
    }
}
