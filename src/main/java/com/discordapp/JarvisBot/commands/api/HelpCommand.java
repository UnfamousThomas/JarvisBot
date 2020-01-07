package com.discordapp.JarvisBot.commands.api;

import com.discordapp.JarvisBot.utils.DataFields;
import com.discordapp.JarvisBot.utils.CustomPermission;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HelpCommand extends Command {
	private HelpBuilder builder = CommandManager.helpBuilder;

	public HelpCommand() {
		super("help");
		minArgs = 0;
		maxArgs = 1;
		description = "A command to view commands. | Usage: `" + DataFields.prefix + "help`";
		category = Categories.USEFUL;
		permission = CustomPermission.MEMBER;
		aliases = alias("ah", "ehh");
		usage = "`" + DataFields.prefix + "help (command)`";
	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		User user = Objects.requireNonNull(event.getMember()).getUser();
		if (args.size() == 0) {
			event.getChannel().sendMessage("Sending you a DM!").queue();
			Long id = Objects.requireNonNull(event.getMember()).getIdLong();
			if (Arrays.asList(permission.returnDev()).contains(event.getAuthor().getId())) {
				event.getMember().getUser().openPrivateChannel().queue(channel -> channel.sendMessage(builder.helpBuilderDev(event.getMember()).build()).queue());
				return;
			}
			if (DataFields.botHelperList.get(id) != null) {
				event.getMember().getUser().openPrivateChannel().queue(channel -> channel.sendMessage(builder.helpBuilderBotHelper(event.getMember()).build()).queue());
				return;
			}
			if (event.getMember().getPermissions().contains(Permission.valueOf(CustomPermission.ADMIN.perm))) {
				event.getMember().getUser().openPrivateChannel().queue(channel -> channel.sendMessage(builder.helpBuilderAdmin(event.getMember()).build()).queue());
				return;
			}
			if (event.getMember().getPermissions().contains(Permission.valueOf(CustomPermission.MODERATOR.perm))) {
				event.getMember().getUser().openPrivateChannel().queue(channel -> channel.sendMessage(builder.helpBuilderMod(event.getMember()).build()).queue());
				return;
			}
			if (event.getMember().getPermissions().contains(Permission.valueOf(CustomPermission.MEMBER.perm))) {
				event.getMember().getUser().openPrivateChannel().queue(channel -> channel.sendMessage(builder.helpBuilderMember(event.getMember()).build()).queue());
			}
		} else if (args.size() == 1) {
			String command = args.get(0);
			if (CommandManager.instance.commands.get(command.toLowerCase()) != null) {
				Command commandObject = CommandManager.instance.commands.get(command.toLowerCase());
				user.openPrivateChannel().queue(channel -> {
					channel.sendMessage(helpUsage(event.getMember(), commandObject.capitalizedName, commandObject.usage).build()).queue();
				});
				event.getChannel().sendMessage("Sending you a dm!").queue();
			} else {
				event.getChannel().sendMessage("Command not found.").queue();
			}
		}
	}

	private EmbedBuilder helpUsage(Member m, String command, String usage) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("Command " + command + " help");
		builder.setColor(Color.decode("#3498db"));
		builder.addField("Command:", command, false);
		builder.addField("Usage: ", usage, false);

		builder.setFooter(m.getEffectiveName(), m.getUser().getEffectiveAvatarUrl());
		return builder;
	}
}
