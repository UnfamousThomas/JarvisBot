package me.discordThomas.JarvisBot.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class GuildMemberJoin extends ListenerAdapter {

    private final String[] join_messages = {
            "{member} joined. You must construct additional pylons.",
            "Never gonna give {member} up. Never let {member} down!",
            "Hey! Listen! {member} has joined!",
            "Ha! {member} has joined! You activated my trap card!",
            "We've been expecting you, {member}.",
            "It's dangerous to go alone, take {member}!",
            "Swoooosh. {member} just landed.",
            "Brace yourselves. {member} just joined the server.",
            "A wild {member} appeared."
    };

    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Random random = new Random();
        int index = random.nextInt(join_messages.length);
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.decode("#36fc05"));
        embedBuilder.setDescription(join_messages[index].replace("{member}", event.getMember().getAsMention()));
        Objects.requireNonNull(event.getGuild().getDefaultChannel()).sendMessage(embedBuilder.build()).queue();
        embedBuilder.clear();
    }
}
