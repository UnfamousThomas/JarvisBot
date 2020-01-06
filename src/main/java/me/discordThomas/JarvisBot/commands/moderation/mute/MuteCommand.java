package me.discordThomas.JarvisBot.commands.moderation.mute;

import me.discordThomas.JarvisBot.commands.api.Categories;
import me.discordThomas.JarvisBot.commands.api.Command;
import me.discordThomas.JarvisBot.utils.CustomPermission;
import me.discordThomas.JarvisBot.utils.DataFields;
import me.discordThomas.JarvisBot.utils.Time;
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
        String staff = event.getMember() != null ? event.getMember().getAsMention() : "Jarvis";
        if (args.size() < 1) {
            channel.sendMessage("You don't have enough args!").queue(message -> message.delete().queueAfter(1, TimeUnit.MINUTES));
        } else {
            Member member = guild.getMemberById(Long.parseLong(args.get(0).replace("<@!", "").replace(">", "")));
            if (member == null) {
                channel.sendMessage("Member is not founded!").complete();
                return;
            }
            try {
                int seconds = 60;
                long minutes = (args.size() == 2 ? Long.parseLong(args.get(1)) : 10);
                long muteTime = minutes * seconds;
                if (!Mute.isMuted(member)) {
                    Mute.mute(member, new Mute("da", event.getMember(), System.currentTimeMillis(), muteTime));
                    channel.sendMessage(member.getAsMention() + " was muted by " + staff + " for " + minutes + " minutes!").complete();
                } else {
                    Mute.unmute(member);
                    channel.sendMessage(member.getAsMention() + " has been unmuted by " + staff + "!").complete();
                }
            } catch (NumberFormatException e) {
                channel.sendMessage("You need a number that are in minutes for your second arg!").complete();
            }
        }
    }
}
