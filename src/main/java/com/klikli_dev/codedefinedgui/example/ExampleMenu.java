// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.example;

import com.klikli_dev.codedefinedgui.api.layout.LayoutMenuView;
import com.klikli_dev.codedefinedgui.api.layout.LayoutSpec;
import com.klikli_dev.codedefinedgui.api.layout.MenuBindingRegistry;
import com.klikli_dev.codedefinedgui.api.layout.MenuLayoutController;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ExampleMenu extends AbstractContainerMenu implements LayoutMenuView {
    private final LayoutSpec layout = ExampleLayouts.BASIC_SCREEN;
    private final MenuLayoutController layoutController = new MenuLayoutController(this);

    public ExampleMenu(int containerId, Inventory inventory) {
        super(null, containerId);
        this.layoutController.bind();
    }

    @Override
    public LayoutSpec layoutSpec() {
        return this.layout;
    }

    @Override
    public void registerBindings(MenuBindingRegistry registry) {
        registry.bind("main.input", ctx -> this.addSlot(new Slot(new ExampleSlotContainer(), 0, ctx.node().x(), ctx.node().y())));
        registry.bind("main.output", ctx -> this.addSlot(new Slot(new ExampleSlotContainer(), 1, ctx.node().x(), ctx.node().y())));
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        return ItemStack.EMPTY;
    }
}
