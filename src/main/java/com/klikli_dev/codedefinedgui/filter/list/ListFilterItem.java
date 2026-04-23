// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.list;

import com.klikli_dev.codedefinedgui.filter.item.AbstractFilterItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class ListFilterItem extends AbstractFilterItem<ListFilterState> {
    public ListFilterItem(Properties properties) {
        super(properties, ListFilterDefinition.INSTANCE);
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory, InteractionHand hand) {
        return new ListFilterMenu(containerId, inventory, hand);
    }
}
