// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.gui.filter;

import com.klikli_dev.codedefinedgui.filter.core.layout.BuiltinSlotSkins;
import com.klikli_dev.codedefinedgui.filter.core.layout.SlotSkinKey;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprites;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class SlotSkinRendererRegistry {
    private static final SlotSkinRenderer DEFAULT_RENDERER = SlotSkinRenderer.create(GuiSprites.INVENTORY_SLOT, 1, 1);
    private static final Map<SlotSkinKey, SlotSkinRenderer> RENDERERS = new ConcurrentHashMap<>();

    static {
        register(BuiltinSlotSkins.PLAYER_INVENTORY, SlotSkinRenderer.create(GuiSprites.INVENTORY_SLOT, 1, 1));
        register(BuiltinSlotSkins.FILTER, SlotSkinRenderer.create(GuiSprites.INVENTORY_SLOT, 1, 1));
    }

    private SlotSkinRendererRegistry() {
    }

    public static void register(SlotSkinKey key, SlotSkinRenderer renderer) {
        SlotSkinRenderer previous = RENDERERS.putIfAbsent(key, renderer);
        if (previous != null) {
            throw new IllegalStateException("A slot skin renderer is already registered for key " + key.id());
        }
    }

    public static SlotSkinRenderer get(SlotSkinKey key) {
        return RENDERERS.getOrDefault(key, DEFAULT_RENDERER);
    }
}
