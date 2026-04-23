// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.attribute;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.component.CustomData;

public record AttributeRule(Identifier typeId, CustomData payload, boolean inverted) {
    public static final Codec<AttributeRule> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("type").forGetter(AttributeRule::typeId),
            CustomData.CODEC.optionalFieldOf("payload", CustomData.EMPTY).forGetter(AttributeRule::payload),
            Codec.BOOL.optionalFieldOf("inverted", false).forGetter(AttributeRule::inverted)
    ).apply(instance, AttributeRule::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, AttributeRule> STREAM_CODEC = StreamCodec.composite(
            Identifier.STREAM_CODEC,
            AttributeRule::typeId,
            CustomData.STREAM_CODEC,
            AttributeRule::payload,
            ByteBufCodecs.BOOL,
            AttributeRule::inverted,
            AttributeRule::new
    );
}
