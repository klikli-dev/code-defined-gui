// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.layout;

public interface LayoutFragment {
    void define(LayoutGroupBuilder root);

    void bindMenu(MenuBindingRegistry registry);

    ClientLayoutFragment client();
}
