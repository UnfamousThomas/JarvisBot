package me.discordThomas.JarvisBot.commands.api;

import com.google.common.collect.Maps;
import me.discordThomas.JarvisBot.utils.CustomPermission;
import me.discordThomas.JarvisBot.utils.Logger;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class Command {
	final String name;

	public CustomPermission permission;
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
		if(!(event.getChannel().getType().isGuild())) {
			event.getChannel().sendMessage("Has to be in a guild channel.").queue();
			return;
		}


		event.getMessage().delete().queueAfter(20, TimeUnit.SECONDS);
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

		if(permission.perm == null || !(event.getMember().getPermissions().contains(permission.perm))) {
				if(permission.perm == null && !(Arrays.asList(permission.returnDev()).contains(event.getMember().getUser().getId()))) {
				event.getChannel().sendMessage("Invalid permissions.").queue();

					return;
			}
				if(!(permission.perm == null) &&!(event.getMember().getPermissions().contains(permission.perm))) {
					event.getChannel().sendMessage("Invalid permissions.").queue();

					return;
				}

			run(event.getMember(), args, event);
		} else if(event.getMember().getPermissions().contains(permission.perm)) {

			run(event.getMember(), args, event);
		}
	}

	protected void addSubcommands(Command... commands) {
		for(Command command: commands) {
			subcommands.put(command.name, command);

			for(String alias : command.aliases)
				subcommands.put(alias, command);
		}
	}
	public String[] alias(String... aliases){
		return aliases;
	}

	public abstract void run(Member m, List<String> args, MessageReceivedEvent event);
}
