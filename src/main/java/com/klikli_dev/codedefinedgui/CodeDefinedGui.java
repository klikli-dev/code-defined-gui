// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui;

import com.klikli_dev.codedefinedgui.command.CdgCommand;
import com.klikli_dev.codedefinedgui.datagen.DataGenerators;
import com.klikli_dev.codedefinedgui.filter.attribute.CdgItemAttributes;
import com.klikli_dev.codedefinedgui.network.Networking;
import com.klikli_dev.codedefinedgui.registry.DataComponentRegistry;
import com.klikli_dev.codedefinedgui.registry.ItemRegistry;
import com.klikli_dev.codedefinedgui.registry.MenuTypeRegistry;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.slf4j.Logger;

@Mod(CodeDefinedGui.MODID)
public class CodeDefinedGui {
    public static final String MODID = "codedefinedgui";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CodeDefinedGui(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        ItemRegistry.ITEMS.register(modEventBus);
        MenuTypeRegistry.MENU_TYPES.register(modEventBus);
        DataComponentRegistry.DATA_COMPONENTS.register(modEventBus);
        CdgItemAttributes.bootstrap();

        modEventBus.addListener(this::onCommonSetupEvent);
        modEventBus.addListener(DataGenerators::onGatherData);
        modEventBus.addListener(Networking::register);

        NeoForge.EVENT_BUS.addListener(this::onRegisterCommandsEvent);
    }

    private void onCommonSetupEvent(FMLCommonSetupEvent event) {

    }

    private void onRegisterCommandsEvent(RegisterCommandsEvent event) {
        CdgCommand.register(event.getDispatcher());
    }
}
