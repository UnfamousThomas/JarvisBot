package me.discordThomas.JarvisBot.commands.useful.info;

import me.discordThomas.JarvisBot.commands.api.Categories;
import me.discordThomas.JarvisBot.commands.api.Command;
import me.discordThomas.JarvisBot.utils.CustomPermission;
import me.discordThomas.JarvisBot.utils.DataFields;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class BotVersionCommand extends Command {
	public BotVersionCommand() {
		super("botversion");
		minArgs = 0;
		description = "Tells you the bots current build version.";
		usage = "`" + DataFields.prefix + "botversion`";
		category = Categories.USEFUL;
		permission = CustomPermission.MEMBER;
		aliases = alias("version", "build", "v", "whoareu");
	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		event.getChannel().sendMessage(buildVersion(event.getMember()).build()).queue();
	}

	EmbedBuilder buildVersion(Member m) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("Build version");
		builder.setColor(Color.decode("#3498db"));
		builder.addField("Current Build version is:", DataFields.version, false);
		builder.setFooter(m.getEffectiveName(), m.getUser().getEffectiveAvatarUrl());
		return builder;
	}
}
