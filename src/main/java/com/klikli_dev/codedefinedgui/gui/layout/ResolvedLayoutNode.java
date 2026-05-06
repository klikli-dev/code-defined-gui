// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.layout;

import org.jspecify.annotations.Nullable;

public record ResolvedLayoutNode(String id, String path, int x, int y, @Nullable Integer width,
                                 @Nullable Integer height) implements LayoutNodeView {
}
