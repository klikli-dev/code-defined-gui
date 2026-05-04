// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.filter.widget;

import com.klikli_dev.codedefinedgui.CodeDefinedGuiConstants;
import com.klikli_dev.codedefinedgui.filter.attribute.AttributeCandidate;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprite;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprites;
import com.klikli_dev.codedefinedgui.gui.widget.AbstractScrollSelectionWidget;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;

public class AttributeSelectionWidget extends AbstractScrollSelectionWidget<AttributeCandidate> {

    public AttributeSelectionWidget(int x, int y, int width, int height, Supplier<List<AttributeCandidate>> candidates, IntSupplier selectedIndex, IntConsumer onChange) {
        this(x, y, width, height, GuiSprites.ATTRIBUTE_FILTER_SELECTION, candidates, selectedIndex, onChange);
    }

    public AttributeSelectionWidget(int x, int y, int width, int height, GuiSprite sprite, Supplier<List<AttributeCandidate>> candidates, IntSupplier selectedIndex, IntConsumer onChange) {
        super(x, y, width, height, sprite, candidates, selectedIndex, onChange);
    }

    public AttributeSelectionWidget withTitle(Component title) {
        super.withTitle(title);
        return this;
    }

    @Override
    protected Component entryLabel(AttributeCandidate entry) {
        return entry.label();
    }

    @Override
    protected Component emptyEntriesText() {
        return Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.NO_REFERENCE);
    }

    @Override
    protected Component scrollHintText() {
        return Component.translatable(CodeDefinedGuiConstants.I18n.Filter.Attribute.SCROLL_TO_SELECT);
    }
}
