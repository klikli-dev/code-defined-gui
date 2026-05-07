// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.layout;

import com.klikli_dev.codedefinedgui.api.layout.LayoutSlotView;

import com.klikli_dev.codedefinedgui.gui.core.GuiHost;
import java.util.List;

public interface LayoutScreenRendererHost extends GuiHost, LayoutScreen {
    List<LayoutSlotView> layoutSlots();
}
