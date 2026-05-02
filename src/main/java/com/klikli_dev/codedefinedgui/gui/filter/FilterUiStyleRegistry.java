// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.filter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class FilterUiStyleRegistry {
    private static final FilterUiStyle DEFAULT_STYLE = new FilterUiStyle() {
    };
    private static final Map<FilterUiStyleKey, FilterUiStyle> STYLES = new ConcurrentHashMap<>();

    static {
        register(BuiltinFilterUiStyles.DEFAULT, DEFAULT_STYLE);
    }

    private FilterUiStyleRegistry() {
    }

    public static void register(FilterUiStyleKey key, FilterUiStyle style) {
        FilterUiStyle previous = STYLES.putIfAbsent(key, style);
        if (previous != null) {
            throw new IllegalStateException("A filter UI style is already registered for key " + key.id());
        }
    }

    public static FilterUiStyle get(FilterUiStyleKey key) {
        return STYLES.getOrDefault(key, DEFAULT_STYLE);
    }
}
