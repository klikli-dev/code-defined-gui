// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.layout;

import com.klikli_dev.codedefinedgui.gui.style.GuiPartKey;
import net.minecraft.world.inventory.Slot;

public record LayoutSlotView(Slot slot, SlotRoleKey role, GuiPartKey part, String nodePath) {
    public int x() {
        return this.slot.x;
    }

    public int y() {
        return this.slot.y;
    }
}
