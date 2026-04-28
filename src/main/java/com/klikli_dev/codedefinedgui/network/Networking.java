// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.network;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import com.klikli_dev.codedefinedgui.network.message.OpenTestScreenMessage;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public final class Networking {
    private Networking() {
    }

    public static void register(RegisterPayloadHandlersEvent event) {
        event.registrar(CodeDefinedGui.MODID)
                .playToClient(OpenTestScreenMessage.TYPE, OpenTestScreenMessage.STREAM_CODEC, MessageHandler::handle);
    }

    public static <T extends Message> void sendTo(ServerPlayer player, T message) {
        PacketDistributor.sendToPlayer(player, message);
    }
}
