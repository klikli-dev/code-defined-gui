// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.list;

import com.klikli_dev.codedefinedgui.filter.core.FilterItem;
import com.klikli_dev.codedefinedgui.filter.core.layout.BuiltinFilterLayouts;
import com.klikli_dev.codedefinedgui.gui.style.GuiLayoutKey;
import com.klikli_dev.codedefinedgui.gui.style.GuiStyleKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class ListFilterItem extends FilterItem<ListFilterState> {
    public ListFilterItem(Properties properties) {
        super(properties, ListFilterDefinition.INSTANCE);
    }

    public ListFilterItem(Properties properties, GuiStyleKey guiStyleKey) {
        super(properties, ListFilterDefinition.INSTANCE, guiStyleKey);
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory, InteractionHand hand) {
        return new ListFilterMenu(containerId, inventory, hand);
    }

    @Override
    protected GuiLayoutKey defaultLayoutKey() {
        return BuiltinFilterLayouts.LIST_FILTER;
    }
}
