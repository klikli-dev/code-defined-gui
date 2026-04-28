// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.attribute;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import com.klikli_dev.codedefinedgui.filter.FilterDefinition;
import com.klikli_dev.codedefinedgui.filter.FilterMatchContext;
import com.klikli_dev.codedefinedgui.filter.FilterStateAccessor;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public final class AttributeFilterDefinition implements FilterDefinition<AttributeFilterState> {
    public static final AttributeFilterDefinition INSTANCE = new AttributeFilterDefinition();
    private static final Identifier ID = Identifier.fromNamespaceAndPath(CodeDefinedGui.MODID, "attribute_filter");

    private AttributeFilterDefinition() {
    }

    @Override
    public Identifier id() {
        return ID;
    }

    @Override
    public FilterStateAccessor<AttributeFilterState> accessor() {
        return AttributeFilterStateAccessor.INSTANCE;
    }

    @Override
    public AttributeFilterState defaultState() {
        return AttributeFilterState.EMPTY;
    }

    @Override
    public boolean matches(ItemStack candidate, AttributeFilterState state, FilterMatchContext context) {
        if (state.rules().isEmpty()) {
            return true;
        }

        return switch (state.mode()) {
            case MATCH_ANY -> state.rules().stream().anyMatch(rule -> this.ruleMatches(rule, candidate, context));
            case MATCH_ALL -> state.rules().stream().allMatch(rule -> this.ruleMatches(rule, candidate, context));
            case DENY -> state.rules().stream().noneMatch(rule -> this.ruleMatches(rule, candidate, context));
        };
    }

    @Override
    public List<Component> summary(AttributeFilterState state, HolderLookup.Provider registries) {
        List<Component> lines = new ArrayList<>();
        if (state.rules().isEmpty()) {
            return lines;
        }

        lines.add(Component.translatable("codedefinedgui.filter.attribute.summary.mode", Component.translatable("codedefinedgui.filter.attribute.mode." + state.mode().getSerializedName()))
                .withStyle(ChatFormatting.GOLD));
        int previewCount = Math.min(state.rules().size(), 4);
        for (int i = 0; i < previewCount; i++) {
            AttributeRule rule = state.rules().get(i);
            Component description = ItemAttributes.get(rule.typeId())
                    .map(type -> type.describe(rule.payload(), registries, rule.inverted()))
                    .orElse(Component.literal(rule.typeId().toString()));
            lines.add(Component.literal("- ").append(description));
        }

        if (state.rules().size() > previewCount) {
            lines.add(Component.translatable("codedefinedgui.filter.summary.more", state.rules().size() - previewCount)
                    .withStyle(ChatFormatting.DARK_GRAY));
        }

        return lines;
    }

    private boolean ruleMatches(AttributeRule rule, ItemStack candidate, FilterMatchContext context) {
        boolean matches = ItemAttributes.get(rule.typeId())
                .map(type -> type.matches(candidate, context.level(), rule.payload()))
                .orElse(false);
        return rule.inverted() ? !matches : matches;
    }
}
