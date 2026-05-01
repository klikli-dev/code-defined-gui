// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.attribute;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import java.util.List;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

public class ItemNameAttributeType implements ItemAttributeType {
    private static final Identifier ID = Identifier.fromNamespaceAndPath(CodeDefinedGui.MODID, "has_name");

    @Override
    public Identifier id() {
        return ID;
    }

    @Override
    public List<AttributeCandidate> collectCandidates(ItemStack reference, Level level) {
        String name = this.extractCustomName(reference);
        if (name.isBlank()) {
            return List.of();
        }

        CustomData payload = AttributePayloads.ofString("value", name);
        return List.of(new AttributeCandidate(new AttributeRule(this.id(), payload, false), this.describe(payload, level.registryAccess(), false)));
    }

    @Override
    public boolean matches(ItemStack candidate, Level level, CustomData payload) {
        return AttributePayloads.getString(payload, "value").equals(this.extractCustomName(candidate));
    }

    @Override
    public Component describe(CustomData payload, HolderLookup.Provider registries, boolean inverted) {
        Component base = Component.translatable("codedefinedgui.filter.attribute.has_name", AttributePayloads.getString(payload, "value"));
        return inverted ? Component.translatable("codedefinedgui.filter.attribute.inverted", base) : base;
    }

    private String extractCustomName(ItemStack stack) {
        Component name = stack.get(DataComponents.CUSTOM_NAME);
        return name == null ? "" : name.getString();
    }
}
