// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.registry;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class CreativeModeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CodeDefinedGui.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CODE_DEFINED_GUI = CREATIVE_MODE_TABS.register(CodeDefinedGui.MODID, () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> CDGItems.LIST_FILTER.get().getDefaultInstance())
            .title(Component.translatable("itemGroup." + CodeDefinedGui.MODID))
            .displayItems((parameters, output) -> {
                output.accept(CDGItems.LIST_FILTER.get());
                output.accept(CDGItems.ATTRIBUTE_FILTER.get());
            })
            .build());

    private CreativeModeTabRegistry() {
    }
}
