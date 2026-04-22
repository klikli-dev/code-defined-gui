package com.klikli_dev.codedefinedgui.gui;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import net.minecraft.client.gui.BundleMouseActions;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class TestScreen extends Screen {

    private static final Identifier GUI_BACKGROUND = Identifier.fromNamespaceAndPath(CodeDefinedGui.MODID, "gui_background");
    private final int imageWidth;
    private final int imageHeight;
    private int leftPos;
    private int topPos;

    public TestScreen(Component title) {
        super(title);

        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    protected void init() {
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);

        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, GUI_BACKGROUND, this.leftPos, this.topPos, this.imageWidth, this.imageHeight);
    }
}
