// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.attribute;

import com.klikli_dev.codedefinedgui.filter.core.FilterMenu;
import com.klikli_dev.codedefinedgui.infrastructure.registry.DataComponentRegistry;
import com.klikli_dev.codedefinedgui.infrastructure.registry.MenuTypeRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class AttributeFilterMenu extends FilterMenu {
    public static final int BUTTON_RESET = 0;
    public static final int BUTTON_MATCH_ANY = 1;
    public static final int BUTTON_MATCH_ALL = 2;
    public static final int BUTTON_DENY = 3;
    public static final int BUTTON_NEXT_CANDIDATE = 4;
    public static final int BUTTON_PREVIOUS_CANDIDATE = 5;
    public static final int BUTTON_ADD_SELECTED = 6;
    public static final int BUTTON_ADD_SELECTED_INVERTED = 7;

    private final DataSlot mode = DataSlot.standalone();
    private final DataSlot selectedCandidateIndex = DataSlot.standalone();

    public AttributeFilterMenu(int containerId, Inventory inventory, RegistryFriendlyByteBuf buffer) {
        this(MenuTypeRegistry.ATTRIBUTE_FILTER.get(), containerId, inventory, buffer.readEnum(InteractionHand.class));
    }

    public AttributeFilterMenu(int containerId, Inventory inventory, InteractionHand hand) {
        this(MenuTypeRegistry.ATTRIBUTE_FILTER.get(), containerId, inventory, hand);
    }

    protected AttributeFilterMenu(MenuType<?> menuType, int containerId, Inventory inventory, InteractionHand hand) {
        super(menuType, containerId, inventory, hand, 2, DataComponentRegistry.ATTRIBUTE_FILTER_REFERENCE.get());

        AttributeFilterState state = AttributeFilterStateAccessor.INSTANCE.read(this.filterStack());
        this.mode.set(state.mode().ordinal());
        this.selectedCandidateIndex.set(0);
        this.addDataSlot(this.mode);
        this.addDataSlot(this.selectedCandidateIndex);

        this.syncSummarySlot();
    }

    public AttributeFilterMode mode() {
        return AttributeFilterMode.values()[this.mode.get()];
    }

    public int selectedCandidateIndex() {
        return this.selectedCandidateIndex.get();
    }

    public ItemStack referenceStack() {
        return this.ghostStack(0);
    }

    public ItemStack summaryStack() {
        ItemStack stack = new ItemStack(Items.NAME_TAG);
        stack.set(DataComponents.ITEM_NAME, Component.literal("Selected Tags").withStyle(ChatFormatting.RESET, ChatFormatting.BLUE));
        return stack;
    }

    public AttributeFilterState state() {
        return AttributeFilterStateAccessor.INSTANCE.read(this.filterStack());
    }

    public List<AttributeCandidate> candidates() {
        if (this.player.level() == null || this.referenceStack().isEmpty()) {
            return List.of();
        }

        List<AttributeCandidate> candidates = new ArrayList<>();
        for (ItemAttributeType type : ItemAttributes.all()) {
            candidates.addAll(type.collectCandidates(this.referenceStack(), this.player.level()));
        }

        return candidates;
    }

    public Optional<AttributeCandidate> selectedCandidate() {
        List<AttributeCandidate> candidates = this.candidates();
        if (candidates.isEmpty()) {
            return Optional.empty();
        }

        int index = Math.max(0, Math.min(this.selectedCandidateIndex(), candidates.size() - 1));
        if (index != this.selectedCandidateIndex()) {
            this.selectedCandidateIndex.set(index);
        }

        return Optional.of(candidates.get(index));
    }

    @Override
    public boolean clickMenuButton(Player player, int buttonId) {
        switch (buttonId) {
            case BUTTON_RESET -> {
                this.writeState(new AttributeFilterState(this.referenceStack(), this.mode(), List.of()));
                return true;
            }
            case BUTTON_MATCH_ANY -> {
                this.mode.set(AttributeFilterMode.MATCH_ANY.ordinal());
                this.saveState();
                return true;
            }
            case BUTTON_MATCH_ALL -> {
                this.mode.set(AttributeFilterMode.MATCH_ALL.ordinal());
                this.saveState();
                return true;
            }
            case BUTTON_DENY -> {
                this.mode.set(AttributeFilterMode.DENY.ordinal());
                this.saveState();
                return true;
            }
            case BUTTON_NEXT_CANDIDATE -> {
                List<AttributeCandidate> candidates = this.candidates();
                if (!candidates.isEmpty()) {
                    this.selectedCandidateIndex.set((this.selectedCandidateIndex() + 1) % candidates.size());
                }
                return true;
            }
            case BUTTON_PREVIOUS_CANDIDATE -> {
                List<AttributeCandidate> candidates = this.candidates();
                if (!candidates.isEmpty()) {
                    this.selectedCandidateIndex.set(Math.floorMod(this.selectedCandidateIndex() - 1, candidates.size()));
                }
                return true;
            }
            case BUTTON_ADD_SELECTED -> {
                return this.addSelectedRule(false);
            }
            case BUTTON_ADD_SELECTED_INVERTED -> {
                return this.addSelectedRule(true);
            }
            default -> {
                return false;
            }
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        if (index == 37) {
            return ItemStack.EMPTY;
        }

        if (index < 36) {
            ItemStack stack = this.slots.get(index).getItem();
            if (!stack.isEmpty()) {
                this.setGhostStack(0, stack);
            }
            return ItemStack.EMPTY;
        }

        this.clearGhostStack(0);
        return ItemStack.EMPTY;
    }

    @Override
    public void clicked(int slotId, int dragType, @NotNull ContainerInput clickTypeIn, @NotNull Player player) {
        if (slotId == 37) {
            return;
        }

        super.clicked(slotId, dragType, clickTypeIn, player);
    }

    @Override
    public boolean canDragTo(Slot slotIn) {
        if (slotIn.index == 37) {
            return false;
        }

        return super.canDragTo(slotIn);
    }

    @Override
    public boolean canTakeItemForPickAll(@NotNull ItemStack stack, Slot slotIn) {
        if (slotIn.index == 37) {
            return false;
        }

        return super.canTakeItemForPickAll(stack, slotIn);
    }

    @Override
    protected void onGhostContentsChanged() {
        this.selectedCandidateIndex.set(0);
        this.syncSummarySlot();
        super.onGhostContentsChanged();
    }

    @Override
    protected int playerInventoryX() {
        return 40;
    }

    @Override
    protected int playerInventoryY() {
        return 109;
    }

    @Override
    protected void addFilterSlots() {
        this.addGhostSlot(0, 19, 24);
        this.addGhostSlot(1, 22, 59);
    }

    private boolean addSelectedRule(boolean inverted) {
        AttributeFilterState state = this.state();
        Optional<AttributeCandidate> selected = this.selectedCandidate();
        if (selected.isEmpty()) {
            return false;
        }

        AttributeRule candidate = selected.get().rule();
        AttributeRule rule = new AttributeRule(candidate.typeId(), candidate.payload(), inverted);
        if (state.rules().contains(rule)) {
            return false;
        }

        List<AttributeRule> rules = new ArrayList<>(state.rules());
        rules.add(rule);
        this.writeState(new AttributeFilterState(this.referenceStack(), this.mode(), List.copyOf(rules)));
        return true;
    }

    private void writeState(AttributeFilterState state) {
        AttributeFilterStateAccessor.INSTANCE.write(this.filterStack(), state);
        this.syncSummarySlot();
    }

    private void saveState() {
        AttributeFilterState state = this.state();
        this.writeState(new AttributeFilterState(this.referenceStack(), this.mode(), state.rules()));
    }

    private void syncSummarySlot() {
        this.ghostStorage.setStackInSlot(1, this.summaryStack());
    }
}
