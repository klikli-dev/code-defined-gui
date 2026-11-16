// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.internal.network;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import com.klikli_dev.codedefinedgui.premade.filter.core.FilterMenu;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public record SetFilterGhostSlotMessage(int containerId, int slotId, ItemStack stack) implements Message {
    public static final Type<SetFilterGhostSlotMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(CodeDefinedGui.MODID, "set_filter_ghost_slot"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SetFilterGhostSlotMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            SetFilterGhostSlotMessage::containerId,
            ByteBufCodecs.VAR_INT,
            SetFilterGhostSlotMessage::slotId,
            ItemStack.OPTIONAL_STREAM_CODEC,
            SetFilterGhostSlotMessage::stack,
            SetFilterGhostSlotMessage::new
    );

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player) {
        if (player.containerMenu.containerId == this.containerId && player.containerMenu instanceof FilterMenu menu && menu.stillValid(player)) {
            menu.setGhostStackFromSlot(this.slotId, this.stack);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
