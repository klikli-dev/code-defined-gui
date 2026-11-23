// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.internal.screen;

import com.klikli_dev.codedefinedgui.api.screen.GuiHost;
import com.klikli_dev.codedefinedgui.api.screen.GuiRootWidget;
import com.klikli_dev.codedefinedgui.api.style.StyleContext;
import com.klikli_dev.codedefinedgui.api.texture.GuiSprites;
import com.klikli_dev.codedefinedgui.api.widget.FrameWidget;
import com.klikli_dev.codedefinedgui.api.widget.GuiBackgroundWidget;
import com.klikli_dev.codedefinedgui.api.widget.GuiSpriteWidget;
import com.klikli_dev.codedefinedgui.api.widget.IconToggleButtonWidget;
import com.klikli_dev.codedefinedgui.api.widget.ProgressBarWidget;
import com.klikli_dev.codedefinedgui.api.widget.ScrollListWidget;
import com.klikli_dev.codedefinedgui.api.widget.ToggleSwitch;
import com.klikli_dev.codedefinedgui.api.widget.WrappedTextWidget;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;

public class TestScreen extends Screen implements GuiHost {
    private final int imageWidth;
    private final int imageHeight;
    private final GuiRootWidget root;
    private int leftPos;
    private int topPos;
    private IconToggleButtonWidget showcaseToggle;
    private ScrollListWidget<String> showcaseList;

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
        GuiSpriteWidget craftingArrow = new GuiSpriteWidget(this.guiX(68), this.guiY(20), GuiSprites.CRAFTING_ARROW);
        FrameWidget beveledArrowFrame = new FrameWidget(craftingArrow).bevel(bevelPrimaryColor, bevelSecondaryColor, bevelCornerColor);

        this.root.addChild(new GuiBackgroundWidget(this));
        this.root.addChild(new GuiSpriteWidget(this.guiX(8), this.guiY(18), GuiSprites.INVENTORY_SLOT));
        this.root.addChild(new GuiSpriteWidget(this.guiX(26), this.guiY(18), GuiSprites.INVENTORY_SLOT));
        this.root.addChild(new GuiSpriteWidget(this.guiX(44), this.guiY(18), GuiSprites.INVENTORY_SLOT));
        this.root.addChild(craftingArrow);
        this.root.addChild(beveledArrowFrame);
        this.root.addChild(new FrameWidget(beveledArrowFrame.getX() - 1, beveledArrowFrame.getY() - 1, beveledArrowFrame.getWidth() + 2, beveledArrowFrame.getHeight() + 2));
        this.root.addChild(new GuiSpriteWidget(this.guiX(94), this.guiY(14), GuiSprites.CRAFTING_RESULT_SLOT));
        this.root.addChild(new FrameWidget(this.guiX(120), this.guiY(18), 40, 24));

        this.root.addChild(new ProgressBarWidget(this.guiX(8), this.guiY(70), 160, 7, () -> 0.6D));

        this.showcaseToggle = this.root.addChild(new IconToggleButtonWidget(this.guiX(8), this.guiY(82), 18, GuiSprites.FILTER_ICON_ALLOW_LIST,
                Component.literal("Toggle: click to select"), () -> this.showcaseToggle.setSelected(!this.showcaseToggle.selected())));
        this.showcaseToggle.setSelected(true);

        this.showcaseList = this.root.addChild(new ScrollListWidget<String>(this.guiX(8), this.guiY(104), 160, 30, 10,
                this::extractShowcaseRow, entry -> {
        }));
        this.showcaseList.setEntries(List.of("Alpha", "Bravo", "Charlie", "Delta", "Echo", "Foxtrot", "Golf", "Hotel"));

        this.root.addChild(new WrappedTextWidget(this.guiX(8), this.guiY(138), 160,
                () -> Component.literal("Wrapped text wraps at the width of the widget and is cut to two lines when it is too long."))
                .withMaxLines(2));

        this.root.syncBoundsToHost();
    }

    @Override
    public void tick() {
        if (this.showcaseList != null) {
            this.showcaseList.tick();
        }
    }

    private void extractShowcaseRow(GuiGraphicsExtractor graphics, String entry, int index, int x, int y, int width, int height, boolean hovered, int textColor) {
        graphics.text(this.font, Component.literal(entry), x, y + (height - 8) / 2, textColor, false);
        ToggleSwitch.extract(graphics, StyleContext.defaults(), x + width - ToggleSwitch.WIDTH, y + (height - ToggleSwitch.HEIGHT) / 2, index % 2 == 0, hovered);
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


