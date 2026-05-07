// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.datagen.lang;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import com.klikli_dev.codedefinedgui.internal.CodeDefinedGuiConstants;
import com.klikli_dev.codedefinedgui.internal.registry.ItemRegistry;
import com.klikli_dev.codedefinedgui.premade.filter.core.FilterTranslationKeys;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ENUSLanguageProvider extends LanguageProvider {
    public ENUSLanguageProvider(PackOutput output) {
        super(output, CodeDefinedGui.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.addConfig();
        this.addCreativeTabs();
        this.addItems();
        this.addFilters();
    }

    protected void addConfig() {
        this.add(CodeDefinedGuiConstants.Config.TITLE, "Code Defined Gui Configs");
    }

    protected void addCreativeTabs() {
        this.add(CodeDefinedGuiConstants.ItemGroup.MAIN, "Code Defined GUI");
    }

    protected void addItems() {
        this.addItem(ItemRegistry.LIST_FILTER, "List Filter");
        this.addItem(ItemRegistry.ATTRIBUTE_FILTER, "Attribute Filter");
    }

    protected void addFilters() {
        this.add(FilterTranslationKeys.Button.RESET, "Reset");
        this.add(FilterTranslationKeys.Button.DONE, "Done");
        this.add(FilterTranslationKeys.Button.RESET_TOOLTIP, "Reset Filter Settings");
        this.add(FilterTranslationKeys.Button.DONE_TOOLTIP, "Save Filter Settings");
        this.add(FilterTranslationKeys.SUMMARY_MORE, "...and %s more");
        this.add(CodeDefinedGuiConstants.I18n.Tooltip.SHOW_EXTENDED, "§6[§dshift §7show more§6]");

        this.add(FilterTranslationKeys.List.Mode.ALLOW, "Allow List");
        this.add(FilterTranslationKeys.List.Mode.DENY, "Deny List");
        this.add(FilterTranslationKeys.List.RESPECT_DATA, "Respect Data Components");
        this.add(FilterTranslationKeys.List.IGNORE_DATA, "Ignore Data Components");
        this.add(FilterTranslationKeys.List.Mode.ALLOW_TOOLTIP, "Allow-List");
        this.add(FilterTranslationKeys.List.Mode.ALLOW_TOOLTIP_SHIFT, "Items pass if they match any of the above. An empty Allow-List rejects everything.");
        this.add(FilterTranslationKeys.List.Mode.DENY_TOOLTIP, "Deny-List");
        this.add(FilterTranslationKeys.List.Mode.DENY_TOOLTIP_SHIFT, "Items pass if they DO NOT match any of the above. An empty Deny-List accepts everything.");
        this.add(FilterTranslationKeys.List.RESPECT_DATA_TOOLTIP, "Respect Data");
        this.add(FilterTranslationKeys.List.RESPECT_DATA_TOOLTIP_SHIFT, "Items pass if they match the data components of the filter item (durability, enchantments and others).");
        this.add(FilterTranslationKeys.List.IGNORE_DATA_TOOLTIP, "Ignore Data");
        this.add(FilterTranslationKeys.List.IGNORE_DATA_TOOLTIP_SHIFT, "Items pass regardless of their data components (durability, enchantments and others).");
        this.add(FilterTranslationKeys.List.SUMMARY_MODE, "Mode: %s");
        this.add(FilterTranslationKeys.List.SUMMARY_RESPECT_DATA, "Matches item components");
        this.add(FilterTranslationKeys.List.SUMMARY_IGNORE_DATA, "Ignores item components");

        this.add(FilterTranslationKeys.Attribute.Mode.MATCH_ANY, "Match Any");
        this.add(FilterTranslationKeys.Attribute.Mode.MATCH_ALL, "Match All");
        this.add(FilterTranslationKeys.Attribute.Mode.DENY, "Deny Matching");
        this.add(FilterTranslationKeys.Attribute.Mode.MATCH_ANY_TOOLTIP, "Allow-List (Any)");
        this.add(FilterTranslationKeys.Attribute.Mode.MATCH_ANY_TOOLTIP_SHIFT, "Items pass if they have ANY of the selected attributes.");
        this.add(FilterTranslationKeys.Attribute.Mode.MATCH_ALL_TOOLTIP, "Allow-List (All)");
        this.add(FilterTranslationKeys.Attribute.Mode.MATCH_ALL_TOOLTIP_SHIFT, "Items pass if they have ALL of the selected attributes.");
        this.add(FilterTranslationKeys.Attribute.Mode.DENY_TOOLTIP, "Deny-List");
        this.add(FilterTranslationKeys.Attribute.Mode.DENY_TOOLTIP_SHIFT, "Items pass if they do NOT have any of the selected attributes.");
        this.add(FilterTranslationKeys.Attribute.SUMMARY_MODE, "Mode: %s");
        this.add(FilterTranslationKeys.Attribute.AVAILABLE, "Available");
        this.add(FilterTranslationKeys.Attribute.ADD, "Add Rule");
        this.add(FilterTranslationKeys.Attribute.ADD_INVERTED, "Add Inverted Rule");
        this.add(FilterTranslationKeys.Attribute.ADD_TOOLTIP, "Add attribute to list");
        this.add(FilterTranslationKeys.Attribute.ADD_INVERTED_TOOLTIP, "Add opposite attribute to list");
        this.add(FilterTranslationKeys.Attribute.NO_REFERENCE, "Add a reference item");
        this.add(FilterTranslationKeys.Attribute.NO_RULES, "No rules selected");
        this.add(FilterTranslationKeys.Attribute.INVERTED, "Not %s");
        this.add(FilterTranslationKeys.Attribute.IN_TAG, "In tag %s");
        this.add(FilterTranslationKeys.Attribute.ADDED_BY, "Added by %s");
        this.add(FilterTranslationKeys.Attribute.HAS_ENCHANTMENT, "Has enchantment %s");
        this.add(FilterTranslationKeys.Attribute.HAS_FLUID, "Contains %s");
        this.add(FilterTranslationKeys.Attribute.HAS_NAME, "Named \"%s\"");
        this.add(FilterTranslationKeys.Attribute.Standard.key("placeable"), "Placeable");
        this.add(FilterTranslationKeys.Attribute.Standard.key("consumable"), "Consumable");
        this.add(FilterTranslationKeys.Attribute.Standard.key("fluid_container"), "Fluid Container");
        this.add(FilterTranslationKeys.Attribute.Standard.key("enchanted"), "Enchanted");
        this.add(FilterTranslationKeys.Attribute.Standard.key("renamed"), "Renamed");
        this.add(FilterTranslationKeys.Attribute.Standard.key("damaged"), "Damaged");
        this.add(FilterTranslationKeys.Attribute.Standard.key("badly_damaged"), "Badly Damaged");
        this.add(FilterTranslationKeys.Attribute.Standard.key("not_stackable"), "Not Stackable");
        this.add(FilterTranslationKeys.Attribute.Standard.key("equipable"), "Equipable");
        this.add(FilterTranslationKeys.Attribute.Standard.key("furnace_fuel"), "Furnace Fuel");
        this.add(FilterTranslationKeys.Attribute.Standard.key("smeltable"), "Smeltable");
        this.add(FilterTranslationKeys.Attribute.Standard.key("smokable"), "Smokable");
        this.add(FilterTranslationKeys.Attribute.Standard.key("blastable"), "Blastable");
        this.add(FilterTranslationKeys.Attribute.Standard.key("compostable"), "Compostable");
    }
}


