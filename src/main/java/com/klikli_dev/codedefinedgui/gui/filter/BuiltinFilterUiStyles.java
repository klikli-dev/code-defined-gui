// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.filter;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import net.minecraft.resources.Identifier;

public final class BuiltinFilterUiStyles {
    public static final FilterUiStyleKey DEFAULT = key("default");

    private BuiltinFilterUiStyles() {
    }

    private static FilterUiStyleKey key(String path) {
        return FilterUiStyleKey.of(Identifier.fromNamespaceAndPath(CodeDefinedGui.MODID, path));
    }
}
