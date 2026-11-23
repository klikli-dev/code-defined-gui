// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.api.screen.page;

import com.klikli_dev.codedefinedgui.api.widget.tab.TabIcon;

/**
 * The root page of a tab in a {@link TabHost}. Its {@link #title()} is shown as the tab tooltip.
 * Detail pages pushed from a tab are popped back to this page.
 */
public interface Tab extends Page {

    /**
     * @return the icon drawn on the tab button
     */
    TabIcon icon();
}
