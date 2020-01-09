package com.discordapp.JarvisBot.commands.useful.info;

import com.discordapp.JarvisBot.commands.api.Command;
import com.discordapp.JarvisBot.utils.CustomPermission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class PunishmentInfo extends Command {
    public PunishmentInfo() {
        super("punishmentinfo");
        aliases = alias("puninfo");
        minArgs = 1;
        maxArgs = 1;
        permission = CustomPermission.MODERATOR;

    }

    @Override
    public void run(Member m, List<String> args, MessageReceivedEvent event) {

    }
}
