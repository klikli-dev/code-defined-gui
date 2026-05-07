// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.api.layout;

import java.util.Map;

public final class ResolvedLayout {
    private final Map<String, ResolvedLayoutNode> nodes;

    ResolvedLayout(Map<String, ResolvedLayoutNode> nodes) {
        this.nodes = Map.copyOf(nodes);
    }

    public ResolvedLayoutNode node(String path) {
        ResolvedLayoutNode node = this.nodes.get(path);
        if (node == null) {
            throw new IllegalArgumentException("Unknown layout node: " + path);
        }

        return node;
    }
}
