// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.filter.attribute;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import com.klikli_dev.codedefinedgui.CodeDefinedGuiConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ComposterBlock;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.access.ItemAccess;

public class StandardAttributeType implements ItemAttributeType {
    private static final Identifier ID = Identifier.fromNamespaceAndPath(CodeDefinedGui.MODID, "standard");

    @Override
    public Identifier id() {
        return ID;
    }

    @Override
    public List<AttributeCandidate> collectCandidates(ItemStack reference, Level level) {
        List<AttributeCandidate> candidates = new ArrayList<>();
        for (StandardAttribute attribute : StandardAttribute.values()) {
            if (attribute.matches(reference, level)) {
                CustomData payload = AttributePayloads.ofString("value", attribute.id());
                candidates.add(new AttributeCandidate(new AttributeRule(this.id(), payload, false), this.describe(payload, level.registryAccess(), false)));
            }
        }

        return candidates;
    }

    @Override
    public boolean matches(ItemStack candidate, Level level, CustomData payload) {
        return StandardAttribute.byId(AttributePayloads.getString(payload, "value"))
                .map(attribute -> attribute.matches(candidate, level))
                .orElse(false);
    }

    @Override
    public Component describe(CustomData payload, HolderLookup.Provider registries, boolean inverted) {
        String value = AttributePayloads.getString(payload, "value");
        Component base = Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.Standard.key(value));
        return inverted ? Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.INVERTED, base) : base;
    }

    private enum StandardAttribute {
        PLACEABLE("placeable", stack -> stack.getItem() instanceof BlockItem),
        CONSUMABLE("consumable", stack -> stack.has(DataComponents.FOOD)),
        FLUID_CONTAINER("fluid_container", stack -> ItemAccess.forStack(stack).oneByOne().getCapability(Capabilities.Fluid.ITEM) != null),
        ENCHANTED("enchanted", ItemStack::isEnchanted),
        RENAMED("renamed", stack -> stack.has(DataComponents.CUSTOM_NAME)),
        DAMAGED("damaged", ItemStack::isDamaged),
        BADLY_DAMAGED("badly_damaged", stack -> stack.isDamaged() && (float) stack.getDamageValue() / stack.getMaxDamage() > 0.75F),
        NOT_STACKABLE("not_stackable", stack -> !stack.isStackable()),
        EQUIPABLE("equipable", stack -> stack.has(DataComponents.EQUIPPABLE)),
        FURNACE_FUEL("furnace_fuel", (stack, level) -> stack.getBurnTime(RecipeType.SMELTING, level.fuelValues()) > 0),
        SMELTABLE("smeltable", (stack, level) -> hasRecipe(stack, level, RecipeType.SMELTING)),
        SMOKABLE("smokable", (stack, level) -> hasRecipe(stack, level, RecipeType.SMOKING)),
        BLASTABLE("blastable", (stack, level) -> hasRecipe(stack, level, RecipeType.BLASTING)),
        COMPOSTABLE("compostable", stack -> ComposterBlock.COMPOSTABLES.containsKey(stack.getItem()));

        private final String id;
        private final Predicate<ItemStack> predicate;
        private final BiPredicate<ItemStack, Level> levelPredicate;

        StandardAttribute(String id, Predicate<ItemStack> predicate) {
            this.id = id;
            this.predicate = predicate;
            this.levelPredicate = null;
        }

        StandardAttribute(String id, BiPredicate<ItemStack, Level> levelPredicate) {
            this.id = id;
            this.predicate = null;
            this.levelPredicate = levelPredicate;
        }

        String id() {
            return this.id;
        }

        boolean matches(ItemStack stack, Level level) {
            if (this.levelPredicate != null) {
                return this.levelPredicate.test(stack, level);
            }

            return this.predicate.test(stack);
        }

        static java.util.Optional<StandardAttribute> byId(String id) {
            for (StandardAttribute attribute : values()) {
                if (attribute.id.equals(id)) {
                    return java.util.Optional.of(attribute);
                }
            }

            return java.util.Optional.empty();
        }

        private static boolean hasRecipe(ItemStack stack, Level level, RecipeType<? extends Recipe<SingleRecipeInput>> type) {
            if (!(level instanceof net.minecraft.server.level.ServerLevel serverLevel)) {
                return false;
            }

            return serverLevel.getServer().getRecipeManager().getRecipeFor(type, new SingleRecipeInput(stack), level).isPresent();
        }
    }
}
