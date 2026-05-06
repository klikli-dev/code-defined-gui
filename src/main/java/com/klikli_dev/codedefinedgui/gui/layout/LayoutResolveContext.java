// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.layout;

import net.minecraft.client.gui.components.AbstractWidget;

public interface LayoutResolveContext extends LayoutLookup {
    LayoutNodeView node();

    void addWidget(AbstractWidget widget);
}
