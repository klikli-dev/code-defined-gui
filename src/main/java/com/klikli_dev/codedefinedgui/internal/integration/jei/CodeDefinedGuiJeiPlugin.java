// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.internal.integration.jei;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import com.klikli_dev.codedefinedgui.premade.filter.AbstractFilterScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import net.minecraft.resources.Identifier;

@JeiPlugin
public class CodeDefinedGuiJeiPlugin implements IModPlugin {
    @Override
    public Identifier getPluginUid() {
        return Identifier.fromNamespaceAndPath(CodeDefinedGui.MODID, "jei");
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addGhostIngredientHandler(AbstractFilterScreen.class, new FilterGhostIngredientHandler());
    }
}
