// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.attribute;

import com.klikli_dev.codedefinedgui.filter.FilterStateAccessor;
import com.klikli_dev.codedefinedgui.registry.CDGDataComponents;
import net.minecraft.world.item.ItemStack;

public final class AttributeFilterStateAccessor implements FilterStateAccessor<AttributeFilterState> {
    public static final AttributeFilterStateAccessor INSTANCE = new AttributeFilterStateAccessor();

    private AttributeFilterStateAccessor() {
    }

    @Override
    public AttributeFilterState read(ItemStack stack) {
        return stack.getOrDefault(CDGDataComponents.ATTRIBUTE_FILTER_STATE.get(), AttributeFilterState.EMPTY);
    }

    @Override
    public void write(ItemStack stack, AttributeFilterState state) {
        if (AttributeFilterState.EMPTY.equals(state)) {
            stack.remove(CDGDataComponents.ATTRIBUTE_FILTER_STATE.get());
            return;
        }

        stack.set(CDGDataComponents.ATTRIBUTE_FILTER_STATE.get(), state);
    }
}
