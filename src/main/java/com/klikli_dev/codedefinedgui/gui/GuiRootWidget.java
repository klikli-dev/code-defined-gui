// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public class GuiRootWidget extends AbstractWidget {
    private final GuiHost host;
    private final Set<AbstractWidget> children = new LinkedHashSet<>();
    private final Set<AbstractWidget> registeredChildren = new LinkedHashSet<>();

    public GuiRootWidget(GuiHost host) {
        super(0, 0, host.width(), host.height(), Component.empty());
        this.host = host;
        this.active = false;
    }

    public <T extends AbstractWidget> T addChild(T child) {
        if (this.children.add(child)) {
            this.syncChild(child);
            this.registerChild(child);
        }

        return child;
    }

    public void removeChild(AbstractWidget child) {
        if (this.children.remove(child) && this.registeredChildren.remove(child)) {
            this.host.removeGuiWidget(child);
        }
    }

    public void clearChildren() {
        for (AbstractWidget child : Set.copyOf(this.children)) {
            this.removeChild(child);
        }
    }

    public void syncWithHost() {
        this.setX(0);
        this.setY(0);
        this.setWidth(this.host.width());
        this.setHeight(this.host.height());

        for (AbstractWidget child : this.children) {
            this.syncChild(child);
        }

        for (AbstractWidget child : this.registeredChildren) {
            this.host.removeGuiWidget(child);
        }

        this.registeredChildren.clear();

        for (AbstractWidget child : this.children) {
            this.registerChild(child);
        }
    }

    public Collection<AbstractWidget> children() {
        return Collections.unmodifiableSet(this.children);
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
    }

    private void registerChild(AbstractWidget child) {
        this.host.addGuiWidget(child);
        this.registeredChildren.add(child);
    }

    private void syncChild(AbstractWidget child) {
        if (child instanceof GuiSyncable syncable) {
            syncable.syncToHost();
        }
    }
}
