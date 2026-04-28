// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.widget;

import com.klikli_dev.codedefinedgui.gui.core.GuiHost;
import com.klikli_dev.codedefinedgui.gui.texture.GuiTextures;
import org.jspecify.annotations.Nullable;

public class GuiBackgroundWidget extends TextureWidget {
    private final GuiHost host;
    private final @Nullable Integer xOverride;
    private final @Nullable Integer yOverride;
    private final @Nullable Integer widthOverride;
    private final @Nullable Integer heightOverride;

    public GuiBackgroundWidget(GuiHost host) {
        this(host, null, null, null, null);
    }

    public GuiBackgroundWidget(GuiHost host, int x, int y, int width, int height) {
        this(host, Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(width), Integer.valueOf(height));
    }

    private GuiBackgroundWidget(GuiHost host, @Nullable Integer xOverride, @Nullable Integer yOverride, @Nullable Integer widthOverride, @Nullable Integer heightOverride) {
        super(0, 0, GuiTextures.GUI_BACKGROUND);
        this.host = host;
        this.xOverride = xOverride;
        this.yOverride = yOverride;
        this.widthOverride = widthOverride;
        this.heightOverride = heightOverride;
        this.syncToHost();
    }

    @Override
    public void syncToHost() {
        this.setX(this.xOverride != null ? this.xOverride : this.host.leftPos());
        this.setY(this.yOverride != null ? this.yOverride : this.host.topPos());
        this.setWidth(this.widthOverride != null ? this.widthOverride : this.host.imageWidth());
        this.setHeight(this.heightOverride != null ? this.heightOverride : this.host.imageHeight());
    }
}
