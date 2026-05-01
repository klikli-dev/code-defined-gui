// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.attribute;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;

public class EnchantmentAttributeType implements ItemAttributeType {
    private static final Identifier ID = Identifier.fromNamespaceAndPath(CodeDefinedGui.MODID, "has_enchantment");

    @Override
    public Identifier id() {
        return ID;
    }

    @Override
    public List<AttributeCandidate> collectCandidates(ItemStack reference, Level level) {
        List<AttributeCandidate> candidates = new ArrayList<>();
        for (Holder<Enchantment> enchantment : reference.getTagEnchantments().keySet()) {
            Identifier id = enchantment.unwrapKey().map(ResourceKey::identifier).orElse(null);
            if (id == null) {
                continue;
            }

            CustomData payload = AttributePayloads.ofString("id", id.toString());
            candidates.add(new AttributeCandidate(new AttributeRule(this.id(), payload, false), this.describe(payload, level.registryAccess(), false)));
        }

        return candidates;
    }

    @Override
    public boolean matches(ItemStack candidate, Level level, CustomData payload) {
        Identifier id = Identifier.tryParse(AttributePayloads.getString(payload, "id"));
        if (id == null) {
            return false;
        }

        for (Holder<Enchantment> enchantment : candidate.getAllEnchantments(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT)).keySet()) {
            if (enchantment.unwrapKey().map(ResourceKey::identifier).filter(id::equals).isPresent()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Component describe(CustomData payload, HolderLookup.Provider registries, boolean inverted) {
        Identifier id = Identifier.tryParse(AttributePayloads.getString(payload, "id"));
        Component enchantmentName = id == null
                ? Component.literal(AttributePayloads.getString(payload, "id"))
                : registries.lookupOrThrow(Registries.ENCHANTMENT)
                .get(ResourceKey.create(Registries.ENCHANTMENT, id))
                .map(holder -> holder.value().description())
                .orElse(Component.literal(id.toString()));
        Component base = Component.translatable("codedefinedgui.filter.attribute.has_enchantment", enchantmentName);
        return inverted ? Component.translatable("codedefinedgui.filter.attribute.inverted", base) : base;
    }
}
