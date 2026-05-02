// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.core;

import net.minecraft.client.gui.components.AbstractWidget;

public interface GuiHost {
    int leftPos();

    int topPos();

    default int guiX(int x) {
        return this.leftPos() + x;
    }

    default int guiY(int y) {
        return this.topPos() + y;
    }

    int width();

    int height();

    int imageWidth();

    int imageHeight();

    <T extends AbstractWidget> T addGuiWidget(T widget);

    void removeGuiWidget(AbstractWidget widget);
}
