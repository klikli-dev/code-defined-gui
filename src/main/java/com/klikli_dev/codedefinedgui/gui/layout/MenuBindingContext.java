// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.layout;

import net.minecraft.world.inventory.Slot;

public interface MenuBindingContext extends LayoutLookup {
    LayoutNodeView node();

    <T extends Slot> T addSlot(T slot);
}
