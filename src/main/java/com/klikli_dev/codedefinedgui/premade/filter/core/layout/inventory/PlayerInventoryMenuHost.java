// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.premade.filter.core.layout.inventory;

import com.klikli_dev.codedefinedgui.api.layout.SlotRoleKey;
import com.klikli_dev.codedefinedgui.api.style.GuiPartKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public interface PlayerInventoryMenuHost {
    Inventory playerInventory();

    Slot addLayoutSlot(Slot slot, SlotRoleKey role, GuiPartKey part, String nodePath);
}
