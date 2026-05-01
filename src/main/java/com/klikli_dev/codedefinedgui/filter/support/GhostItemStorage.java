// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.support;

import java.util.Objects;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

public class GhostItemStorage {
    private final NonNullList<ItemStack> contents;

    public GhostItemStorage(ItemContainerContents initialContents, int size) {
        this.contents = NonNullList.withSize(size, ItemStack.EMPTY);
        if (initialContents != null) {
            initialContents.copyInto(this.contents);
        }

        for (int slot = 0; slot < this.contents.size(); slot++) {
            this.contents.set(slot, normalizeStack(this.contents.get(slot)));
        }
    }

    public int size() {
        return this.contents.size();
    }

    public ItemStack getStackInSlotCopy(int slot) {
        Objects.checkIndex(slot, this.contents.size());
        return this.contents.get(slot).copy();
    }

    public ItemContainerContents contents() {
        return ItemContainerContents.fromItems(this.contents);
    }

    public boolean setStackInSlot(int slot, ItemStack stack) {
        Objects.checkIndex(slot, this.contents.size());
        ItemStack normalized = normalizeStack(stack);
        if (ItemStack.matches(this.contents.get(slot), normalized)) {
            return false;
        }

        this.contents.set(slot, normalized);
        return true;
    }

    public boolean clearContent() {
        boolean changed = false;
        for (int slot = 0; slot < this.contents.size(); slot++) {
            if (!this.contents.get(slot).isEmpty()) {
                this.contents.set(slot, ItemStack.EMPTY);
                changed = true;
            }
        }

        return changed;
    }

    public boolean hasItem(int index) {
        Objects.checkIndex(index, this.contents.size());
        return !this.contents.get(index).isEmpty();
    }

    private static ItemStack normalizeStack(ItemStack stack) {
        return stack.isEmpty() ? ItemStack.EMPTY : stack.copyWithCount(1);
    }
}
