// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.attribute;

import com.klikli_dev.codedefinedgui.filter.core.FilterStateAccessor;
import com.klikli_dev.codedefinedgui.registry.DataComponentRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

public final class AttributeFilterStateAccessor implements FilterStateAccessor<AttributeFilterState> {
    public static final AttributeFilterStateAccessor INSTANCE = new AttributeFilterStateAccessor();

    private AttributeFilterStateAccessor() {
    }

    @Override
    public AttributeFilterState read(ItemStack stack) {
        ItemContainerContents reference = stack.get(DataComponentRegistry.ATTRIBUTE_FILTER_REFERENCE.get());
        AttributeFilterConfig config = stack.get(DataComponentRegistry.ATTRIBUTE_FILTER_CONFIG.get());
        ItemStack referenceStack = ItemStack.EMPTY;
        if (reference != null) {
            NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);
            reference.copyInto(items);
            referenceStack = items.get(0);
        }

        return new AttributeFilterState(referenceStack, config != null ? config.mode() : AttributeFilterConfig.EMPTY.mode(), config != null ? config.rules() : AttributeFilterConfig.EMPTY.rules());
    }

    @Override
    public void write(ItemStack stack, AttributeFilterState state) {
        if (state.referenceStack().isEmpty()) {
            stack.remove(DataComponentRegistry.ATTRIBUTE_FILTER_REFERENCE.get());
        } else {
            stack.set(DataComponentRegistry.ATTRIBUTE_FILTER_REFERENCE.get(), ItemContainerContents.fromItems(NonNullList.of(ItemStack.EMPTY, state.referenceStack().copyWithCount(1))));
        }

        AttributeFilterConfig config = new AttributeFilterConfig(state.mode(), state.rules());
        if (AttributeFilterConfig.EMPTY.equals(config)) {
            stack.remove(DataComponentRegistry.ATTRIBUTE_FILTER_CONFIG.get());
        } else {
            stack.set(DataComponentRegistry.ATTRIBUTE_FILTER_CONFIG.get(), config);
        }
    }
}
