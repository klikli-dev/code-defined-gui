// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.attribute;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import java.util.function.IntFunction;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum AttributeFilterMode implements StringRepresentable {
    MATCH_ANY("match_any"),
    MATCH_ALL("match_all"),
    DENY("deny");

    public static final Codec<AttributeFilterMode> CODEC = StringRepresentable.fromEnum(AttributeFilterMode::values);
    private static final IntFunction<AttributeFilterMode> BY_ID = ByIdMap.continuous(AttributeFilterMode::ordinal, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
    public static final StreamCodec<ByteBuf, AttributeFilterMode> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, AttributeFilterMode::ordinal);

    private final String serializedName;

    AttributeFilterMode(String serializedName) {
        this.serializedName = serializedName;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.serializedName;
    }
}
