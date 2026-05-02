// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.filter;

import com.klikli_dev.codedefinedgui.filter.core.layout.MenuSlotView;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprite;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprites;
import com.klikli_dev.codedefinedgui.gui.widget.GuiBackgroundWidget;
import com.klikli_dev.codedefinedgui.gui.widget.IconButtonBackgroundSprites;

public interface FilterUiStyle {
    SlotSkinRenderer DEFAULT_SLOT_RENDERER = SlotSkinRenderer.create(GuiSprites.INVENTORY_SLOT, 1, 1);

    default SlotSkinRenderer slotRenderer(MenuSlotView slotView) {
        return DEFAULT_SLOT_RENDERER;
    }

    default IconButtonBackgroundSprites buttonBackgroundSprites() {
        return IconButtonBackgroundSprites.DEFAULT;
    }

    default GuiSprite filterIndicatorOnSprite() {
        return GuiSprites.FILTER_INDICATOR_ON;
    }

    default GuiSprite filterIndicatorOffSprite() {
        return GuiSprites.FILTER_INDICATOR_OFF;
    }

    default GuiSprite playerInventoryBackgroundSprite() {
        return GuiSprites.GUI_BACKGROUND;
    }

    default int listTitleColor() {
        return 0x303030;
    }

    default int attributeTitleColor() {
        return 0x592424;
    }

    default GuiSprite attributeSelectionSprite() {
        return GuiSprites.ATTRIBUTE_FILTER_SELECTION;
    }

    default GuiSprite attributeSummarySprite() {
        return GuiSprites.ATTRIBUTE_FILTER_SUMMARY;
    }

    default void addListBackgroundWidgets(ListFilterScreen<?> screen) {
        screen.addRootChild(new GuiBackgroundWidget(screen, screen.guiX(0), screen.guiY(0), screen.imageWidth(), 99));
    }

    default void addListForegroundWidgets(ListFilterScreen<?> screen) {
    }

    default void addAttributeBackgroundWidgets(AttributeFilterScreen<?> screen) {
        screen.addRootChild(new GuiBackgroundWidget(screen, screen.guiX(0), screen.guiY(0), screen.imageWidth(), 85));
    }

    default void addAttributeForegroundWidgets(AttributeFilterScreen<?> screen) {
    }
}
