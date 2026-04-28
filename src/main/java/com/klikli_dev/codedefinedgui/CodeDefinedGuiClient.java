// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui;

import com.klikli_dev.codedefinedgui.gui.filter.AttributeFilterScreen;
import com.klikli_dev.codedefinedgui.gui.filter.ListFilterScreen;
import com.klikli_dev.codedefinedgui.filter.attribute.AttributeFilterMenu;
import com.klikli_dev.codedefinedgui.filter.list.ListFilterMenu;
import com.klikli_dev.codedefinedgui.registry.MenuTypeRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@Mod(value = CodeDefinedGui.MODID, dist = Dist.CLIENT)
public class CodeDefinedGuiClient {
    public CodeDefinedGuiClient(IEventBus modEventBus, ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);

        modEventBus.addListener(this::onClientSetupEvent);
        modEventBus.addListener(this::onRegisterMenuScreensEvent);
    }

    void onClientSetupEvent(FMLClientSetupEvent event) {
    }

    private void onRegisterMenuScreensEvent(RegisterMenuScreensEvent event) {
        event.register(MenuTypeRegistry.LIST_FILTER.get(), this::createListFilterScreen);
        event.register(MenuTypeRegistry.ATTRIBUTE_FILTER.get(), this::createAttributeFilterScreen);
    }

    private ListFilterScreen<ListFilterMenu> createListFilterScreen(ListFilterMenu menu, Inventory playerInventory, Component title) {
        return new ListFilterScreen<>(menu, playerInventory, title);
    }

    private AttributeFilterScreen<AttributeFilterMenu> createAttributeFilterScreen(AttributeFilterMenu menu, Inventory playerInventory, Component title) {
        return new AttributeFilterScreen<>(menu, playerInventory, title);
    }
}
