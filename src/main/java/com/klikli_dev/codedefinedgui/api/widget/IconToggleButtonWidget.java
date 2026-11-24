// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.api.widget;

import com.klikli_dev.codedefinedgui.api.style.BuiltinGuiParts;
import com.klikli_dev.codedefinedgui.api.style.GuiStyleProperties;
import com.klikli_dev.codedefinedgui.api.style.StyleContext;
import com.klikli_dev.codedefinedgui.api.texture.GuiSprite;
import com.klikli_dev.codedefinedgui.api.texture.GuiSprites;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

/**
 * An {@link IconButtonWidget} that stays pressed while it is selected, e.g. one of several modes.
 * <p>
 * Its look follows its state, in this order: disabled, selected, hovered, normal. The owner sets the selected flag, the enabled flag and
 * the tooltip, usually from its tick, because the state can change without the player touching the button.
 * <p>
 * Styled with {@link BuiltinGuiParts#ICON_TOGGLE_BUTTON}: {@code SPRITE}, {@code HOVER_SPRITE}, {@code ON_SPRITE} (selected) and
 * {@code DISABLED_SPRITE}.
 */
public class IconToggleButtonWidget extends IconButtonWidget {
    private static final int ICON_SIZE = 16;

    private StyleContext style = StyleContext.defaults();
    private boolean selected;

    /**
     * @param x left edge
     * @param y top edge
     * @param size width and height in pixels
     * @param icon the icon, drawn at 16x16 and centred, whatever its size
     * @param tooltip the initial tooltip
     * @param onPress run when the button is clicked while it is active
     */
    public IconToggleButtonWidget(int x, int y, int size, GuiSprite icon, Component tooltip, Runnable onPress) {
        super(x, y, size, size, icon.sized(ICON_SIZE, ICON_SIZE), IconButtonBackgroundSprites.DEFAULT, tooltip, onPress);
        this.setTooltip(Tooltip.create(tooltip));
    }

    public IconToggleButtonWidget withStyle(StyleContext style) {
        this.style = style;
        return this;
    }

    /**
     * @param selected whether the button is the current choice. A selected button draws the selected sprite.
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean selected() {
        return this.selected;
    }

    /**
     * Replaces the tooltip. Creates a new tooltip object, so call it only when the text changes.
     */
    public void setTooltipText(Component tooltip) {
        this.setTooltip(Tooltip.create(tooltip));
    }

    @Override
    protected GuiSprite backgroundSprite(int mouseX, int mouseY) {
        if (!this.active) {
            return this.style.get(BuiltinGuiParts.ICON_TOGGLE_BUTTON, GuiStyleProperties.DISABLED_SPRITE, GuiSprites.FILTER_BUTTON.tinted(WidgetDefaults.DISABLED_TINT));
        }

        if (this.selected) {
            return this.style.onSprite(BuiltinGuiParts.ICON_TOGGLE_BUTTON, GuiSprites.FILTER_BUTTON_DOWN);
        }

        if (this.isMouseOver(mouseX, mouseY)) {
            return this.style.hoverSprite(BuiltinGuiParts.ICON_TOGGLE_BUTTON, GuiSprites.FILTER_BUTTON_HOVER);
        }

        return this.style.sprite(BuiltinGuiParts.ICON_TOGGLE_BUTTON, GuiSprites.FILTER_BUTTON);
    }

    @Override
    public void onClick(MouseButtonEvent event, boolean doubleClick) {
        WidgetDefaults.playClickSound();
        super.onClick(event, doubleClick);
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (this.active && event.isSelection()) {
            WidgetDefaults.playClickSound();
        }

        return super.keyPressed(event);
    }
}


