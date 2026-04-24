// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.datagen.model;

import com.klikli_dev.codedefinedgui.registry.ItemRegistry;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;

public class ItemModelSubProvider {
    public void registerModels(ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ItemRegistry.LIST_FILTER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ATTRIBUTE_FILTER.get(), ModelTemplates.FLAT_ITEM);
    }
}
