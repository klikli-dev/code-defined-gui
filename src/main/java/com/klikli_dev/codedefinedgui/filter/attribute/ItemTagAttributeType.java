// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.attribute;

import com.klikli_dev.codedefinedgui.infrastructure.CodeDefinedGui;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

public class ItemTagAttributeType implements ItemAttributeType {
    private static final Identifier ID = Identifier.fromNamespaceAndPath(CodeDefinedGui.MODID, "in_tag");

    @Override
    public Identifier id() {
        return ID;
    }

    @Override
    public List<AttributeCandidate> collectCandidates(ItemStack reference, Level level) {
        return BuiltInRegistries.ITEM.wrapAsHolder(reference.getItem()).tags()
                .map(tag -> {
                    CustomData payload = AttributePayloads.ofString("id", tag.location().toString());
                    return new AttributeCandidate(new AttributeRule(this.id(), payload, false), this.describe(payload, level.registryAccess(), false));
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean matches(ItemStack candidate, Level level, CustomData payload) {
        Identifier id = Identifier.tryParse(AttributePayloads.getString(payload, "id"));
        return id != null && candidate.is(ItemTags.create(id));
    }

    @Override
    public Component describe(CustomData payload, HolderLookup.Provider registries, boolean inverted) {
        Component base = Component.translatable("codedefinedgui.filter.attribute.in_tag", "#" + AttributePayloads.getString(payload, "id"));
        return inverted ? Component.translatable("codedefinedgui.filter.attribute.inverted", base) : base;
    }
}
