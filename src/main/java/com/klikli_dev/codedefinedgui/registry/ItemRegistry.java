// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.registry;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import com.klikli_dev.codedefinedgui.filter.attribute.AttributeFilterItem;
import com.klikli_dev.codedefinedgui.filter.list.ListFilterItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CodeDefinedGui.MODID);

    public static final DeferredItem<ListFilterItem> LIST_FILTER = ITEMS.registerItem("list_filter", p -> new ListFilterItem(p.stacksTo(1)));
    public static final DeferredItem<AttributeFilterItem> ATTRIBUTE_FILTER = ITEMS.registerItem("attribute_filter", p -> new AttributeFilterItem(p.stacksTo(1)));
}
