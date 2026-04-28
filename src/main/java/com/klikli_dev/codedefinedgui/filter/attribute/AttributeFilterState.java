// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.attribute;

import com.klikli_dev.codedefinedgui.filter.core.FilterState;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public record AttributeFilterState(ItemStack referenceStack, AttributeFilterMode mode, List<AttributeRule> rules) implements FilterState {
    public static final AttributeFilterState EMPTY = new AttributeFilterState(ItemStack.EMPTY, AttributeFilterMode.MATCH_ANY, List.of());

    public static final Codec<AttributeFilterState> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemStack.CODEC.optionalFieldOf("reference", ItemStack.EMPTY).forGetter(AttributeFilterState::referenceStack),
            AttributeFilterMode.CODEC.optionalFieldOf("mode", AttributeFilterMode.MATCH_ANY).forGetter(AttributeFilterState::mode),
            AttributeRule.CODEC.listOf().optionalFieldOf("rules", List.of()).forGetter(AttributeFilterState::rules)
    ).apply(instance, AttributeFilterState::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, AttributeFilterState> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC,
            AttributeFilterState::referenceStack,
            AttributeFilterMode.STREAM_CODEC,
            AttributeFilterState::mode,
            AttributeRule.STREAM_CODEC.apply(ByteBufCodecs.list(256)),
            AttributeFilterState::rules,
            AttributeFilterState::new
    );
}
