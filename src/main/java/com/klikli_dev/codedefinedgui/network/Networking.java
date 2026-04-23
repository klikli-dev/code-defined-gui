// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.network;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public final class Networking {
    private Networking() {
    }

    public static void register(RegisterPayloadHandlersEvent event) {
        event.registrar(CodeDefinedGui.MODID)
                .playToClient(OpenTestScreenPayload.TYPE, OpenTestScreenPayload.STREAM_CODEC);
    }

    public static void sendTo(ServerPlayer player, OpenTestScreenPayload payload) {
        PacketDistributor.sendToPlayer(player, payload);
    }
}
