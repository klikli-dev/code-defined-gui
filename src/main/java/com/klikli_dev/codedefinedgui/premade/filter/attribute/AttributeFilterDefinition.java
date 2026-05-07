// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.premade.filter.attribute;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import com.klikli_dev.codedefinedgui.CodeDefinedGuiConstants;
import com.klikli_dev.codedefinedgui.premade.filter.core.FilterDefinition;
import com.klikli_dev.codedefinedgui.premade.filter.core.FilterMatchContext;
import com.klikli_dev.codedefinedgui.premade.filter.core.FilterStateAccessor;
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

        lines.add(Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.SUMMARY_MODE, Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.Mode.key(state.mode().getSerializedName())))
                .withStyle(ChatFormatting.GOLD));
        int previewCount = Math.min(state.rules().size(), 4);
        for (int i = 0; i < previewCount; i++) {
            AttributeRule rule = state.rules().get(i);
            ItemAttributeType type = ItemAttributes.get(rule.typeId());
            Component description = type != null
                    ? type.describe(rule.payload(), registries, rule.inverted())
                    : Component.literal(rule.typeId().toString());
            lines.add(Component.literal("- ").append(description));
        }

        if (state.rules().size() > previewCount) {
            lines.add(Component.translatable(CodeDefinedGuiConstants.I18n.Filter.SUMMARY_MORE, state.rules().size() - previewCount)
                    .withStyle(ChatFormatting.DARK_GRAY));
        }

        return lines;
    }

    private boolean ruleMatches(AttributeRule rule, ItemStack candidate, FilterMatchContext context) {
        ItemAttributeType type = ItemAttributes.get(rule.typeId());
        boolean matches = type != null && type.matches(candidate, context.level(), rule.payload());
        return rule.inverted() ? !matches : matches;
    }
}



