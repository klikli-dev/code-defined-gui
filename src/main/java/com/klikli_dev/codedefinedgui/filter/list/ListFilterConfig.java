// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.list;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record ListFilterConfig(ListFilterMode mode, boolean respectDataComponents) {
    public static final ListFilterConfig EMPTY = new ListFilterConfig(ListFilterMode.ALLOW, false);

    public static final Codec<ListFilterConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ListFilterMode.CODEC.optionalFieldOf("mode", ListFilterMode.ALLOW).forGetter(ListFilterConfig::mode),
            Codec.BOOL.optionalFieldOf("respect_data_components", false).forGetter(ListFilterConfig::respectDataComponents)
    ).apply(instance, ListFilterConfig::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ListFilterConfig> STREAM_CODEC = StreamCodec.composite(
            ListFilterMode.STREAM_CODEC,
            ListFilterConfig::mode,
            ByteBufCodecs.BOOL,
            ListFilterConfig::respectDataComponents,
            ListFilterConfig::new
    );
}
