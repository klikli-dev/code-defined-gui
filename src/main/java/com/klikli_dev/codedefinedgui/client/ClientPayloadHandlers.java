// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.client;

import com.klikli_dev.codedefinedgui.gui.TestScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandlers {
    public static void handleOpenTestScreen(com.klikli_dev.codedefinedgui.network.OpenTestScreenPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> Minecraft.getInstance().setScreen(new TestScreen(Component.literal("Test Screen"))));
    }
}
