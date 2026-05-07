// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.layout;

import com.klikli_dev.codedefinedgui.gui.core.GuiHost;
import com.klikli_dev.codedefinedgui.gui.core.GuiRootWidget;
import com.klikli_dev.codedefinedgui.gui.style.GuiStyleContext;
import com.klikli_dev.codedefinedgui.gui.style.StyleContext;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import net.minecraft.client.gui.components.AbstractWidget;

public final class ScreenLayoutController {
    private final LayoutScreen owner;
    private final GuiHost host;
    private final GuiRootWidget root;
    private final StyleContext style;

    public ScreenLayoutController(LayoutScreen owner, GuiHost host, GuiRootWidget root, StyleContext style) {
        this.owner = owner;
        this.host = host;
        this.root = root;
        this.style = style;
    }

    public void init() {
        ResolvedLayout layout = this.owner.layoutSpec().resolve();
        ResolverRegistryImpl registry = new ResolverRegistryImpl(layout, "");
        this.owner.registerResolvers(registry);
        registry.execute(this.host, this.root, this.style);
    }

    private static final class ResolverRegistryImpl implements LayoutResolverRegistry {
        private final ResolvedLayout layout;
        private final String scope;
        private final AtomicLong sequence = new AtomicLong();
        private final Map<String, Registration> primary = new LinkedHashMap<>();
        private final List<Registration> additional = new ArrayList<>();

        private ResolverRegistryImpl(ResolvedLayout layout, String scope) {
            this.layout = layout;
            this.scope = scope;
        }

        @Override
        public void resolve(String id, Consumer<LayoutResolveContext> resolver) {
            this.resolve(id, 0, resolver);
        }

        @Override
        public void resolve(String id, int priority, Consumer<LayoutResolveContext> resolver) {
            String path = scopedPath(this.scope, id);
            this.primary.put(path, new Registration(path, priority, this.sequence.getAndIncrement(), resolver));
        }

        @Override
        public void add(String id, Consumer<LayoutResolveContext> resolver) {
            this.add(id, 0, resolver);
        }

        @Override
        public void add(String id, int priority, Consumer<LayoutResolveContext> resolver) {
            String path = scopedPath(this.scope, id);
            this.additional.add(new Registration(path, priority, this.sequence.getAndIncrement(), resolver));
        }

        @Override
        public LayoutResolverRegistry scope(String id) {
            return new ScopedResolverRegistry(this, scopedPath(this.scope, id));
        }

        private void execute(GuiHost host, GuiRootWidget root, StyleContext style) {
            List<Registration> registrations = new ArrayList<>(this.primary.values());
            registrations.addAll(this.additional);
            registrations.sort(Comparator.comparingInt(Registration::priority).thenComparingLong(Registration::order));
            for (Registration registration : registrations) {
                registration.resolver().accept(new ScreenResolveContext(this.layout, registration.path(), host, root, style));
            }
        }
    }

    private record Registration(String path, int priority, long order, Consumer<LayoutResolveContext> resolver) {
    }

    private record ScopedResolverRegistry(ResolverRegistryImpl delegate, String scope) implements LayoutResolverRegistry {
        @Override
        public void resolve(String id, Consumer<LayoutResolveContext> resolver) {
            this.delegate.resolve(scopedPath(this.scope, id), resolver);
        }

        @Override
        public void resolve(String id, int priority, Consumer<LayoutResolveContext> resolver) {
            this.delegate.resolve(scopedPath(this.scope, id), priority, resolver);
        }

        @Override
        public void add(String id, Consumer<LayoutResolveContext> resolver) {
            this.delegate.add(scopedPath(this.scope, id), resolver);
        }

        @Override
        public void add(String id, int priority, Consumer<LayoutResolveContext> resolver) {
            this.delegate.add(scopedPath(this.scope, id), priority, resolver);
        }

        @Override
        public LayoutResolverRegistry scope(String id) {
            return new ScopedResolverRegistry(this.delegate, scopedPath(this.scope, id));
        }
    }

    private record ScreenResolveContext(ResolvedLayout layout, String path, GuiHost host,
                                        GuiRootWidget root, StyleContext style) implements LayoutResolveContext {
        @Override
        public LayoutNodeView node() {
            return new ScreenNodeView(this.layout.node(this.path), this.host);
        }

        @Override
        public LayoutNodeView node(String id) {
            return new ScreenNodeView(LayoutResolvers.findRelative(this.layout, parentScope(this.path), id), this.host);
        }

        @Override
        public StyleContext style() {
            return this.style;
        }

        @Override
        public void addWidget(AbstractWidget widget) {
            this.root.addChild(widget);
        }
    }

    private record ScreenNodeView(ResolvedLayoutNode delegate, GuiHost host) implements LayoutNodeView {
        @Override
        public String id() {
            return this.delegate.id();
        }

        @Override
        public String path() {
            return this.delegate.path();
        }

        @Override
        public int x() {
            return this.host.guiX(this.delegate.x());
        }

        @Override
        public int y() {
            return this.host.guiY(this.delegate.y());
        }

        @Override
        public Integer width() {
            return this.delegate.width();
        }

        @Override
        public Integer height() {
            return this.delegate.height();
        }
    }

    private static String scopedPath(String scope, String id) {
        return scope == null || scope.isEmpty() ? id : scope + "." + id;
    }

    private static String parentScope(String path) {
        int separator = path.lastIndexOf('.');
        return separator >= 0 ? path.substring(0, separator) : "";
    }
}
