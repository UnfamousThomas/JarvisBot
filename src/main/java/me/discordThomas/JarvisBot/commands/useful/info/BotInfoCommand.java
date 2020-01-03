package me.discordThomas.JarvisBot.commands.useful.info;

import me.discordThomas.JarvisBot.commands.api.Categories;
import me.discordThomas.JarvisBot.commands.api.Command;
import me.discordThomas.JarvisBot.utils.CustomPermission;
import me.discordThomas.JarvisBot.utils.DataFields;
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
		builder.addField("Developers", "**Sheep & UnfamousThomas**", true);
		builder.addField("Bots ping", String.valueOf(jda.getGatewayPing()), true);
		builder.addField("Shards guild amount", String.valueOf(jda.getGuilds().size()), true);
		builder.addField("Bots account created at", jda.getSelfUser().getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME), true);
		builder.addBlankField(false);
		builder.addField("Build version", DataFields.version, false);
		builder.setThumbnail(jda.getSelfUser().getEffectiveAvatarUrl());
		builder.setColor(Color.decode("#3498db"));
		builder.setFooter(jda.getSelfUser().getName(), jda.getSelfUser().getEffectiveAvatarUrl());
		return builder.build();
	}

}