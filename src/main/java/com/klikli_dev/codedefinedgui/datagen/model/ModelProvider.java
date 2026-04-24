// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.datagen.model;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.data.PackOutput;
import org.jspecify.annotations.NonNull;

public class ModelProvider extends net.minecraft.client.data.models.ModelProvider {
    private final ItemModelSubProvider itemModels = new ItemModelSubProvider();

    public ModelProvider(PackOutput packOutput) {
        super(packOutput, CodeDefinedGui.MODID);
    }

    @Override
    public @NonNull String getName() {
        return "Model Definitions - " + CodeDefinedGui.MODID;
    }

    @Override
    protected void registerModels(@NonNull BlockModelGenerators blockModels, @NonNull ItemModelGenerators itemModels) {
        this.itemModels.registerModels(itemModels);
    }
}
