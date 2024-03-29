package com.discordapp.JarvisBot.commands.useful.info;

import com.discordapp.JarvisBot.utils.DataFields;
import com.discordapp.JarvisBot.commands.api.Categories;
import com.discordapp.JarvisBot.commands.api.Command;
import com.discordapp.JarvisBot.utils.CustomPermission;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BotInfoCommand extends Command {
	public BotInfoCommand() {
		super("botinfo");
		minArgs = 0;
		description = "Tells you info about the bot.";
		usage = "`" + DataFields.prefix + "botinfo`";
		category = Categories.USEFUL;
		permission = CustomPermission.MEMBER;

	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		event.getChannel().sendMessage(BotInfo(event.getJDA())).queue();
	}

	private MessageEmbed BotInfo(JDA jda) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("Bot Info");
		builder.addField("Developers", "**Sheep, UnfamousThomas & OhhhZenix**", true);
		builder.addField("Bot Gateway ping", String.valueOf(jda.getGatewayPing()), true);
		builder.addField("Guild amount", String.valueOf(DataFields.guildsList.size()), true);
		builder.addField("Bots account created at", jda.getSelfUser().getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME), true);
		builder.addBlankField(false);
		builder.addField("Public Discord", "[LINK](https://discord.gg/7nCEAvA)", false);
		builder.addField("Bot Invite:", "[LINK](https://discordapp.com/api/oauth2/authorize?client_id=658780975496691716&permissions=8&scope=bot)", true);
		builder.addBlankField(false);
		builder.addField("Build version", DataFields.version, false);
		builder.setThumbnail(jda.getSelfUser().getEffectiveAvatarUrl());
		builder.setColor(Color.decode("#3498db"));
		builder.setFooter(jda.getSelfUser().getName(), jda.getSelfUser().getEffectiveAvatarUrl());
		return builder.build();
	}

}