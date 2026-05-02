// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.core;

import com.klikli_dev.codedefinedgui.filter.core.storage.GhostItemStorage;
import com.klikli_dev.codedefinedgui.filter.core.storage.GhostResourceHandlerSlot;
import com.klikli_dev.codedefinedgui.filter.core.layout.BuiltinFilterParts;
import com.klikli_dev.codedefinedgui.filter.core.layout.BuiltinSlotRoles;
import com.klikli_dev.codedefinedgui.filter.core.layout.BuiltinFilterLayouts;
import com.klikli_dev.codedefinedgui.filter.core.layout.MenuSlotView;
import com.klikli_dev.codedefinedgui.filter.core.layout.SlotRoleKey;
import com.klikli_dev.codedefinedgui.gui.style.BuiltinGuiStyles;
import com.klikli_dev.codedefinedgui.gui.style.GuiLayoutKey;
import com.klikli_dev.codedefinedgui.gui.style.GuiPartKey;
import com.klikli_dev.codedefinedgui.gui.style.GuiStyleKey;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import org.jetbrains.annotations.NotNull;

public abstract class FilterMenu extends AbstractContainerMenu {
    private static final int PLAYER_SLOT_COUNT = 36;
    private static final int OFFHAND_SLOT = 40;

    protected final Player player;
    protected final ItemStack draftFilterStack;
    protected final GhostItemStorage ghostStorage;
    private final Item filterItem;
    private final int heldSlot;
    private final int ghostSlots;
    private final int lockedPlayerSlotId;
    private final GuiLayoutKey layoutKey;
    private final GuiStyleKey styleKey;
    private final List<MenuSlotView> slotViews = new ArrayList<>();

    protected FilterMenu(MenuType<?> menuType, int containerId, Inventory inventory, InteractionHand hand, int ghostSlots, DataComponentType<ItemContainerContents> ghostComponent) {
        this(menuType, containerId, inventory, hand, BuiltinFilterLayouts.LIST_FILTER, null, ghostSlots, ghostComponent);
    }

    protected FilterMenu(MenuType<?> menuType, int containerId, Inventory inventory, InteractionHand hand, GuiLayoutKey layoutKey, GuiStyleKey styleKey, int ghostSlots, DataComponentType<ItemContainerContents> ghostComponent) {
        super(menuType, containerId);
        this.player = inventory.player;
        this.heldSlot = heldSlot(inventory.player, hand);
        this.filterItem = this.filterStack().getItem();
        this.draftFilterStack = this.filterStack().copy();
        this.ghostSlots = ghostSlots;
        this.lockedPlayerSlotId = playerSlotId(this.heldSlot);
        this.ghostStorage = new GhostItemStorage(ItemAccess.forStack(this.draftFilterStack), ghostComponent, ghostSlots);
        this.layoutKey = layoutKey;
        this.styleKey = styleKey != null ? styleKey : this.resolveStyleKey(this.draftFilterStack, layoutKey);

        this.addPlayerInventorySlots(inventory, this.playerInventoryX(), this.playerInventoryY());
        this.addFilterSlots();
    }

    public List<MenuSlotView> slotViews() {
        return List.copyOf(this.slotViews);
    }

    public ItemStack filterStack() {
        return this.player.getInventory().getItem(this.heldSlot);
    }

    public GuiLayoutKey layoutKey() {
        return this.layoutKey;
    }

    public GuiStyleKey styleKey() {
        return this.styleKey;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        ItemStack currentStack = player.getInventory().getItem(this.heldSlot);
        return player == this.player && player.isAlive() && !currentStack.isEmpty() && currentStack.getItem() == this.filterItem;
    }

    @Override
    public boolean canTakeItemForPickAll(@NotNull ItemStack stack, net.minecraft.world.inventory.Slot slotIn) {
        return !(slotIn instanceof GhostResourceHandlerSlot);
    }

    @Override
    public boolean canDragTo(net.minecraft.world.inventory.Slot slotIn) {
        return !(slotIn instanceof GhostResourceHandlerSlot);
    }

    @Override
    public void clicked(int slotId, int dragType, @NotNull ContainerInput clickTypeIn, @NotNull Player player) {
        if (this.isLockedPlayerSlotInteraction(slotId, dragType, clickTypeIn)) {
            return;
        }

        if (!this.isGhostSlotId(slotId)) {
            super.clicked(slotId, dragType, clickTypeIn, player);
            return;
        }

        int ghostSlot = this.toGhostSlot(slotId);
        if (!this.isGhostSlotInteractive(ghostSlot) || clickTypeIn == ContainerInput.THROW) {
            return;
        }

        ItemStack carried = this.getCarried();
        if (clickTypeIn == ContainerInput.CLONE) {
            if (player.isCreative() && carried.isEmpty()) {
                ItemStack stackInSlot = this.ghostStack(ghostSlot);
                if (!stackInSlot.isEmpty()) {
                    this.setCarried(stackInSlot.copyWithCount(stackInSlot.getMaxStackSize()));
                }
            }
            return;
        }

        this.setGhostStack(ghostSlot, carried);
    }

    @Override
    public void slotsChanged(Container container) {
        super.slotsChanged(container);
    }

    protected void onGhostContentsChanged() {
    }

    protected boolean isGhostSlotInteractive(int ghostSlot) {
        return ghostSlot >= 0 && ghostSlot < this.ghostSlots;
    }

    protected ItemStack ghostStack(int ghostSlot) {
        return this.ghostStorage.getStackInSlotCopy(ghostSlot);
    }

    protected boolean setGhostStack(int ghostSlot, ItemStack stack) {
        boolean changed = this.ghostStorage.setStackInSlot(ghostSlot, stack);
        if (changed) {
            this.onGhostContentsChanged();
            this.broadcastChanges();
        }
        return changed;
    }

    protected boolean clearGhostStack(int ghostSlot) {
        return this.setGhostStack(ghostSlot, ItemStack.EMPTY);
    }

    protected boolean clearGhostContents() {
        boolean changed = this.ghostStorage.clearContent();
        if (changed) {
            this.onGhostContentsChanged();
            this.broadcastChanges();
        }
        return changed;
    }

    protected Slot addGhostSlot(int slot, int xPosition, int yPosition, SlotRoleKey role) {
        return this.addLayoutSlot(new GhostResourceHandlerSlot(this.ghostStorage, slot, xPosition, yPosition), role);
    }

    protected Slot addGhostSlot(int slot, int xPosition, int yPosition, SlotRoleKey role, GuiPartKey part) {
        return this.addLayoutSlot(new GhostResourceHandlerSlot(this.ghostStorage, slot, xPosition, yPosition), role, part);
    }

    protected final int ghostMenuSlotId(int ghostSlot) {
        return PLAYER_SLOT_COUNT + ghostSlot;
    }

    private boolean isGhostSlotId(int slotId) {
        return slotId >= PLAYER_SLOT_COUNT && slotId < PLAYER_SLOT_COUNT + this.ghostSlots;
    }

    private boolean isLockedPlayerSlotInteraction(int slotId, int dragType, ContainerInput clickTypeIn) {
        if (slotId == this.lockedPlayerSlotId) {
            return true;
        }

        return clickTypeIn == ContainerInput.SWAP && this.handIsMainHandSlot(dragType);
    }

    private boolean handIsMainHandSlot(int hotbarSlot) {
        return this.heldSlot < 9 && hotbarSlot == this.heldSlot;
    }

    private int toGhostSlot(int slotId) {
        return slotId - PLAYER_SLOT_COUNT;
    }

    private static int playerSlotId(int inventorySlot) {
        if (inventorySlot < 9) {
            return 27 + inventorySlot;
        }

        if (inventorySlot < PLAYER_SLOT_COUNT) {
            return inventorySlot - 9;
        }

        return -1;
    }

    private static int heldSlot(Player player, InteractionHand hand) {
        return switch (hand) {
            case MAIN_HAND -> player.getInventory().getSelectedSlot();
            case OFF_HAND -> OFFHAND_SLOT;
        };
    }

    protected static GuiStyleKey readStyleKey(RegistryFriendlyByteBuf buffer) {
        return GuiStyleKey.of(Identifier.parse(buffer.readUtf()));
    }

    private GuiStyleKey resolveStyleKey(ItemStack stack, GuiLayoutKey layout) {
        if (stack.getItem() instanceof FilterItem<?> filterItem) {
            return filterItem.guiStyleKey(stack, layout);
        }

        return BuiltinGuiStyles.DEFAULT;
    }

    private void addPlayerInventorySlots(Inventory inventory, int x, int y) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                int slot = col + row * 9 + 9;
                this.addLayoutSlot(new Slot(inventory, slot, x + col * 18, y + row * 18), BuiltinSlotRoles.PLAYER_MAIN);
            }
        }

        for (int col = 0; col < 9; col++) {
            this.addLayoutSlot(new Slot(inventory, col, x + col * 18, y + 58), BuiltinSlotRoles.PLAYER_HOTBAR);
        }
    }

    protected Slot addLayoutSlot(Slot slot, SlotRoleKey role) {
        return this.addLayoutSlot(slot, role, BuiltinFilterParts.slotPart(role));
    }

    protected Slot addLayoutSlot(Slot slot, SlotRoleKey role, GuiPartKey part) {
        Slot addedSlot = this.addSlot(slot);
        this.slotViews.add(new MenuSlotView(addedSlot, role, part));
        return addedSlot;
    }

    protected abstract int playerInventoryX();

    protected abstract int playerInventoryY();

    protected abstract void addFilterSlots();
}
