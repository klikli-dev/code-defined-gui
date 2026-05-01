// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui;

public class CodeDefinedGuiConstants {
    private CodeDefinedGuiConstants() {
    }

    public static class I18n {
        public static class Tooltip {
            private static final String PREFIX = "tooltip." + CodeDefinedGui.MODID + ".";

            public static final String SHOW_EXTENDED = PREFIX + "show_extended";
        }

        public static class Filter {
            private static final String PREFIX = CodeDefinedGui.MODID + ".filter.";

            public static final String SUMMARY_MORE = PREFIX + "summary.more";

            public static class Button {
                private static final String PREFIX = Filter.PREFIX + "button.";

                public static final String RESET = PREFIX + "reset";
                public static final String RESET_TOOLTIP = PREFIX + "reset.tooltip";
                public static final String DONE = PREFIX + "done";
                public static final String DONE_TOOLTIP = PREFIX + "done.tooltip";
            }

            public static class List {
                private static final String PREFIX = Filter.PREFIX + "list.";
                private static final String MODE_PREFIX = PREFIX + "mode.";
                private static final String SUMMARY_PREFIX = PREFIX + "summary.";

                public static final String RESPECT_DATA = PREFIX + "respect_data";
                public static final String RESPECT_DATA_TOOLTIP = RESPECT_DATA + ".tooltip";
                public static final String RESPECT_DATA_TOOLTIP_SHIFT = RESPECT_DATA_TOOLTIP + ".shift";
                public static final String IGNORE_DATA = PREFIX + "ignore_data";
                public static final String IGNORE_DATA_TOOLTIP = IGNORE_DATA + ".tooltip";
                public static final String IGNORE_DATA_TOOLTIP_SHIFT = IGNORE_DATA_TOOLTIP + ".shift";
                public static final String SUMMARY_MODE = SUMMARY_PREFIX + "mode";
                public static final String SUMMARY_RESPECT_DATA = SUMMARY_PREFIX + "respect_data";
                public static final String SUMMARY_IGNORE_DATA = SUMMARY_PREFIX + "ignore_data";

                public static class Mode {
                    public static final String ALLOW = MODE_PREFIX + "allow";
                    public static final String ALLOW_TOOLTIP = ALLOW + ".tooltip";
                    public static final String ALLOW_TOOLTIP_SHIFT = ALLOW_TOOLTIP + ".shift";
                    public static final String DENY = MODE_PREFIX + "deny";
                    public static final String DENY_TOOLTIP = DENY + ".tooltip";
                    public static final String DENY_TOOLTIP_SHIFT = DENY_TOOLTIP + ".shift";

                    public static String key(String mode) {
                        return MODE_PREFIX + mode;
                    }
                }
            }

            public static class Attribute {
                private static final String PREFIX = Filter.PREFIX + "attribute.";
                private static final String SUMMARY_PREFIX = PREFIX + "summary.";
                private static final String STANDARD_PREFIX = PREFIX + "standard.";

                public static final String AVAILABLE = PREFIX + "available";
                public static final String ADD = PREFIX + "add";
                public static final String ADD_TOOLTIP = ADD + ".tooltip";
                public static final String ADD_INVERTED = PREFIX + "add_inverted";
                public static final String ADD_INVERTED_TOOLTIP = ADD_INVERTED + ".tooltip";
                public static final String NO_REFERENCE = PREFIX + "no_reference";
                public static final String NO_RULES = PREFIX + "no_rules";
                public static final String SCROLL_TO_SELECT = PREFIX + "scroll_to_select";
                public static final String INVERTED = PREFIX + "inverted";
                public static final String IN_TAG = PREFIX + "in_tag";
                public static final String ADDED_BY = PREFIX + "added_by";
                public static final String HAS_ENCHANTMENT = PREFIX + "has_enchantment";
                public static final String HAS_FLUID = PREFIX + "has_fluid";
                public static final String HAS_NAME = PREFIX + "has_name";
                public static final String SUMMARY_MODE = SUMMARY_PREFIX + "mode";

                public static class Mode {
                    private static final String PREFIX = Attribute.PREFIX + "mode.";

                    public static final String MATCH_ANY = PREFIX + "match_any";
                    public static final String MATCH_ANY_TOOLTIP = MATCH_ANY + ".tooltip";
                    public static final String MATCH_ANY_TOOLTIP_SHIFT = MATCH_ANY_TOOLTIP + ".shift";
                    public static final String MATCH_ALL = PREFIX + "match_all";
                    public static final String MATCH_ALL_TOOLTIP = MATCH_ALL + ".tooltip";
                    public static final String MATCH_ALL_TOOLTIP_SHIFT = MATCH_ALL_TOOLTIP + ".shift";
                    public static final String DENY = PREFIX + "deny";
                    public static final String DENY_TOOLTIP = DENY + ".tooltip";
                    public static final String DENY_TOOLTIP_SHIFT = DENY_TOOLTIP + ".shift";

                    public static String key(String mode) {
                        return PREFIX + mode;
                    }
                }

                public static class Standard {
                    public static String key(String value) {
                        return STANDARD_PREFIX + value;
                    }
                }
            }
        }
    }
}
