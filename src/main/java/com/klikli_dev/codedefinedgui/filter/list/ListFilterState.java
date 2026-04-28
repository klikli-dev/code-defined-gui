// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.list;

import com.klikli_dev.codedefinedgui.filter.core.FilterState;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.component.ItemContainerContents;

public record ListFilterState(ItemContainerContents entries, ListFilterMode mode, boolean respectDataComponents) implements FilterState {
    public static final ListFilterState EMPTY = new ListFilterState(ItemContainerContents.EMPTY, ListFilterMode.ALLOW, false);

    public static final Codec<ListFilterState> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemContainerContents.CODEC.optionalFieldOf("entries", ItemContainerContents.EMPTY).forGetter(ListFilterState::entries),
            ListFilterMode.CODEC.optionalFieldOf("mode", ListFilterMode.ALLOW).forGetter(ListFilterState::mode),
            Codec.BOOL.optionalFieldOf("respect_data_components", false).forGetter(ListFilterState::respectDataComponents)
    ).apply(instance, ListFilterState::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ListFilterState> STREAM_CODEC = StreamCodec.composite(
            ItemContainerContents.STREAM_CODEC,
            ListFilterState::entries,
            ListFilterMode.STREAM_CODEC,
            ListFilterState::mode,
            ByteBufCodecs.BOOL,
            ListFilterState::respectDataComponents,
            ListFilterState::new
    );
}
