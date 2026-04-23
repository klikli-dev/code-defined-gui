// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.registry;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import com.klikli_dev.codedefinedgui.filter.attribute.AttributeFilterConfig;
import com.klikli_dev.codedefinedgui.filter.list.ListFilterConfig;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class CDGDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, CodeDefinedGui.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemContainerContents>> LIST_FILTER_CONTENTS = DATA_COMPONENTS.registerComponentType(
            "list_filter_contents",
            builder -> builder.persistent(ItemContainerContents.CODEC).networkSynchronized(ItemContainerContents.STREAM_CODEC)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ListFilterConfig>> LIST_FILTER_CONFIG = DATA_COMPONENTS.registerComponentType(
            "list_filter_config",
            builder -> builder.persistent(ListFilterConfig.CODEC).networkSynchronized(ListFilterConfig.STREAM_CODEC)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemContainerContents>> ATTRIBUTE_FILTER_REFERENCE = DATA_COMPONENTS.registerComponentType(
            "attribute_filter_reference",
            builder -> builder.persistent(ItemContainerContents.CODEC).networkSynchronized(ItemContainerContents.STREAM_CODEC)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<AttributeFilterConfig>> ATTRIBUTE_FILTER_CONFIG = DATA_COMPONENTS.registerComponentType(
            "attribute_filter_config",
            builder -> builder.persistent(AttributeFilterConfig.CODEC).networkSynchronized(AttributeFilterConfig.STREAM_CODEC)
    );

    private CDGDataComponents() {
    }
}
