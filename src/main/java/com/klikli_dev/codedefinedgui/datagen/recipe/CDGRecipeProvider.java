// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.datagen.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.klikli_dev.codedefinedgui.registry.CDGItems;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

public class CDGRecipeProvider implements DataProvider {
    private final PackOutput.PathProvider pathProvider;

    public CDGRecipeProvider(PackOutput output) {
        this.pathProvider = output.createRegistryElementsPathProvider(net.minecraft.core.registries.Registries.RECIPE);
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        Map<Identifier, JsonObject> recipes = new LinkedHashMap<>();
        recipes.put(CDGItems.LIST_FILTER.getId(), this.shaped("codedefinedgui:list_filter", new String[][]{{"ppp", "pmp", "ppp"}}, Map.of('p', "minecraft:paper", 'm', "minecraft:iron_ingot")));
        recipes.put(CDGItems.ATTRIBUTE_FILTER.getId(), this.shaped("codedefinedgui:attribute_filter", new String[][]{{"pfp", "pmp", "ppp"}}, Map.of('p', "minecraft:paper", 'm', "minecraft:iron_ingot", 'f', "minecraft:feather")));

        CompletableFuture<?>[] futures = recipes.entrySet().stream()
                .map(entry -> DataProvider.saveStable(output, entry.getValue(), this.pathProvider.json(entry.getKey())))
                .toArray(CompletableFuture[]::new);
        return CompletableFuture.allOf(futures);
    }

    @Override
    public String getName() {
        return "Code Defined GUI Recipes";
    }

    private JsonObject shaped(String resultId, String[][] patternRows, Map<Character, String> ingredients) {
        JsonObject root = new JsonObject();
        root.addProperty("type", "minecraft:crafting_shaped");
        root.addProperty("category", "misc");

        JsonObject key = new JsonObject();
        ingredients.forEach((symbol, itemId) -> {
            JsonObject ingredient = new JsonObject();
            ingredient.addProperty("item", itemId);
            key.add(String.valueOf(symbol), ingredient);
        });
        root.add("key", key);

        JsonArray pattern = new JsonArray();
        for (String[] rows : patternRows) {
            for (String row : rows) {
                pattern.add(row);
            }
        }
        root.add("pattern", pattern);

        JsonObject result = new JsonObject();
        result.addProperty("id", resultId);
        result.addProperty("count", 1);
        root.add("result", result);
        return root;
    }
}
