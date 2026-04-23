// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.registry;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import com.klikli_dev.codedefinedgui.filter.attribute.AttributeFilterState;
import com.klikli_dev.codedefinedgui.filter.list.ListFilterState;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class CDGDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, CodeDefinedGui.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ListFilterState>> LIST_FILTER_STATE = DATA_COMPONENTS.registerComponentType(
            "list_filter_state",
            builder -> builder.persistent(ListFilterState.CODEC).networkSynchronized(ListFilterState.STREAM_CODEC)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<AttributeFilterState>> ATTRIBUTE_FILTER_STATE = DATA_COMPONENTS.registerComponentType(
            "attribute_filter_state",
            builder -> builder.persistent(AttributeFilterState.CODEC).networkSynchronized(AttributeFilterState.STREAM_CODEC)
    );

    private CDGDataComponents() {
    }
}
