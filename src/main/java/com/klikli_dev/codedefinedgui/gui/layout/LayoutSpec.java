// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.api.layout;

import com.klikli_dev.codedefinedgui.api.layout.LayoutNodeSpec;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class LayoutSpec {
    private final LayoutNodeSpec root;

    private LayoutSpec(LayoutNodeSpec root) {
        this.root = root;
    }

    public static LayoutSpec create(Consumer<LayoutGroupBuilder> builder) {
        LayoutNodeSpec root = new LayoutNodeSpec("", null, true);
        builder.accept(new LayoutGroupBuilder(root));
        return new LayoutSpec(root);
    }

    public ResolvedLayout resolve() {
        return resolve(this.root);
    }

    static ResolvedLayout resolve(LayoutNodeSpec root) {
        Map<String, ResolvedLayoutNode> nodes = new LinkedHashMap<>();
        resolveNode(root, "", 0, 0, nodes);
        return new ResolvedLayout(nodes);
    }

    private static void resolveNode(LayoutNodeSpec spec, String parentPath, int parentX, int parentY, Map<String, ResolvedLayoutNode> nodes) {
        String path = spec.id.isEmpty() ? "" : (parentPath.isEmpty() ? spec.id : parentPath + "." + spec.id);
        int resolvedX = parentX + spec.x;
        int resolvedY = parentY + spec.y;
        nodes.put(path, new ResolvedLayoutNode(spec.id, path, resolvedX, resolvedY, spec.width, spec.height));
        for (LayoutNodeSpec child : spec.children) {
            resolveNode(child, path, resolvedX, resolvedY, nodes);
        }
    }
}
