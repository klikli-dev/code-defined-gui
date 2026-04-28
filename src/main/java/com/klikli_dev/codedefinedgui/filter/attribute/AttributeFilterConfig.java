// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.attribute;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record AttributeFilterConfig(AttributeFilterMode mode, List<AttributeRule> rules) {
    public static final AttributeFilterConfig EMPTY = new AttributeFilterConfig(AttributeFilterMode.MATCH_ANY, List.of());

    public static final Codec<AttributeFilterConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            AttributeFilterMode.CODEC.optionalFieldOf("mode", AttributeFilterMode.MATCH_ANY).forGetter(AttributeFilterConfig::mode),
            AttributeRule.CODEC.listOf().optionalFieldOf("rules", List.of()).forGetter(AttributeFilterConfig::rules)
    ).apply(instance, AttributeFilterConfig::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, AttributeFilterConfig> STREAM_CODEC = StreamCodec.composite(
            AttributeFilterMode.STREAM_CODEC,
            AttributeFilterConfig::mode,
            AttributeRule.STREAM_CODEC.apply(net.minecraft.network.codec.ByteBufCodecs.list(256)),
            AttributeFilterConfig::rules,
            AttributeFilterConfig::new
    );
}
