// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.layout.inventory;

import com.klikli_dev.codedefinedgui.api.layout.BuiltinLayoutSlotRoles;
import com.klikli_dev.codedefinedgui.api.layout.LayoutResolverRegistry;
import com.klikli_dev.codedefinedgui.internal.layout.LayoutScreenRenderer;
import com.klikli_dev.codedefinedgui.internal.layout.LayoutScreenRendererHost;
import com.klikli_dev.codedefinedgui.api.layout.LayoutSlotView;
import com.klikli_dev.codedefinedgui.api.style.BuiltinGuiParts;
import com.klikli_dev.codedefinedgui.api.style.GuiStyleProperties;
import com.klikli_dev.codedefinedgui.api.texture.GuiSprites;
import com.klikli_dev.codedefinedgui.api.widget.GuiBackgroundWidget;
import com.klikli_dev.codedefinedgui.api.widget.GuiSpriteWidget;

public final class PlayerInventoryScreenRenderer implements LayoutScreenRenderer {
    @Override
    public void registerResolvers(LayoutResolverRegistry registry, LayoutScreenRendererHost host) {
        registry.resolve("background", -100, ctx -> ctx.addWidget(new GuiBackgroundWidget(
                host,
                ctx.node().x(),
                ctx.node().y(),
                ctx.node().widthOrThrow(),
                ctx.node().heightOrThrow(),
                ctx.style().get(BuiltinGuiParts.PLAYER_INVENTORY_BACKGROUND, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND)
        )));

        for (LayoutSlotView slotView : host.layoutSlots()) {
            if (!slotView.role().equals(BuiltinLayoutSlotRoles.PLAYER_MAIN) && !slotView.role().equals(BuiltinLayoutSlotRoles.PLAYER_HOTBAR)) {
                continue;
            }

            if (slotView.nodePath() == null || slotView.nodePath().isEmpty()) {
                continue;
            }

            registry.add(slotView.nodePath(), -25, ctx -> ctx.addWidget(new GuiSpriteWidget(
                    slotView.x() - 1 + host.leftPos(),
                    slotView.y() - 1 + host.topPos(),
                    ctx.style().get(slotView.part(), GuiStyleProperties.SPRITE, GuiSprites.INVENTORY_SLOT)
            )));
        }
    }
}

