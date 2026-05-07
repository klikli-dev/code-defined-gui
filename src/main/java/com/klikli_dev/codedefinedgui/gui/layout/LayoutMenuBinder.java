// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.internal.layout;

import com.klikli_dev.codedefinedgui.api.layout.MenuBindingRegistry;

public interface LayoutMenuBinder {
    void bind(MenuBindingRegistry registry, LayoutMenuBinderHost host);
}
