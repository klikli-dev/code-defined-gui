// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.list;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import com.klikli_dev.codedefinedgui.filter.FilterDefinition;
import com.klikli_dev.codedefinedgui.filter.FilterMatchContext;
import com.klikli_dev.codedefinedgui.filter.FilterStateAccessor;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public final class ListFilterDefinition implements FilterDefinition<ListFilterState> {
    public static final ListFilterDefinition INSTANCE = new ListFilterDefinition();
    private static final Identifier ID = Identifier.fromNamespaceAndPath(CodeDefinedGui.MODID, "list_filter");

    private ListFilterDefinition() {
    }

    @Override
    public Identifier id() {
        return ID;
    }

    @Override
    public FilterStateAccessor<ListFilterState> accessor() {
        return ListFilterStateAccessor.INSTANCE;
    }

    @Override
    public ListFilterState defaultState() {
        return ListFilterState.EMPTY;
    }

    @Override
    public boolean matches(ItemStack candidate, ListFilterState state, FilterMatchContext context) {
        boolean matched = false;
        NonNullList<ItemStack> entries = NonNullList.withSize(state.entries().getSlots(), ItemStack.EMPTY);
        state.entries().copyInto(entries);
        for (ItemStack entry : entries) {
            if (state.respectDataComponents() ? ItemStack.isSameItemSameComponents(entry, candidate) : ItemStack.isSameItem(entry, candidate)) {
                matched = true;
                break;
            }
        }

        return state.mode() == ListFilterMode.ALLOW ? matched : !matched;
    }

    @Override
    public List<Component> summary(ListFilterState state, HolderLookup.Provider registries) {
        List<Component> lines = new ArrayList<>();
        List<ItemStack> entries = new ArrayList<>();
        NonNullList<ItemStack> contents = NonNullList.withSize(state.entries().getSlots(), ItemStack.EMPTY);
        state.entries().copyInto(contents);
        for (ItemStack entry : contents) {
            if (!entry.isEmpty()) {
                entries.add(entry);
            }
        }

        if (entries.isEmpty()) {
            return lines;
        }

        lines.add(Component.translatable("codedefinedgui.filter.list.summary.mode", Component.translatable("codedefinedgui.filter.list.mode." + state.mode().getSerializedName())));
        lines.add(Component.translatable(
                state.respectDataComponents() ? "codedefinedgui.filter.list.summary.respect_data" : "codedefinedgui.filter.list.summary.ignore_data"
        ));

        int previewCount = Math.min(entries.size(), 4);
        for (int i = 0; i < previewCount; i++) {
            lines.add(Component.literal("- ").append(entries.get(i).getHoverName()));
        }

        if (entries.size() > previewCount) {
            lines.add(Component.translatable("codedefinedgui.filter.summary.more", entries.size() - previewCount));
        }

        return lines;
    }
}
