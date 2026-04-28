// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.core;

import java.util.List;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public interface FilterDefinition<S extends FilterState> {
    Identifier id();

    FilterStateAccessor<S> accessor();

    S defaultState();

    boolean matches(ItemStack candidate, S state, FilterMatchContext context);

    List<Component> summary(S state, HolderLookup.Provider registries);

    default boolean matches(ItemStack filterStack, ItemStack candidate, FilterMatchContext context) {
        return this.matches(candidate, this.accessor().read(filterStack), context);
    }

    default List<Component> summary(ItemStack filterStack, HolderLookup.Provider registries) {
        return this.summary(this.accessor().read(filterStack), registries);
    }
}
