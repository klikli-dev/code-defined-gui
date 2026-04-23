// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.list;

import com.klikli_dev.codedefinedgui.filter.FilterStateAccessor;
import com.klikli_dev.codedefinedgui.registry.CDGDataComponents;
import net.minecraft.world.item.ItemStack;

public final class ListFilterStateAccessor implements FilterStateAccessor<ListFilterState> {
    public static final ListFilterStateAccessor INSTANCE = new ListFilterStateAccessor();

    private ListFilterStateAccessor() {
    }

    @Override
    public ListFilterState read(ItemStack stack) {
        return stack.getOrDefault(CDGDataComponents.LIST_FILTER_STATE.get(), ListFilterState.EMPTY);
    }

    @Override
    public void write(ItemStack stack, ListFilterState state) {
        if (ListFilterState.EMPTY.equals(state)) {
            stack.remove(CDGDataComponents.LIST_FILTER_STATE.get());
            return;
        }

        stack.set(CDGDataComponents.LIST_FILTER_STATE.get(), state);
    }
}
