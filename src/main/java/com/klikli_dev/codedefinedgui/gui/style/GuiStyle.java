// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.style;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Generic GUI style sheet.
 * <p>
 * A style is just data: a mapping from {@link GuiPartKey} and {@link GuiStyleProperty}
 * to a concrete value. Widgets, screens and menu slot views decide which part keys they use,
 * while the style sheet only answers how those parts should look.
 * <p>
 * This keeps styling extensible:
 * <ul>
 *     <li>new premade GUIs add new {@link GuiPartKey}s, not new registries</li>
 *     <li>new widgets can define their own part keys and properties</li>
 *     <li>mods can swap visuals without subclassing screens or menus</li>
 * </ul>
 */
public interface GuiStyle {
    <T> T get(GuiPartKey part, GuiStyleProperty<T> property, T fallback);

    static Builder builder() {
        return new Builder();
    }

    final class Builder {
        private final Map<GuiPartKey, Map<GuiStyleProperty<?>, Object>> values = new LinkedHashMap<>();

        /**
         * Sets a concrete property value for a part.
         * <p>
         * Example: tint the player inventory background and slots for a style:
         * <pre>{@code
         * GuiStyle.builder()
         *     .set(BuiltinFilterParts.PLAYER_INVENTORY_BACKGROUND, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(0xFFC2AA88))
         *     .set(BuiltinFilterParts.PLAYER_SLOT, GuiStyleProperties.SPRITE, GuiSprites.INVENTORY_SLOT.tinted(0xFFB8946A))
         *     .set(BuiltinFilterParts.PLAYER_SLOT, GuiStyleProperties.OFFSET_X, 1)
         *     .set(BuiltinFilterParts.PLAYER_SLOT, GuiStyleProperties.OFFSET_Y, 1)
         *     .build();
         * }</pre>
         */
        public <T> Builder set(GuiPartKey part, GuiStyleProperty<T> property, T value) {
            this.values.computeIfAbsent(part, ignored -> new LinkedHashMap<>()).put(property, value);
            return this;
        }

        public GuiStyle build() {
            Map<GuiPartKey, Map<GuiStyleProperty<?>, Object>> styleValues = new LinkedHashMap<>();
            for (Map.Entry<GuiPartKey, Map<GuiStyleProperty<?>, Object>> entry : this.values.entrySet()) {
                styleValues.put(entry.getKey(), Map.copyOf(entry.getValue()));
            }

            Map<GuiPartKey, Map<GuiStyleProperty<?>, Object>> immutableValues = Map.copyOf(styleValues);
            return new GuiStyle() {
                @Override
                @SuppressWarnings("unchecked")
                public <T> T get(GuiPartKey part, GuiStyleProperty<T> property, T fallback) {
                    Map<GuiStyleProperty<?>, Object> partValues = immutableValues.get(part);
                    if (partValues == null || !partValues.containsKey(property)) {
                        return fallback;
                    }

                    return (T) partValues.get(property);
                }
            };
        }
    }
}
