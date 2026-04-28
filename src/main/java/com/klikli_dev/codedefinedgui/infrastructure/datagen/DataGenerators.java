// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.infrastructure.datagen;

import com.klikli_dev.codedefinedgui.infrastructure.datagen.lang.ENUSLanguageProvider;
import com.klikli_dev.codedefinedgui.infrastructure.datagen.model.ModelProvider;
import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class DataGenerators {

    public static void onGatherData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        event.addProvider(new ENUSLanguageProvider(generator.getPackOutput()));
        event.addProvider(new ModelProvider(generator.getPackOutput()));
    }
}
