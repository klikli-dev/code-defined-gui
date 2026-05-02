// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.style;

import java.util.LinkedHashMap;
import java.util.Map;

public interface GuiStyle {
    <T> T get(GuiPartKey part, GuiStyleProperty<T> property, T fallback);

    static Builder builder() {
        return new Builder();
    }

    final class Builder {
        private final Map<GuiPartKey, Map<GuiStyleProperty<?>, Object>> values = new LinkedHashMap<>();

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
