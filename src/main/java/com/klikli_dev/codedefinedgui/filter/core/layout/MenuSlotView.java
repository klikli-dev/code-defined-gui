// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.core.layout;

import com.klikli_dev.codedefinedgui.gui.style.GuiPartKey;
import net.minecraft.world.inventory.Slot;

public record MenuSlotView(Slot slot, SlotRoleKey role, GuiPartKey part) {
    public int x() {
        return this.slot.x;
    }

    public int y() {
        return this.slot.y;
    }
}
