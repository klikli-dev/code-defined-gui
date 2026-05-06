// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.layout;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.world.inventory.Slot;

public final class MenuLayoutController {
    private final LayoutMenu owner;

    public MenuLayoutController(LayoutMenu owner) {
        this.owner = owner;
    }

    public void bind() {
        ResolvedLayout layout = this.owner.layoutSpec().resolve();
        BindingRegistryImpl registry = new BindingRegistryImpl(layout, "");
        this.owner.registerBindings(registry);
        registry.execute();
    }

    private static final class BindingRegistryImpl implements MenuBindingRegistry {
        private final ResolvedLayout layout;
        private final String scope;
        private final Map<String, Consumer<MenuBindingContext>> primary = new LinkedHashMap<>();
        private final List<BindingRegistration> additional = new ArrayList<>();

        private BindingRegistryImpl(ResolvedLayout layout, String scope) {
            this.layout = layout;
            this.scope = scope;
        }

        @Override
        public void bind(String id, Consumer<MenuBindingContext> binding) {
            this.primary.put(scopedPath(this.scope, id), binding);
        }

        @Override
        public void add(String id, Consumer<MenuBindingContext> binding) {
            this.additional.add(new BindingRegistration(scopedPath(this.scope, id), binding));
        }

        @Override
        public MenuBindingRegistry scope(String id) {
            return new ScopedBindingRegistry(this, scopedPath(this.scope, id));
        }

        private void execute() {
            for (Map.Entry<String, Consumer<MenuBindingContext>> entry : this.primary.entrySet()) {
                entry.getValue().accept(new BindingContext(this.layout, entry.getKey()));
            }
            for (BindingRegistration registration : this.additional) {
                registration.binding().accept(new BindingContext(this.layout, registration.path()));
            }
        }
    }

    private record BindingRegistration(String path, Consumer<MenuBindingContext> binding) {
    }

    private record ScopedBindingRegistry(BindingRegistryImpl delegate, String scope) implements MenuBindingRegistry {
        @Override
        public void bind(String id, Consumer<MenuBindingContext> binding) {
            this.delegate.bind(scopedPath(this.scope, id), binding);
        }

        @Override
        public void add(String id, Consumer<MenuBindingContext> binding) {
            this.delegate.add(scopedPath(this.scope, id), binding);
        }

        @Override
        public MenuBindingRegistry scope(String id) {
            return new ScopedBindingRegistry(this.delegate, scopedPath(this.scope, id));
        }
    }

    private record BindingContext(ResolvedLayout layout, String path) implements MenuBindingContext {
        @Override
        public LayoutNodeView node() {
            return this.layout.node(this.path);
        }

        @Override
        public LayoutNodeView node(String id) {
            return LayoutResolvers.findRelative(this.layout, parentScope(this.path), id);
        }

        @Override
        public <T extends Slot> T addSlot(T slot) {
            return slot;
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
