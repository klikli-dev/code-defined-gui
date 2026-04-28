// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.codedefinedgui.command;

import com.klikli_dev.codedefinedgui.example.network.OpenTestScreenMessage;
import com.klikli_dev.codedefinedgui.infrastructure.network.Networking;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public final class CdgCommand {
    private CdgCommand() {
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(root());
    }

    private static LiteralArgumentBuilder<CommandSourceStack> root() {
        return Commands.literal("cdg")
                .then(show());
    }

    private static LiteralArgumentBuilder<CommandSourceStack> show() {
        return Commands.literal("show")
                .then(Commands.literal("testscreen")
                        .executes(context -> showTestScreen(context.getSource())));
    }

    private static int showTestScreen(CommandSourceStack source) {
        if (!(source.getEntity() instanceof ServerPlayer player)) {
            source.sendFailure(Component.literal("This command can only be used by a player."));
            return 0;
        }

        Networking.sendTo(player, new OpenTestScreenMessage());
        source.sendSuccess(() -> Component.literal("Opening test screen."), false);
        return 1;
    }
}
