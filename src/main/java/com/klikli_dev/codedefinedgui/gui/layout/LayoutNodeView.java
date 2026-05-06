// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.layout;

import org.jspecify.annotations.Nullable;

public interface LayoutNodeView {
    String id();

    String path();

    int x();

    int y();

    @Nullable Integer width();

    @Nullable Integer height();

    default int widthOrThrow() {
        Integer width = this.width();
        if (width == null) {
            throw new IllegalStateException("Layout node '" + this.path() + "' has no width");
        }

        return width;
    }

    default int heightOrThrow() {
        Integer height = this.height();
        if (height == null) {
            throw new IllegalStateException("Layout node '" + this.path() + "' has no height");
        }

        return height;
    }
}
