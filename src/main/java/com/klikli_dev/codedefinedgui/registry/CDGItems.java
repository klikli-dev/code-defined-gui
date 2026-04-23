// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.registry;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import com.klikli_dev.codedefinedgui.filter.attribute.AttributeFilterItem;
import com.klikli_dev.codedefinedgui.filter.list.ListFilterItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class CDGItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CodeDefinedGui.MODID);

    public static final DeferredHolder<Item, ListFilterItem> LIST_FILTER = ITEMS.register("list_filter", () -> new ListFilterItem(new Item.Properties().stacksTo(1)));
    public static final DeferredHolder<Item, AttributeFilterItem> ATTRIBUTE_FILTER = ITEMS.register("attribute_filter", () -> new AttributeFilterItem(new Item.Properties().stacksTo(1)));

    private CDGItems() {
    }
}
