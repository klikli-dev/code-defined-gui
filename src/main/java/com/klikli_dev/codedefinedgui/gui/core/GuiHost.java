// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui;

import net.minecraft.client.gui.components.AbstractWidget;

public interface GuiHost {
    int leftPos();

    int topPos();

    int width();

    int height();

    int imageWidth();

    int imageHeight();

    <T extends AbstractWidget> T addGuiWidget(T widget);

    void removeGuiWidget(AbstractWidget widget);
}
