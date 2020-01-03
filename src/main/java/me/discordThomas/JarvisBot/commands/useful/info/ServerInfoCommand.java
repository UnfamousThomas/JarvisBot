package me.discordThomas.JarvisBot.commands.useful.info;

import me.discordThomas.JarvisBot.commands.api.Categories;
import me.discordThomas.JarvisBot.commands.api.Command;
import me.discordThomas.JarvisBot.utils.CustomPermission;
import me.discordThomas.JarvisBot.utils.DataFields;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ServerInfoCommand extends Command {
	public ServerInfoCommand() {
		super("serverinfo");
		minArgs = 0;
		description = "Tells you info about the server.";
		usage = "`" + DataFields.prefix + "serverinfo`";
		category = Categories.USEFUL;
		permission = CustomPermission.MEMBER;

	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		event.getChannel().sendMessage(Guildinfo(event.getGuild(), event.getJDA().getShardInfo().getShardId())).queue();
	}

	private MessageEmbed Guildinfo(Guild g, int shard) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("Server Info");
		builder.addField("Owner", g.getOwner().getEffectiveName(), true);
		builder.addField("Boosts", String.valueOf(g.getBoostCount()), true);
		builder.addField("Boost level", String.valueOf(g.getBoostTier().getKey()), true);
		builder.addField("Created at", g.getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME), true);
		builder.addField("Verification level", convertVerificationLevel(g.getVerificationLevel()), true);
		builder.addField("Region", g.getRegion().getName(), true);
		builder.addBlankField(false);
		builder.addField("Using Shard", String.valueOf(shard), true);
		builder.setThumbnail(g.getIconUrl());
		builder.setColor(Color.decode("#3498db"));
		builder.setFooter(g.getName(), g.getIconUrl());
		return builder.build();
	}

	private String convertVerificationLevel(Guild.VerificationLevel verificationLevel) {
		String[] names = verificationLevel.name().toLowerCase().split("_");
		StringBuilder out = new StringBuilder();

		for (String name : names) {
			out.append(Character.toUpperCase(name.charAt(0))).append(name.substring(1)).append(" ");
		}

		return out.toString().trim();
	}


}
