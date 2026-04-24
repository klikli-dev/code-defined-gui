// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.datagen.model;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.data.PackOutput;

public class CDGModelProvider extends ModelProvider {
    private final CDGItemModelSubProvider itemModels = new CDGItemModelSubProvider();

    public CDGModelProvider(PackOutput packOutput) {
        super(packOutput, CodeDefinedGui.MODID);
    }

    @Override
    public String getName() {
        return "Model Definitions - " + CodeDefinedGui.MODID;
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        this.itemModels.registerModels(itemModels);
    }
}
