// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.example;

import com.klikli_dev.codedefinedgui.api.layout.LayoutSpec;

public final class ExampleLayouts {
    public static final LayoutSpec BASIC_SCREEN = LayoutSpec.create(root -> {
        root.group("main", main -> {
            main.node("background").at(0, 0).size(176, 166);
            main.node("title").at(8, 6).size(160, 8);
            main.node("input").at(26, 40).size(18, 18);
            main.node("output").at(132, 40).size(18, 18);
            main.node("hint").at(8, 88).size(160, 8);
        });
    });

    private ExampleLayouts() {
    }
}
