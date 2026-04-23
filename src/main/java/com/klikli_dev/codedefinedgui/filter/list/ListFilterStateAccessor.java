// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.list;

import com.klikli_dev.codedefinedgui.filter.FilterStateAccessor;
import com.klikli_dev.codedefinedgui.registry.CDGDataComponents;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

public final class ListFilterStateAccessor implements FilterStateAccessor<ListFilterState> {
    public static final ListFilterStateAccessor INSTANCE = new ListFilterStateAccessor();

    private ListFilterStateAccessor() {
    }

    @Override
    public ListFilterState read(ItemStack stack) {
        ItemContainerContents contents = stack.get(CDGDataComponents.LIST_FILTER_CONTENTS.get());
        ListFilterConfig config = stack.get(CDGDataComponents.LIST_FILTER_CONFIG.get());
        if (contents != null || config != null) {
            return new ListFilterState(contents != null ? contents : ItemContainerContents.EMPTY, config != null ? config.mode() : ListFilterConfig.EMPTY.mode(), config != null && config.respectDataComponents());
        }

        return stack.getOrDefault(CDGDataComponents.LIST_FILTER_STATE.get(), ListFilterState.EMPTY);
    }

    @Override
    public void write(ItemStack stack, ListFilterState state) {
        ListFilterConfig config = new ListFilterConfig(state.mode(), state.respectDataComponents());
        if (isEmptyContents(state.entries())) {
            stack.remove(CDGDataComponents.LIST_FILTER_CONTENTS.get());
        } else {
            stack.set(CDGDataComponents.LIST_FILTER_CONTENTS.get(), state.entries());
        }

        if (ListFilterConfig.EMPTY.equals(config)) {
            stack.remove(CDGDataComponents.LIST_FILTER_CONFIG.get());
        } else {
            stack.set(CDGDataComponents.LIST_FILTER_CONFIG.get(), config);
        }

        stack.remove(CDGDataComponents.LIST_FILTER_STATE.get());
    }

    private static boolean isEmptyContents(ItemContainerContents contents) {
        NonNullList<ItemStack> items = NonNullList.withSize(contents.getSlots(), ItemStack.EMPTY);
        contents.copyInto(items);
        for (ItemStack item : items) {
            if (!item.isEmpty()) {
                return false;
            }
        }

        return true;
    }
}
