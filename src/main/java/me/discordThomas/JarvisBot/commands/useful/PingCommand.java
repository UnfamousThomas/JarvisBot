package me.discordThomas.JarvisBot.commands.useful;

import me.discordThomas.JarvisBot.commands.api.Categories;
import me.discordThomas.JarvisBot.commands.api.Command;
import me.discordThomas.JarvisBot.utils.CustomPermission;
import me.discordThomas.JarvisBot.utils.DataFields;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;
import java.util.Objects;

public class PingCommand extends Command {
	public PingCommand() {
		super("ping");
		maxArgs = 0;
		minArgs = 0;
		description = "A command to ping the bot! | Usage: `" + DataFields.prefix + "ping`";
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