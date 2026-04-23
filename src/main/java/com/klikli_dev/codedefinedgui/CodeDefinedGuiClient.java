// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui;

import com.klikli_dev.codedefinedgui.client.ClientPayloadHandlers;
import com.klikli_dev.codedefinedgui.network.OpenTestScreenPayload;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;

@Mod(value = CodeDefinedGui.MODID, dist = Dist.CLIENT)
public class CodeDefinedGuiClient {
    public CodeDefinedGuiClient(IEventBus modEventBus, ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);

        modEventBus.addListener(this::onClientSetupEvent);
        modEventBus.addListener(this::onRegisterClientPayloadHandlersEvent);
    }

    void onClientSetupEvent(FMLClientSetupEvent event) {

    }

    private void onRegisterClientPayloadHandlersEvent(RegisterClientPayloadHandlersEvent event) {
        event.register(OpenTestScreenPayload.TYPE, ClientPayloadHandlers::handleOpenTestScreen);
    }
}
