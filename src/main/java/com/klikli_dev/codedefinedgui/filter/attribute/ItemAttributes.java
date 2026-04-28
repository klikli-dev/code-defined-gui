// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.attribute;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
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
        TYPES.put(type.id(), type);
        return type;
    }

    public static Collection<ItemAttributeType> all() {
        return TYPES.values();
    }

    public static Optional<ItemAttributeType> get(Identifier id) {
        return Optional.ofNullable(TYPES.get(id));
    }
}
