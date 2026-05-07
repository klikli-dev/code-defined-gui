// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui;

import com.klikli_dev.codedefinedgui.datagen.DataGenerators;
import com.klikli_dev.codedefinedgui.premade.filter.attribute.ItemAttributes;
import com.klikli_dev.codedefinedgui.premade.filter.core.registry.FilterDataComponents;
import com.klikli_dev.codedefinedgui.premade.filter.core.registry.FilterMenuTypes;
import com.klikli_dev.codedefinedgui.internal.Config;
import com.klikli_dev.codedefinedgui.internal.registry.ItemRegistry;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(CodeDefinedGui.MODID)
public class CodeDefinedGui {
    public static final String MODID = "codedefinedgui";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CodeDefinedGui(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        ItemRegistry.ITEMS.register(modEventBus);
        FilterMenuTypes.MENU_TYPES.register(modEventBus);
        FilterDataComponents.DATA_COMPONENTS.register(modEventBus);
        modEventBus.addListener(DataGenerators::onGatherData);
    }
}



