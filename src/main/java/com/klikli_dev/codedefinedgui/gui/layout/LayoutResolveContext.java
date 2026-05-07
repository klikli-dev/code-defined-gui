// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.api.layout;

import com.klikli_dev.codedefinedgui.gui.style.StyleContext;
import net.minecraft.client.gui.components.AbstractWidget;

public interface LayoutResolveContext extends LayoutLookup {
    LayoutNodeView node();

    StyleContext style();

    void addWidget(AbstractWidget widget);
}
