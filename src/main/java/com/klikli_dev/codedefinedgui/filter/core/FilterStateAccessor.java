// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter;

import net.minecraft.world.item.ItemStack;

public interface FilterStateAccessor<S extends FilterState> {
    S read(ItemStack stack);

    void write(ItemStack stack, S state);
}
