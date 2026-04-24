// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.datagen.item;

import com.google.gson.JsonObject;
import com.klikli_dev.codedefinedgui.registry.ItemRegistry;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

public class CDGItemModelProvider implements DataProvider {
    private final PackOutput.PathProvider itemInfoPathProvider;
    private final PackOutput.PathProvider modelPathProvider;

    public CDGItemModelProvider(PackOutput output) {
        this.itemInfoPathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "items");
        this.modelPathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models/item");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        Map<Identifier, String> textures = new LinkedHashMap<>();
        textures.put(ItemRegistry.LIST_FILTER.getId(), "codedefinedgui:item/list_filter");
        textures.put(ItemRegistry.ATTRIBUTE_FILTER.getId(), "codedefinedgui:item/attribute_filter");

        CompletableFuture<?>[] futures = textures.entrySet().stream()
                .flatMap(entry -> java.util.stream.Stream.of(
                        DataProvider.saveStable(output, this.model(entry.getValue()), this.modelPathProvider.json(entry.getKey())),
                        DataProvider.saveStable(output, this.clientItem(entry.getKey()), this.itemInfoPathProvider.json(entry.getKey()))
                ))
                .toArray(CompletableFuture[]::new);
        return CompletableFuture.allOf(futures);
    }

    @Override
    public String getName() {
        return "Code Defined GUI Item Models";
    }

    private JsonObject model(String layer0) {
        JsonObject root = new JsonObject();
        root.addProperty("parent", "minecraft:item/generated");
        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", layer0);
        root.add("textures", textures);
        return root;
    }

    private JsonObject clientItem(Identifier modelId) {
        JsonObject root = new JsonObject();
        JsonObject model = new JsonObject();
        model.addProperty("type", "minecraft:model");
        model.addProperty("model", modelId.withPrefix("item/").toString());
        root.add("model", model);
        return root;
    }
}
