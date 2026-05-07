// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.api.layout;

import java.util.function.Consumer;

public interface LayoutResolverRegistry {
    void resolve(String id, Consumer<LayoutResolveContext> resolver);

    void resolve(String id, int priority, Consumer<LayoutResolveContext> resolver);

    void add(String id, Consumer<LayoutResolveContext> resolver);

    void add(String id, int priority, Consumer<LayoutResolveContext> resolver);

    LayoutResolverRegistry scope(String id);
}


