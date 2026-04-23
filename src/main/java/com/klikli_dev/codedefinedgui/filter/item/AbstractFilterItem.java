// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.item;

import com.klikli_dev.codedefinedgui.filter.FilterDefinition;
import com.klikli_dev.codedefinedgui.filter.FilterState;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public abstract class AbstractFilterItem<S extends FilterState> extends Item {
    private final FilterDefinition<S> definition;

    protected AbstractFilterItem(Properties properties, FilterDefinition<S> definition) {
        super(properties);
        this.definition = definition;
    }

    public FilterDefinition<S> definition() {
        return this.definition;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(new SimpleMenuProvider(
                    (containerId, inventory, menuPlayer) -> this.createMenu(containerId, inventory, hand),
                    stack.getHoverName()
            ), buffer -> buffer.writeEnum(hand));
        }

        return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
    }

    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        List<Component> summary = this.definition.summary(stack, context.registries());
        if (!summary.isEmpty()) {
            tooltipComponents.add(Component.empty());
            tooltipComponents.addAll(summary);
        }
    }

    protected abstract AbstractContainerMenu createMenu(int containerId, net.minecraft.world.entity.player.Inventory inventory, InteractionHand hand);
}
