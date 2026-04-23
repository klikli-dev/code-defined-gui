// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.list;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import java.util.function.IntFunction;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum ListFilterMode implements StringRepresentable {
    ALLOW("allow"),
    DENY("deny");

    public static final Codec<ListFilterMode> CODEC = StringRepresentable.fromEnum(ListFilterMode::values);
    private static final IntFunction<ListFilterMode> BY_ID = ByIdMap.continuous(ListFilterMode::ordinal, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
    public static final StreamCodec<ByteBuf, ListFilterMode> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, ListFilterMode::ordinal);

    private final String serializedName;

    ListFilterMode(String serializedName) {
        this.serializedName = serializedName;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.serializedName;
    }
}
