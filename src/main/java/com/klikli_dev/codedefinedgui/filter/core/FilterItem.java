// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.core;

import com.klikli_dev.codedefinedgui.gui.style.BuiltinGuiStyles;
import com.klikli_dev.codedefinedgui.gui.style.GuiLayoutKey;
import com.klikli_dev.codedefinedgui.gui.style.GuiStyleKey;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.network.RegistryFriendlyByteBuf;
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
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

public abstract class FilterItem<S extends FilterState> extends Item {
    private final FilterDefinition<S> definition;
    private final GuiStyleKey guiStyleKey;

    protected FilterItem(Properties properties, FilterDefinition<S> definition) {
        this(properties, definition, BuiltinGuiStyles.DEFAULT);
    }

    protected FilterItem(Properties properties, FilterDefinition<S> definition, GuiStyleKey guiStyleKey) {
        super(properties);
        this.definition = definition;
        this.guiStyleKey = guiStyleKey;
    }

    public FilterDefinition<S> definition() {
        return this.definition;
    }

    /**
     * Chooses the GUI style for the layout that is about to open.
     * <p>
     * The default implementation returns a single fixed style key, but downstream mods can
     * override this and return different styles for different layouts or stack state.
     * <p>
     * Example: a mod could return one style for {@code BuiltinFilterLayouts.LIST_FILTER}
     * and another for {@code BuiltinFilterLayouts.ATTRIBUTE_FILTER}.
     */
    public GuiStyleKey guiStyleKey(ItemStack stack, GuiLayoutKey layout) {
        return this.guiStyleKey;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(new SimpleMenuProvider(
                    (containerId, inventory, menuPlayer) -> this.createMenu(containerId, inventory, hand),
                    stack.getHoverName()
            ), buffer -> this.writeMenuData(buffer, player, hand, stack));
        }

        return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag tooltipFlag) {
        List<Component> summary = this.definition.summary(stack, context.registries());
        if (!summary.isEmpty()) {
            tooltipAdder.accept(Component.empty());
            summary.forEach(tooltipAdder);
        }
    }

    /**
     * Writes client menu initialization data.
     * <p>
     * Subclasses can override this to send additional data for custom menu and screen implementations.
     * Always call {@code super.writeMenuData(...)} first so the hand and chosen gui style key are written
     * in the expected order for the client menu constructor.
     */
    protected void writeMenuData(RegistryFriendlyByteBuf buffer, Player player, InteractionHand hand, ItemStack stack) {
        buffer.writeEnum(hand);
        buffer.writeUtf(this.guiStyleKey(stack, this.layoutKey()).id().toString());
    }

    protected GuiLayoutKey layoutKey() {
        return this.defaultLayoutKey();
    }

    protected abstract GuiLayoutKey defaultLayoutKey();

    /**
     * Creates the server-side menu for this filter item.
     * <p>
     * Subclasses may return custom menu subclasses and pair them with their own menu type and client screen registration.
     */
    protected abstract AbstractContainerMenu createMenu(int containerId, net.minecraft.world.entity.player.Inventory inventory, InteractionHand hand);
}
