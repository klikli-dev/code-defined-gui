// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.Level;

public record FilterMatchContext(Level level) {
    public HolderLookup.Provider registries() {
        return this.level.registryAccess();
    }
}
