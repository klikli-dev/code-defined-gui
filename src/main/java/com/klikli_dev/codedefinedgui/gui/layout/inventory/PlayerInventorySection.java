// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.layout.inventory;

import com.klikli_dev.codedefinedgui.internal.layout.LayoutFragment;
import com.klikli_dev.codedefinedgui.api.layout.LayoutGroupBuilder;
import com.klikli_dev.codedefinedgui.internal.layout.LayoutMenuBinder;
import com.klikli_dev.codedefinedgui.internal.layout.LayoutMenuBinderHost;
import com.klikli_dev.codedefinedgui.api.layout.LayoutResolverRegistry;
import com.klikli_dev.codedefinedgui.internal.layout.LayoutScreenRenderer;
import com.klikli_dev.codedefinedgui.internal.layout.LayoutScreenRendererHost;
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

    public void bindMenu(MenuBindingRegistry registry, LayoutMenuBinderHost host) {
        this.binder.bind(registry, host);
    }

    public void registerResolvers(LayoutResolverRegistry registry, LayoutScreenRendererHost host) {
        this.renderer.registerResolvers(registry, host);
    }

    public LayoutFragment fragment() {
        return this.fragment;
    }

    public LayoutMenuBinder menuBinder() {
        return this.binder;
    }

    public LayoutScreenRenderer screenRenderer() {
        return this.renderer;
    }
}
