// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.datagen.lang;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import com.klikli_dev.codedefinedgui.registry.ItemRegistry;
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
        this.add(CodeDefinedGui.MODID + ".configuration.title", "Code Defined Gui Configs");
    }

    protected void addCreativeTabs() {
        this.add("itemGroup." + CodeDefinedGui.MODID, "Code Defined GUI");
    }

    protected void addItems() {
        this.addItem(ItemRegistry.LIST_FILTER, "List Filter");
        this.addItem(ItemRegistry.ATTRIBUTE_FILTER, "Attribute Filter");
    }

    protected void addFilters() {
        this.add("codedefinedgui.filter.button.reset", "Reset");
        this.add("codedefinedgui.filter.button.done", "Done");
        this.add("codedefinedgui.filter.button.reset.tooltip", "Reset Filter Settings");
        this.add("codedefinedgui.filter.button.done.tooltip", "Save Filter Settings");
        this.add("codedefinedgui.filter.summary.more", "...and %s more");
        this.add("codedefinedgui.tooltip.show_extended", "§6[§dshift §7show more§6]");

        this.add("codedefinedgui.filter.list.mode.allow", "Allow List");
        this.add("codedefinedgui.filter.list.mode.deny", "Deny List");
        this.add("codedefinedgui.filter.list.respect_data", "Respect Data Components");
        this.add("codedefinedgui.filter.list.ignore_data", "Ignore Data Components");
        this.add("codedefinedgui.filter.list.mode.allow.tooltip", "Allow-List");
        this.add("codedefinedgui.filter.list.mode.allow.tooltip.shift", "Items pass if they match any of the above. An empty Allow-List rejects everything.");
        this.add("codedefinedgui.filter.list.mode.deny.tooltip", "Deny-List");
        this.add("codedefinedgui.filter.list.mode.deny.tooltip.shift", "Items pass if they DO NOT match any of the above. An empty Deny-List accepts everything.");
        this.add("codedefinedgui.filter.list.respect_data.tooltip", "Respect Data");
        this.add("codedefinedgui.filter.list.respect_data.tooltip.shift", "Items pass if they match the data components of the filter item (durability, enchantments and others).");
        this.add("codedefinedgui.filter.list.ignore_data.tooltip", "Ignore Data");
        this.add("codedefinedgui.filter.list.ignore_data.tooltip.shift", "Items pass regardless of their data components (durability, enchantments and others).");
        this.add("codedefinedgui.filter.list.summary.mode", "Mode: %s");
        this.add("codedefinedgui.filter.list.summary.respect_data", "Matches item components");
        this.add("codedefinedgui.filter.list.summary.ignore_data", "Ignores item components");

        this.add("codedefinedgui.filter.attribute.mode.match_any", "Match Any");
        this.add("codedefinedgui.filter.attribute.mode.match_all", "Match All");
        this.add("codedefinedgui.filter.attribute.mode.deny", "Deny Matching");
        this.add("codedefinedgui.filter.attribute.mode.match_any.tooltip", "Allow-List (Any)");
        this.add("codedefinedgui.filter.attribute.mode.match_any.tooltip.shift", "Items pass if they have ANY of the selected attributes.");
        this.add("codedefinedgui.filter.attribute.mode.match_all.tooltip", "Allow-List (All)");
        this.add("codedefinedgui.filter.attribute.mode.match_all.tooltip.shift", "Items pass if they have ALL of the selected attributes.");
        this.add("codedefinedgui.filter.attribute.mode.deny.tooltip", "Deny-List");
        this.add("codedefinedgui.filter.attribute.mode.deny.tooltip.shift", "Items pass if they do NOT have any of the selected attributes.");
        this.add("codedefinedgui.filter.attribute.summary.mode", "Mode: %s");
        this.add("codedefinedgui.filter.attribute.add", "Add Rule");
        this.add("codedefinedgui.filter.attribute.add_inverted", "Add Inverted Rule");
        this.add("codedefinedgui.filter.attribute.add.tooltip", "Add attribute to list");
        this.add("codedefinedgui.filter.attribute.add_inverted.tooltip", "Add opposite attribute to list");
        this.add("codedefinedgui.filter.attribute.no_reference", "Add a reference item");
        this.add("codedefinedgui.filter.attribute.no_rules", "No rules selected");
        this.add("codedefinedgui.filter.attribute.inverted", "Not %s");
        this.add("codedefinedgui.filter.attribute.in_tag", "In tag %s");
        this.add("codedefinedgui.filter.attribute.added_by", "Added by %s");
        this.add("codedefinedgui.filter.attribute.has_enchantment", "Has enchantment %s");
        this.add("codedefinedgui.filter.attribute.has_fluid", "Contains %s");
        this.add("codedefinedgui.filter.attribute.has_name", "Named \"%s\"");
        this.add("codedefinedgui.filter.attribute.standard.placeable", "Placeable");
        this.add("codedefinedgui.filter.attribute.standard.consumable", "Consumable");
        this.add("codedefinedgui.filter.attribute.standard.fluid_container", "Fluid Container");
        this.add("codedefinedgui.filter.attribute.standard.enchanted", "Enchanted");
        this.add("codedefinedgui.filter.attribute.standard.renamed", "Renamed");
        this.add("codedefinedgui.filter.attribute.standard.damaged", "Damaged");
        this.add("codedefinedgui.filter.attribute.standard.badly_damaged", "Badly Damaged");
        this.add("codedefinedgui.filter.attribute.standard.not_stackable", "Not Stackable");
        this.add("codedefinedgui.filter.attribute.standard.equipable", "Equipable");
        this.add("codedefinedgui.filter.attribute.standard.furnace_fuel", "Furnace Fuel");
        this.add("codedefinedgui.filter.attribute.standard.smeltable", "Smeltable");
        this.add("codedefinedgui.filter.attribute.standard.smokable", "Smokable");
        this.add("codedefinedgui.filter.attribute.standard.blastable", "Blastable");
        this.add("codedefinedgui.filter.attribute.standard.compostable", "Compostable");
    }
}
