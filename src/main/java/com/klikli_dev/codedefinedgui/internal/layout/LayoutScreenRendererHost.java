// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.internal.layout;

import com.klikli_dev.codedefinedgui.api.layout.LayoutScreenView;
import com.klikli_dev.codedefinedgui.api.layout.LayoutSlotView;
import com.klikli_dev.codedefinedgui.api.screen.GuiHost;
import java.util.List;

public interface LayoutScreenRendererHost extends GuiHost, LayoutScreenView {
    List<LayoutSlotView> layoutSlots();
}



