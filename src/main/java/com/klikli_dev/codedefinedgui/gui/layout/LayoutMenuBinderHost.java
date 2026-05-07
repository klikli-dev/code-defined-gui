// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.internal.layout;

import com.klikli_dev.codedefinedgui.api.style.GuiPartKey;
import com.klikli_dev.codedefinedgui.api.layout.SlotRoleKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public interface LayoutMenuBinderHost {
    Inventory playerInventory();

    Slot addLayoutSlot(Slot slot, SlotRoleKey role, GuiPartKey part, String nodePath);
}



