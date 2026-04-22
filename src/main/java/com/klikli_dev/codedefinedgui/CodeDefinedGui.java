// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui;

import com.klikli_dev.codedefinedgui.datagen.DataGenerators;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod(CodeDefinedGui.MODID)
public class CodeDefinedGui {
    public static final String MODID = "codedefinedgui";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CodeDefinedGui(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        modEventBus.addListener(this::onCommonSetupEvent);
        modEventBus.addListener(DataGenerators::onGatherData);
    }

    private void onCommonSetupEvent(FMLCommonSetupEvent event) {

    }
}
