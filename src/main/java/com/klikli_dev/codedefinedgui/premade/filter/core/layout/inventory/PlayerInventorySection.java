// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.premade.filter.core.layout.inventory;

import com.klikli_dev.codedefinedgui.api.layout.LayoutGroupBuilder;
import com.klikli_dev.codedefinedgui.api.layout.LayoutResolverRegistry;
import com.klikli_dev.codedefinedgui.api.layout.MenuBindingRegistry;

public final class PlayerInventorySection {
    private final PlayerInventoryLayoutFragment fragment;
    private final PlayerInventoryMenuBinder binder;
    private final PlayerInventoryScreenRenderer renderer;

    private PlayerInventorySection(PlayerInventoryLayoutFragment fragment, PlayerInventoryMenuBinder binder, PlayerInventoryScreenRenderer renderer) {
        this.fragment = fragment;
        this.binder = binder;
        this.renderer = renderer;
    }

    public static PlayerInventorySection standard() {
        return new PlayerInventorySection(PlayerInventoryLayoutFragment.create(), new PlayerInventoryMenuBinder(), new PlayerInventoryScreenRenderer());
    }

    public void define(LayoutGroupBuilder root) {
        this.fragment.define(root);
    }

    public void bindMenu(MenuBindingRegistry registry, PlayerInventoryMenuHost host) {
        this.binder.bind(registry, host);
    }

    public void registerResolvers(LayoutResolverRegistry registry, PlayerInventoryScreenHost host) {
        this.renderer.registerResolvers(registry, host);
    }
}
