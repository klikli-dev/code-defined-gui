// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.api.layout;

import com.klikli_dev.codedefinedgui.api.layout.LayoutNodeSpec;

public class LayoutNodeBuilder implements LayoutNodeView {
    protected final LayoutNodeSpec spec;

    LayoutNodeBuilder(LayoutNodeSpec spec) {
        this.spec = spec;
    }

    public LayoutNodeBuilder at(int x, int y) {
        this.spec.x = x;
        this.spec.y = y;
        return this;
    }

    public LayoutNodeBuilder size(int width, int height) {
        this.spec.width = width;
        this.spec.height = height;
        return this;
    }

    @Override
    public String id() {
        return this.spec.id;
    }

    @Override
    public String path() {
        return this.spec.path();
    }

    @Override
    public int x() {
        return this.spec.x;
    }

    @Override
    public int y() {
        return this.spec.y;
    }

    @Override
    public Integer width() {
        return this.spec.width;
    }

    @Override
    public Integer height() {
        return this.spec.height;
    }
}
