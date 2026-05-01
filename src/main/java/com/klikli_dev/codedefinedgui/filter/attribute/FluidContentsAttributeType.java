// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.attribute;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.fluid.FluidUtil;

public class FluidContentsAttributeType implements ItemAttributeType {
    private static final Identifier ID = Identifier.fromNamespaceAndPath(CodeDefinedGui.MODID, "has_fluid");

    @Override
    public Identifier id() {
        return ID;
    }

    @Override
    public List<AttributeCandidate> collectCandidates(ItemStack reference, Level level) {
        List<AttributeCandidate> candidates = new ArrayList<>();
        for (Fluid fluid : this.extractFluids(reference)) {
            Identifier id = BuiltInRegistries.FLUID.getKey(fluid);
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

        for (Fluid fluid : this.extractFluids(candidate)) {
            if (id.equals(BuiltInRegistries.FLUID.getKey(fluid))) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Component describe(CustomData payload, HolderLookup.Provider registries, boolean inverted) {
        Identifier id = Identifier.tryParse(AttributePayloads.getString(payload, "id"));
        Fluid fluid = id == null ? Fluids.EMPTY : BuiltInRegistries.FLUID.getOptional(id).orElse(Fluids.EMPTY);
        Component base = Component.translatable("codedefinedgui.filter.attribute.has_fluid", fluid.getFluidType().getDescription());
        return inverted ? Component.translatable("codedefinedgui.filter.attribute.inverted", base) : base;
    }

    private Set<Fluid> extractFluids(ItemStack stack) {
        Set<Fluid> fluids = new LinkedHashSet<>();
        var capability = ItemAccess.forStack(stack).oneByOne().getCapability(Capabilities.Fluid.ITEM);
        if (capability != null) {
            for (int slot = 0; slot < capability.size(); slot++) {
                fluids.add(FluidUtil.getStack(capability, slot).getFluid());
            }
        }

        return fluids;
    }
}
