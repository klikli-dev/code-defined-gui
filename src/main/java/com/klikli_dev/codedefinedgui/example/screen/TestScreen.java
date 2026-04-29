// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.example.screen;

import com.klikli_dev.codedefinedgui.gui.core.GuiHost;
import com.klikli_dev.codedefinedgui.gui.core.GuiRootWidget;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprites;
import com.klikli_dev.codedefinedgui.gui.widget.FrameWidget;
import com.klikli_dev.codedefinedgui.gui.widget.GuiBackgroundWidget;
import com.klikli_dev.codedefinedgui.gui.widget.TextureWidget;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class TestScreen extends Screen implements GuiHost {
    private final int imageWidth;
    private final int imageHeight;
    private final GuiRootWidget root;
    private int leftPos;
    private int topPos;

    public TestScreen(Component title) {
        super(title);

        this.imageWidth = 176;
        this.imageHeight = 166;
        this.root = new GuiRootWidget(this);
    }

    @Override
    protected void init() {
        final int bevelCornerColor = 0xFFC2C2C2;
        final int bevelPrimaryColor = 0xFFAAAAAA;
        final int bevelSecondaryColor = 0xFFEAEAEA;

        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        this.addRenderableWidget(this.root);

        this.root.clearChildren();
        TextureWidget craftingArrow = new TextureWidget(this.leftPos + 68, this.topPos + 20, GuiSprites.CRAFTING_ARROW);
        FrameWidget beveledArrowFrame = new FrameWidget(craftingArrow).bevel(bevelPrimaryColor, bevelSecondaryColor, bevelCornerColor);

        this.root.addChild(new GuiBackgroundWidget(this));
        this.root.addChild(new TextureWidget(this.leftPos + 8, this.topPos + 18, GuiSprites.INVENTORY_SLOT));
        this.root.addChild(new TextureWidget(this.leftPos + 26, this.topPos + 18, GuiSprites.INVENTORY_SLOT));
        this.root.addChild(new TextureWidget(this.leftPos + 44, this.topPos + 18, GuiSprites.INVENTORY_SLOT));
        this.root.addChild(craftingArrow);
        this.root.addChild(beveledArrowFrame);
        this.root.addChild(new FrameWidget(beveledArrowFrame.getX() - 1, beveledArrowFrame.getY() - 1, beveledArrowFrame.getWidth() + 2, beveledArrowFrame.getHeight() + 2));
        this.root.addChild(new TextureWidget(this.leftPos + 94, this.topPos + 14, GuiSprites.CRAFTING_RESULT_SLOT));
        this.root.addChild(new FrameWidget(this.leftPos + 120, this.topPos + 18, 40, 24));

        this.root.syncBoundsToHost();
    }

    @Override
    public int leftPos() {
        return this.leftPos;
    }

    @Override
    public int topPos() {
        return this.topPos;
    }

    @Override
    public int width() {
        return this.width;
    }

    @Override
    public int height() {
        return this.height;
    }

    @Override
    public int imageWidth() {
        return this.imageWidth;
    }

    @Override
    public int imageHeight() {
        return this.imageHeight;
    }

    @Override
    public <T extends AbstractWidget> T addGuiWidget(T widget) {
        return this.addRenderableWidget(widget);
    }

    @Override
    public void removeGuiWidget(AbstractWidget widget) {
        this.removeWidget(widget);
    }
}
