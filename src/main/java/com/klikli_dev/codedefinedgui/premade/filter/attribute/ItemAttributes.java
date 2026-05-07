// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.premade.filter.attribute;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.resources.Identifier;

public final class ItemAttributes {
    private static final Map<Identifier, ItemAttributeType> TYPES = new LinkedHashMap<>();

    static {
        register(new StandardAttributeType());
        register(new ItemTagAttributeType());
        register(new AddedByAttributeType());
        register(new EnchantmentAttributeType());
        register(new FluidContentsAttributeType());
        register(new ItemNameAttributeType());
    }

    private ItemAttributes() {
    }

    public static void bootstrap() {
    }

    public static <T extends ItemAttributeType> T register(T type) {
        if (TYPES.putIfAbsent(type.id(), type) != null) {
            throw new IllegalArgumentException("Duplicate item attribute id: " + type.id());
        }
        return type;
    }

    public static Collection<ItemAttributeType> all() {
        return TYPES.values();
    }

    public static ItemAttributeType get(Identifier id) {
        return TYPES.get(id);
    }
}

