package com.discordapp.JarvisBot.commands.api;

import com.discordapp.JarvisBot.utils.DataFields;
import com.google.common.collect.Maps;
import com.discordapp.JarvisBot.utils.CustomPermission;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class Command {
	final String name;
	private final Map<String, Command> subcommands = Maps.newHashMap();
	public String capitalizedName;
	public CustomPermission permission;
	public String usage;
	public int minArgs = 0;
	public int maxArgs = Integer.MAX_VALUE;
	public Categories category;
	protected String[] aliases = {};
	protected String description = "No description set. Sorry!";

	public Command(String name) {
		this.name = name;
		this.capitalizedName = this.name.substring(0, 1).toUpperCase() + this.name.substring(1);
	}

	public void execute(MessageReceivedEvent event, List<String> args) {
		if (!(event.getChannel().getType().isGuild())) {
			event.getChannel().sendMessage("Has to be in a guild channel.").queue();
			return;
		}


		event.getMessage().delete().queueAfter(20, TimeUnit.SECONDS);
		if (args.size() > 0) {
			Command subcommand = subcommands.get(args.get(0).toLowerCase());
			if (subcommand != null) {
				args.remove(0);
				subcommand.execute(event, args);
				return;
			}
		}

		if (args.size() < minArgs || args.size() > maxArgs) {
			event.getChannel().sendMessage("Invalid usage. Please use .help.").queue();
			return;
		}

		TextChannel textChannel = event.getTextChannel();
		if (permission == CustomPermission.DEV || permission == CustomPermission.BOTHELPER) {
			if (permission == CustomPermission.DEV) {
				if (!(Arrays.asList(permission.returnDev()).contains(event.getAuthor().getId()))) {
					sendPermissionMessage(textChannel);
					return;
				}
			} else {
				if (DataFields.botHelperList.get(event.getAuthor().getIdLong()) != null && !(Arrays.asList(permission.returnDev()).contains(event.getAuthor().getId()))) {
					sendPermissionMessage(textChannel);
					return;
				}
			}
		}

		switch (permission) {
			case ADMIN:
				if (!(event.getMember().getPermissions().contains(Permission.valueOf(CustomPermission.ADMIN.perm)))) {
					sendPermissionMessage(textChannel);
					return;
				}
				break;
			case MODERATOR:
				if (!(event.getMember().getPermissions().contains(Permission.valueOf(CustomPermission.MODERATOR.perm)))) {
					sendPermissionMessage(textChannel);
					return;
				}
				break;
			case MEMBER:
				if (!(event.getMember().getPermissions().contains(Permission.valueOf(CustomPermission.MEMBER.perm)))) {
					sendPermissionMessage(textChannel);
					return;
				}
				break;
		}
		run(event.getMember(), args, event);
	}

	protected void addSubcommands(Command... commands) {
		for (Command command : commands) {
			subcommands.put(command.name, command);

			for (String alias : command.aliases)
				subcommands.put(alias, command);
		}
	}

	public String[] alias(String... aliases) {
		return aliases;
	}

	private void sendPermissionMessage(TextChannel tc) {
		tc.sendMessage("You do not have sufficient permissions for that command.").queue();
	}

	public abstract void run(Member m, List<String> args, MessageReceivedEvent event);
}
