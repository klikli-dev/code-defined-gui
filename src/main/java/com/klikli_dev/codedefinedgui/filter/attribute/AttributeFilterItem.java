// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.attribute;

import com.klikli_dev.codedefinedgui.filter.item.AbstractFilterItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class AttributeFilterItem extends AbstractFilterItem<AttributeFilterState> {
    public AttributeFilterItem(Properties properties) {
        super(properties, AttributeFilterDefinition.INSTANCE);
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory, InteractionHand hand) {
        return new AttributeFilterMenu(containerId, inventory, hand);
    }
}
