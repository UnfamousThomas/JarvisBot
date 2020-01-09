package com.discordapp.JarvisBot.commands.admin;

import com.discordapp.JarvisBot.commands.api.Command;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class AppealCommand extends Command {
    public AppealCommand() {
        super("appeal");
    }

    @Override
    public void run(Member m, List<String> args, MessageReceivedEvent event) {

    }
}
