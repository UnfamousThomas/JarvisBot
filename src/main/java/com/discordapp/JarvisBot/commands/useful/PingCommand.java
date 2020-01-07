package com.discordapp.JarvisBot.commands.useful;

import com.discordapp.JarvisBot.commands.api.Categories;
import com.discordapp.JarvisBot.utils.DataFields;
import com.discordapp.JarvisBot.commands.api.Command;
import com.discordapp.JarvisBot.utils.CustomPermission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class PingCommand extends Command {
	public PingCommand() {
		super("ping");
		maxArgs = 0;
		minArgs = 0;
		description = "A command to ping the bot!";
		usage = "`" + DataFields.prefix + "ping`";
		category = Categories.USEFUL;
		permission = CustomPermission.MEMBER;
	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		event.getChannel().sendMessage("Pong!").queue();
		long ping = event.getJDA().getGatewayPing();

		event.getChannel().sendMessage("Current ping: " + ping).queue();
	}
}