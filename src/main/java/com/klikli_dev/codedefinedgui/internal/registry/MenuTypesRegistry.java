// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.internal.registry;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import com.klikli_dev.codedefinedgui.premade.filter.attribute.AttributeFilterMenu;
import com.klikli_dev.codedefinedgui.premade.filter.list.ListFilterMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class MenuTypesRegistry {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, CodeDefinedGui.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<ListFilterMenu>> LIST_FILTER = MENU_TYPES.register("list_filter", () -> IMenuTypeExtension.create(ListFilterMenu::new));
    public static final DeferredHolder<MenuType<?>, MenuType<AttributeFilterMenu>> ATTRIBUTE_FILTER = MENU_TYPES.register("attribute_filter", () -> IMenuTypeExtension.create(AttributeFilterMenu::new));
}
