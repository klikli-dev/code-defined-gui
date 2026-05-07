// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.api.layout;

final class LayoutResolvers {
    private LayoutResolvers() {
    }

    static ResolvedLayoutNode findRelative(ResolvedLayout layout, String scope, String id) {
        if (id.isEmpty()) {
            return layout.node(scope);
        }

        String current = scope;
        while (true) {
            String candidate = current == null || current.isEmpty() ? id : current + "." + id;
            try {
                return layout.node(candidate);
            } catch (IllegalArgumentException ignored) {
            }

            if (current == null || current.isEmpty()) {
                break;
            }

            int separator = current.lastIndexOf('.');
            current = separator >= 0 ? current.substring(0, separator) : "";
        }

        throw new IllegalArgumentException("Unknown layout node '" + id + "' in scope '" + scope + "'");
    }
}
