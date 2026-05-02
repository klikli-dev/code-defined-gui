// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.style;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Global registry for GUI style sheets.
 * <p>
 * This is the only style registry in CDG. Slot visuals, backgrounds, buttons,
 * indicators and future widget-specific parts all resolve through the same registry.
 */
public final class GuiStyleRegistry {
    private static final GuiStyle EMPTY_STYLE = GuiStyle.builder().build();
    private static final Map<GuiStyleKey, GuiStyle> STYLES = new ConcurrentHashMap<>();

    private GuiStyleRegistry() {
    }

    public static void register(GuiStyleKey key, GuiStyle style) {
        GuiStyle previous = STYLES.putIfAbsent(key, style);
        if (previous != null) {
            throw new IllegalStateException("A gui style is already registered for key " + key.id());
        }
    }

    public static GuiStyle get(GuiStyleKey key) {
        return STYLES.getOrDefault(key, EMPTY_STYLE);
    }
}
