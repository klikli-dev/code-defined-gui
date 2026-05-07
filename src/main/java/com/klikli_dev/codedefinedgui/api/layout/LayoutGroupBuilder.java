// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.api.layout;

import java.util.function.Consumer;

public class LayoutGroupBuilder extends LayoutNodeBuilder {
    LayoutGroupBuilder(LayoutNodeSpec spec) {
        super(spec);
    }

    public LayoutGroupBuilder group(String id, Consumer<LayoutGroupBuilder> builder) {
        LayoutNodeSpec child = new LayoutNodeSpec(id, this.spec, true);
        this.spec.children.add(child);
        LayoutGroupBuilder childBuilder = new LayoutGroupBuilder(child);
        builder.accept(childBuilder);
        return this;
    }

    public LayoutNodeBuilder node(String id) {
        LayoutNodeSpec child = new LayoutNodeSpec(id, this.spec, false);
        this.spec.children.add(child);
        return new LayoutNodeBuilder(child);
    }

    /**
     * Looks up an already-defined node or group relative to this builder scope.
     * <p>
     * Resolution starts in the current group, then walks up parent scopes until a match is found.
     * Like runtime relative node lookup, this supports sibling references such as
     * {@code area.ref("top_bar.background")}. Only nodes that have already been defined can be found.
     */
    public LayoutNodeView ref(String id) {
        return new LayoutNodeBuilder(LayoutNodeSpecs.findRelative(this.spec, id));
    }
}


