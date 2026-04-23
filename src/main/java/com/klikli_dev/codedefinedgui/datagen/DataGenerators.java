// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.datagen;

import com.klikli_dev.codedefinedgui.datagen.lang.ENUSLanguageProvider;
import com.klikli_dev.codedefinedgui.datagen.item.CDGItemModelProvider;
import com.klikli_dev.codedefinedgui.datagen.recipe.CDGRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class DataGenerators {

    public static void onGatherData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        event.addProvider(new ENUSLanguageProvider(generator.getPackOutput()));
        event.addProvider(new CDGItemModelProvider(generator.getPackOutput()));
        event.addProvider(new CDGRecipeProvider(generator.getPackOutput()));
    }
}
