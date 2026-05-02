// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.style;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import net.minecraft.resources.Identifier;

public final class BuiltinGuiStyles {
    public static final GuiStyleKey DEFAULT = key("default");

    private BuiltinGuiStyles() {
    }

    private static GuiStyleKey key(String path) {
        return GuiStyleKey.of(Identifier.fromNamespaceAndPath(CodeDefinedGui.MODID, path));
    }
}
