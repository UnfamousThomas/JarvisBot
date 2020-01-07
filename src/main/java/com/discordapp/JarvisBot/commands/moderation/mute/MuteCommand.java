package com.discordapp.JarvisBot.commands.moderation.mute;

import com.discordapp.JarvisBot.commands.api.Categories;
import com.discordapp.JarvisBot.commands.api.Command;
import com.discordapp.JarvisBot.utils.CustomPermission;
import com.discordapp.JarvisBot.utils.DataFields;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MuteCommand extends Command {

    public MuteCommand() {
        super("mute");
        maxArgs = 2;
        description = "A command to mute a person | If no args specified, mutes the person for 10 minutes.";
        usage = "`" + DataFields.prefix + "mute (person) (time)`";
        category = Categories.MODERATE;
        permission = CustomPermission.MODERATOR;
    }

    @Override
    public void run(Member m, List<String> args, MessageReceivedEvent event) {
        Guild guild = event.getGuild();
        MessageChannel channel = event.getChannel();
        Member staff = event.getMember();
        if (args.size() < 1) {
            channel.sendMessage("You don't have enough args!").queue(message -> message.delete().queueAfter(1, TimeUnit.MINUTES));
        } else {
            Member member = guild.getMemberById(Long.parseLong(args.get(0).replace("<@!", "").replace(">", "")));
            long time = args.size() == 2 ? stringToTime(args.get(1)) : 10 * 60 * 1000;
            if (member == null) {
                channel.sendMessage("Member is not founded!").complete();
                return;
            }
            MuteManager muteManager = new MuteManager(member);
            if (muteManager.isMuted()) {
                muteManager.unmuteMember();
            } else {
                muteManager.muteMember(new Mute("You're breaking the server rules.", staff, System.currentTimeMillis(), time, event.getGuild()));
            }
        }
    }

    private long stringToTime(String string) {
        String[] split;
        if (string.endsWith("d")) {
            split = string.split("d");
            return Long.parseLong(split[0]) * 86400000;
        } else if (string.endsWith("h")) {
            split = string.split("h");
            return Long.parseLong(split[0]) * 3600000;
        } else if (string.endsWith("m")) {
            split = string.split("m");
            return Long.parseLong(split[0]) * 60000;
        } else if (string.endsWith("s")) {
            split = string.split("s");
            return Long.parseLong(split[0]) * 1000;
        }
        return 10 * 60 * 1000;
    }
}
