package me.discordThomas.JarvisBot.commands.api;

import com.google.common.collect.Maps;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;
import java.util.Map;

public abstract class Command {
	final String name;

	public int minArgs = 0;
	public int maxArgs = Integer.MAX_VALUE;

	public Categories category;

	private final Map<String, Command> subcommands = Maps.newHashMap();

	protected String[] aliases = {};
	protected String description = "No description set. Sorry!";

	public Command(String name) {
		this.name = name;
	}

	public void execute(MessageReceivedEvent event, List<String> args) {
		if(args.size() > 0) {
			Command subcommand = subcommands.get(args.get(0).toLowerCase());
			if(subcommand != null) {
				args.remove(0);
				subcommand.execute(event, args);
				return;
			}
		}

		if(args.size() < minArgs || args.size() > maxArgs) {
			event.getChannel().sendMessage("Invalid usage. Please use .help.").queue();
		return;
		}
		run(event.getMember(), args, event);
	}

	protected void addSubcommands(Command... commands) {
		for(Command command: commands) {
			subcommands.put(command.name, command);

			for(String alias : command.aliases)
				subcommands.put(alias, command);
		}
	}

	public abstract void run(Member m, List<String> args, MessageReceivedEvent event);
}
