// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.widget;

import com.klikli_dev.codedefinedgui.gui.core.GuiHost;
import com.klikli_dev.codedefinedgui.gui.core.GuiSyncable;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprite;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprites;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class GuiBackgroundWidget extends AbstractWidget implements GuiSyncable {
    private final GuiHost host;
    private final @Nullable Integer xOverride;
    private final @Nullable Integer yOverride;
    private final @Nullable Integer widthOverride;
    private final @Nullable Integer heightOverride;
    private GuiSprite sprite;

    public GuiBackgroundWidget(GuiHost host) {
        this(host, GuiSprites.GUI_BACKGROUND);
    }

    public GuiBackgroundWidget(GuiHost host, GuiSprite sprite) {
        this(host, null, null, null, null, sprite);
    }

    public GuiBackgroundWidget(GuiHost host, int x, int y, int width, int height) {
        this(host, x, y, width, height, GuiSprites.GUI_BACKGROUND);
    }

    public GuiBackgroundWidget(GuiHost host, int x, int y, int width, int height, GuiSprite sprite) {
        this(host, Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(width), Integer.valueOf(height), sprite);
    }

    private GuiBackgroundWidget(GuiHost host, @Nullable Integer xOverride, @Nullable Integer yOverride, @Nullable Integer widthOverride, @Nullable Integer heightOverride, GuiSprite sprite) {
        super(0, 0, sprite.width(), sprite.height(), Component.empty());
        this.host = host;
        this.xOverride = xOverride;
        this.yOverride = yOverride;
        this.widthOverride = widthOverride;
        this.heightOverride = heightOverride;
        this.sprite = sprite;
        this.active = false;
        this.syncToHost();
    }

    public GuiBackgroundWidget withSprite(GuiSprite sprite) {
        this.sprite = sprite;
        return this;
    }

    @Override
    protected void extractWidgetRenderState(@NonNull GuiGraphicsExtractor guiGraphicsExtractor, int mouseX, int mouseY, float partialTick) {
        this.sprite.extractRenderState(guiGraphicsExtractor, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
    }

    @Override
    public void syncToHost() {
        this.setX(this.xOverride != null ? this.xOverride : this.host.leftPos());
        this.setY(this.yOverride != null ? this.yOverride : this.host.topPos());
        this.setWidth(this.widthOverride != null ? this.widthOverride : this.host.imageWidth());
        this.setHeight(this.heightOverride != null ? this.heightOverride : this.host.imageHeight());
    }
}
