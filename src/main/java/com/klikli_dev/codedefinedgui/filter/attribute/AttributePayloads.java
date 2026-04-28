// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.attribute;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.component.CustomData;

final class AttributePayloads {
    private AttributePayloads() {
    }

    static CustomData ofString(String key, String value) {
        CompoundTag tag = new CompoundTag();
        tag.putString(key, value);
        return CustomData.of(tag);
    }

    static String getString(CustomData data, String key) {
        return data.copyTag().getString(key).orElse("");
    }
}
