// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.layout;

import com.klikli_dev.codedefinedgui.gui.core.GuiHost;
import com.klikli_dev.codedefinedgui.gui.style.GuiPartKey;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprite;
import java.util.List;

public interface LayoutScreenRendererHost extends GuiHost, LayoutScreen {
    List<LayoutSlotView> layoutSlots();

    GuiSprite resolvedPartSprite(GuiPartKey part, GuiSprite fallback);

    GuiSprite resolvedSlotSprite(LayoutSlotView slotView);
}
