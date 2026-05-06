// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.layout;

import java.util.ArrayList;
import java.util.List;
import org.jspecify.annotations.Nullable;

final class LayoutNodeSpec {
    final String id;
    final @Nullable LayoutNodeSpec parent;
    final boolean group;
    final List<LayoutNodeSpec> children = new ArrayList<>();
    int x;
    int y;
    @Nullable Integer width;
    @Nullable Integer height;

    LayoutNodeSpec(String id, @Nullable LayoutNodeSpec parent, boolean group) {
        this.id = id;
        this.parent = parent;
        this.group = group;
    }

    String path() {
        if (this.parent == null || this.parent.path().isEmpty()) {
            return this.id;
        }

        return this.parent.path() + "." + this.id;
    }
}
