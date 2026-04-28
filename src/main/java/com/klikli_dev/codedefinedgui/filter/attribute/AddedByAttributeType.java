// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.attribute;

import com.klikli_dev.codedefinedgui.infrastructure.CodeDefinedGui;
import java.util.List;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.neoforgespi.language.IModInfo;
import org.apache.commons.lang3.StringUtils;

public class AddedByAttributeType implements ItemAttributeType {
    private static final Identifier ID = Identifier.fromNamespaceAndPath(CodeDefinedGui.MODID, "added_by");

    @Override
    public Identifier id() {
        return ID;
    }

    @Override
    public List<AttributeCandidate> collectCandidates(ItemStack reference, Level level) {
        String namespace = BuiltInRegistries.ITEM.getKey(reference.getItem()).getNamespace();
        if (namespace == null || namespace.isBlank()) {
            return List.of();
        }

        CustomData payload = AttributePayloads.ofString("id", namespace);
        return List.of(new AttributeCandidate(new AttributeRule(this.id(), payload, false), this.describe(payload, level.registryAccess(), false)));
    }

    @Override
    public boolean matches(ItemStack candidate, Level level, CustomData payload) {
        return AttributePayloads.getString(payload, "id").equals(BuiltInRegistries.ITEM.getKey(candidate.getItem()).getNamespace());
    }

    @Override
    public Component describe(CustomData payload, HolderLookup.Provider registries, boolean inverted) {
        String modId = AttributePayloads.getString(payload, "id");
        String displayName = ModList.get().getModContainerById(modId)
                .map(ModContainer::getModInfo)
                .map(IModInfo::getDisplayName)
                .orElse(StringUtils.capitalize(modId));
        Component base = Component.translatable("codedefinedgui.filter.attribute.added_by", displayName);
        return inverted ? Component.translatable("codedefinedgui.filter.attribute.inverted", base) : base;
    }
}
