// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.filter;

import com.klikli_dev.codedefinedgui.filter.core.layout.MenuSlotView;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprite;
import com.klikli_dev.codedefinedgui.gui.core.GuiHost;

public interface SlotSkinRenderer {
    GuiSprite sprite(MenuSlotView slotView);

    default int offsetX(MenuSlotView slotView) {
        return 0;
    }

    default int offsetY(MenuSlotView slotView) {
        return 0;
    }

    default int renderX(GuiHost host, MenuSlotView slotView) {
        return host.leftPos() + slotView.x() - this.offsetX(slotView);
    }

    default int renderY(GuiHost host, MenuSlotView slotView) {
        return host.topPos() + slotView.y() - this.offsetY(slotView);
    }

    static SlotSkinRenderer create(GuiSprite sprite, int offsetX, int offsetY) {
        return new SlotSkinRenderer() {
            @Override
            public GuiSprite sprite(MenuSlotView slotView) {
                return sprite;
            }

            @Override
            public int offsetX(MenuSlotView slotView) {
                return offsetX;
            }

            @Override
            public int offsetY(MenuSlotView slotView) {
                return offsetY;
            }
        };
    }
}
