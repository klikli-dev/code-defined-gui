// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.api.layout;

import java.util.function.Consumer;

public interface MenuBindingRegistry {
    void bind(String id, Consumer<MenuBindingContext> binding);

    void add(String id, Consumer<MenuBindingContext> binding);

    MenuBindingRegistry scope(String id);
}
