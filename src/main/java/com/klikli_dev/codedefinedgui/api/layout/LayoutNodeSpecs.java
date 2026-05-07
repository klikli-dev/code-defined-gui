// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.api.layout;

final class LayoutNodeSpecs {
    private LayoutNodeSpecs() {
    }

    static LayoutNodeSpec findRelative(LayoutNodeSpec scope, String id) {
        if (id.isEmpty()) {
            return scope;
        }

        LayoutNodeSpec root = root(scope);
        String current = scope.path();
        while (true) {
            String candidate = current.isEmpty() ? id : current + "." + id;
            LayoutNodeSpec match = findAbsolute(root, candidate);
            if (match != null) {
                return match;
            }

            if (current.isEmpty()) {
                break;
            }

            int separator = current.lastIndexOf('.');
            current = separator >= 0 ? current.substring(0, separator) : "";
        }

        throw new IllegalArgumentException("Unknown layout node '" + id + "' in scope '" + scope.path() + "'");
    }

    private static LayoutNodeSpec root(LayoutNodeSpec spec) {
        LayoutNodeSpec current = spec;
        while (current.parent != null) {
            current = current.parent;
        }

        return current;
    }

    private static LayoutNodeSpec findAbsolute(LayoutNodeSpec root, String path) {
        if (path.isEmpty()) {
            return root;
        }

        LayoutNodeSpec current = root;
        for (String segment : path.split("\\.")) {
            LayoutNodeSpec child = child(current, segment);
            if (child == null) {
                return null;
            }

            current = child;
        }

        return current;
    }

    private static LayoutNodeSpec child(LayoutNodeSpec parent, String id) {
        for (LayoutNodeSpec child : parent.children) {
            if (child.id.equals(id)) {
                return child;
            }
        }

        return null;
    }
}
