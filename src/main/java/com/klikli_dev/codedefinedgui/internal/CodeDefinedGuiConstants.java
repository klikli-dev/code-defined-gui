// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.internal;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;

public class CodeDefinedGuiConstants {
    private CodeDefinedGuiConstants() {
    }

    public static class Config {
        public static final String TITLE = CodeDefinedGui.MODID + ".configuration.title";
    }

    public static class ItemGroup {
        public static final String MAIN = "itemGroup." + CodeDefinedGui.MODID;
    }

    public static class I18n {
        public static class Tooltip {
            private static final String PREFIX = "tooltip." + CodeDefinedGui.MODID + ".";

            public static final String SHOW_EXTENDED = PREFIX + "show_extended";
        }
    }
}


