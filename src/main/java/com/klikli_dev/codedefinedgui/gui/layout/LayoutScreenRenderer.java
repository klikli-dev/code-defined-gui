// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.internal.layout;

import com.klikli_dev.codedefinedgui.api.layout.LayoutResolverRegistry;

public interface LayoutScreenRenderer {
    void registerResolvers(LayoutResolverRegistry registry, LayoutScreenRendererHost host);
}
