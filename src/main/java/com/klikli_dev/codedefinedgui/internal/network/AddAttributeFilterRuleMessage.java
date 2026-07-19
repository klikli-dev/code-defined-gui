// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.internal.network;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import com.klikli_dev.codedefinedgui.premade.filter.attribute.AttributeFilterMenu;
import com.klikli_dev.codedefinedgui.premade.filter.attribute.AttributeRule;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public record AddAttributeFilterRuleMessage(int containerId, AttributeRule rule, boolean inverted) implements Message {
    public static final Type<AddAttributeFilterRuleMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(CodeDefinedGui.MODID, "add_attribute_filter_rule"));
    public static final StreamCodec<RegistryFriendlyByteBuf, AddAttributeFilterRuleMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            AddAttributeFilterRuleMessage::containerId,
            AttributeRule.STREAM_CODEC,
            AddAttributeFilterRuleMessage::rule,
            ByteBufCodecs.BOOL,
            AddAttributeFilterRuleMessage::inverted,
            AddAttributeFilterRuleMessage::new
    );

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player) {
        if (player.containerMenu.containerId == this.containerId && player.containerMenu instanceof AttributeFilterMenu menu) {
            menu.addSelectedRule(this.rule, this.inverted);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
