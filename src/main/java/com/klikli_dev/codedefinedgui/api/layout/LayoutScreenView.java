// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.api.layout;

public interface LayoutScreenView {
    LayoutSpec layoutSpec();
    void registerResolvers(LayoutResolverRegistry registry);
}
