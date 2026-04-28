// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.attribute;

import java.util.List;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

public interface ItemAttributeType {
    Identifier id();

    List<AttributeCandidate> collectCandidates(ItemStack reference, Level level);

    boolean matches(ItemStack candidate, Level level, CustomData payload);

    Component describe(CustomData payload, HolderLookup.Provider registries, boolean inverted);
}
