package me.discordThomas.JarvisBot.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class GuildMemberLeave extends ListenerAdapter {

    // Still need more messages
    private final String[] leave_messages = {
            "{member} left. The party is over.",
    };

    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
        Random random = new Random();
        int index = random.nextInt(leave_messages.length);
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.decode("#fc1105"));
        embedBuilder.setDescription(leave_messages[index].replace("{member}", event.getMember().getAsMention()));
        Objects.requireNonNull(event.getGuild().getDefaultChannel()).sendMessage(embedBuilder.build()).queue();
        embedBuilder.clear();
    }
}
