// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.internal.integration.jei;

import com.klikli_dev.codedefinedgui.internal.network.SetFilterGhostSlotMessage;
import com.klikli_dev.codedefinedgui.premade.filter.AbstractFilterScreen;
import com.klikli_dev.codedefinedgui.premade.filter.core.FilterMenu;
import java.util.ArrayList;
import java.util.List;
import mezz.jei.api.gui.handlers.IGhostIngredientHandler;
import mezz.jei.api.ingredients.ITypedIngredient;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

public class FilterGhostIngredientHandler implements IGhostIngredientHandler<AbstractFilterScreen> {
    private static final int SLOT_SIZE = 16;

    @Override
    public <I> List<Target<I>> getTargetsTyped(AbstractFilterScreen screen, ITypedIngredient<I> ingredient, boolean doStart) {
        List<Target<I>> targets = new ArrayList<>();
        if (ingredient.getItemStack().isEmpty()) {
            return targets;
        }

        FilterMenu menu = (FilterMenu) screen.getMenu();
        for (int slotId = 0; slotId < menu.slots.size(); slotId++) {
            if (!menu.canSetGhostStackFromSlot(slotId)) {
                continue;
            }

            Slot slot = menu.slots.get(slotId);
            Rect2i area = new Rect2i(screen.leftPos() + slot.x, screen.topPos() + slot.y, SLOT_SIZE, SLOT_SIZE);
            targets.add(new GhostSlotTarget<>(screen, slotId, area));
        }

        return targets;
    }

    @Override
    public void onComplete() {
    }

    private static final class GhostSlotTarget<I> implements IGhostIngredientHandler.Target<I> {
        private final AbstractFilterScreen screen;
        private final int slotId;
        private final Rect2i area;

        private GhostSlotTarget(AbstractFilterScreen screen, int slotId, Rect2i area) {
            this.screen = screen;
            this.slotId = slotId;
            this.area = area;
        }

        @Override
        public Rect2i getArea() {
            return this.area;
        }

        @Override
        public void accept(I ingredient) {
            if (!(ingredient instanceof ItemStack stack)) {
                return;
            }

            FilterMenu menu = (FilterMenu) this.screen.getMenu();
            ItemStack ghostStack = stack.copyWithCount(1);
            if (menu.setGhostStackFromSlot(this.slotId, ghostStack)) {
                ClientPacketDistributor.sendToServer(new SetFilterGhostSlotMessage(menu.containerId, this.slotId, ghostStack));
            }
        }
    }
}
