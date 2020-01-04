package me.discordThomas.JarvisBot.listeners;

import me.discordThomas.JarvisBot.utils.DataFields;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Objects;

public class GuildEventsListener extends ListenerAdapter {

	public void onGuildJoin(GuildJoinEvent event) {
		DataFields.guildsList.add(event.getGuild());
		if(event.getGuild().getDefaultChannel() != null) {
			event.getGuild().getDefaultChannel().sendMessage(helloEmbed(event.getJDA())).queue();
		} else {
			Objects.requireNonNull(event.getGuild().getOwner()).getUser().openPrivateChannel().queue(channel -> channel.sendMessage(helloEmbed(event.getJDA())).queue());
		}
	}

	public void onGuildLeave(GuildLeaveEvent event) {
		DataFields.guildsList.remove(event.getGuild());
	}

	private MessageEmbed helloEmbed(JDA jda) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("Hello!");
		builder.addField("Thank you for adding me to your discord!", "I am a utility bot that focuses on jokes, daily facts & more!", true);
			builder.addField("Guilds", "I am currently in " + DataFields.guildsList.size() + " guilds.", true);
		builder.addBlankField(false);
		builder.addField("Public Discord", "[LINK](https://discord.gg/7nCEAvA)", false);
		builder.addField("Bot Invite:", "[LINK](https://discordapp.com/api/oauth2/authorize?client_id=658780975496691716&permissions=8&scope=bot)", true);
		builder.addBlankField(false);
		builder.addField("Bot Build version", DataFields.version, false);
		builder.setThumbnail(jda.getSelfUser().getEffectiveAvatarUrl());
		builder.setColor(Color.decode("#3498db"));
		builder.setFooter(jda.getSelfUser().getName(), jda.getSelfUser().getEffectiveAvatarUrl());
		return builder.build();
	}
}