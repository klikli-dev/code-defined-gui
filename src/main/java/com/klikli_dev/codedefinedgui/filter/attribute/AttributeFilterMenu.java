// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.attribute;

import com.klikli_dev.codedefinedgui.filter.menu.AbstractFilterMenu;
import com.klikli_dev.codedefinedgui.registry.CDGMenuTypes;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class AttributeFilterMenu extends AbstractFilterMenu {
    public static final int BUTTON_RESET = 0;
    public static final int BUTTON_MATCH_ANY = 1;
    public static final int BUTTON_MATCH_ALL = 2;
    public static final int BUTTON_DENY = 3;
    public static final int BUTTON_NEXT_CANDIDATE = 4;
    public static final int BUTTON_ADD_SELECTED = 5;
    public static final int BUTTON_ADD_SELECTED_INVERTED = 6;

    private final DataSlot mode = DataSlot.standalone();
    private final DataSlot selectedCandidateIndex = DataSlot.standalone();

    public AttributeFilterMenu(int containerId, Inventory inventory, RegistryFriendlyByteBuf buffer) {
        this(containerId, inventory, buffer.readEnum(InteractionHand.class));
    }

    public AttributeFilterMenu(int containerId, Inventory inventory, InteractionHand hand) {
        super(CDGMenuTypes.ATTRIBUTE_FILTER.get(), containerId, inventory, hand, 1);

        AttributeFilterState state = AttributeFilterStateAccessor.INSTANCE.read(this.filterStack);
        this.mode.set(state.mode().ordinal());
        this.selectedCandidateIndex.set(0);
        this.addDataSlot(this.mode);
        this.addDataSlot(this.selectedCandidateIndex);
    }

    public AttributeFilterMode mode() {
        return AttributeFilterMode.values()[this.mode.get()];
    }

    public int selectedCandidateIndex() {
        return this.selectedCandidateIndex.get();
    }

    public ItemStack referenceStack() {
        return this.ghostInventory.getItem(0);
    }

    public AttributeFilterState state() {
        return AttributeFilterStateAccessor.INSTANCE.read(this.filterStack);
    }

    public List<AttributeCandidate> candidates() {
        if (this.player.level() == null || this.referenceStack().isEmpty()) {
            return List.of();
        }

        List<AttributeCandidate> candidates = new ArrayList<>();
        for (ItemAttributeType type : CdgItemAttributes.all()) {
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
                this.saveToFilterStack();
                return true;
            }
            case BUTTON_MATCH_ALL -> {
                this.mode.set(AttributeFilterMode.MATCH_ALL.ordinal());
                this.saveToFilterStack();
                return true;
            }
            case BUTTON_DENY -> {
                this.mode.set(AttributeFilterMode.DENY.ordinal());
                this.saveToFilterStack();
                return true;
            }
            case BUTTON_NEXT_CANDIDATE -> {
                List<AttributeCandidate> candidates = this.candidates();
                if (!candidates.isEmpty()) {
                    this.selectedCandidateIndex.set((this.selectedCandidateIndex() + 1) % candidates.size());
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
        if (index < 36) {
            ItemStack stack = this.slots.get(index).getItem();
            if (!stack.isEmpty()) {
                this.ghostInventory.setItem(0, stack.copyWithCount(1));
            }
            return ItemStack.EMPTY;
        }

        this.ghostInventory.setItem(0, ItemStack.EMPTY);
        return ItemStack.EMPTY;
    }

    @Override
    protected void onGhostInventoryChanged() {
        this.selectedCandidateIndex.set(0);
        super.onGhostInventoryChanged();
    }

    @Override
    protected int playerInventoryX() {
        return 40;
    }

    @Override
    protected int playerInventoryY() {
        return 107;
    }

    @Override
    protected void addFilterSlots() {
        this.addSlot(new Slot(this.ghostInventory, 0, 16, 24) {
            @Override
            public boolean mayPickup(Player player) {
                return false;
            }
        });
    }

    @Override
    protected void loadFromFilterStack() {
        this.ghostInventory.setItem(0, AttributeFilterStateAccessor.INSTANCE.read(this.filterStack).referenceStack());
    }

    @Override
    protected void saveToFilterStack() {
        AttributeFilterState state = this.state();
        this.writeState(new AttributeFilterState(this.referenceStack(), this.mode(), state.rules()));
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
        AttributeFilterStateAccessor.INSTANCE.write(this.filterStack, state);
    }
}
