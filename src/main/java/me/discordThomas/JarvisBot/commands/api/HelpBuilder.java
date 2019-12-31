package me.discordThomas.JarvisBot.commands.api;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

import java.awt.*;

public class HelpBuilder {
	private StringBuilder DevCommands = new StringBuilder().append("\n");
	private StringBuilder AdminCommands = new StringBuilder().append("\n");
	private StringBuilder FunCommands = new StringBuilder().append("\n");
	private StringBuilder UsefulCommands = new StringBuilder().append("\n");
	private StringBuilder ModCommands = new StringBuilder().append("\n");
	private StringBuilder BotHelperCommands = new StringBuilder().append("\n");

	public void addCommand(Command command) {
		switch (command.category) {
			case DEVELOPER:
				if(!(command.permission == null)) {
					DevCommands.append(command.name).append(" - ").append(command.description).append(" - ").append(command.permission.name()).append("\n");
				} else {
					DevCommands.append(command.name).append(" - ").append(command.description).append(" - ").append(command.permission.name()).append("\n");
				}
				break;

			case ADMIN:
				if(!(command.permission == null)) {
					AdminCommands.append(command.name).append(" - ").append(command.description).append(" - ").append(command.permission.name()).append("\n");

				} else {
					AdminCommands.append(command.name).append(" - ").append(command.description).append(" - ").append(command.permission.name()).append("\n");

				}
				break;

			case FUN:
				if(!(command.permission == null)) {
					FunCommands.append(command.name).append(" - ").append(command.description).append(" - ").append(command.permission.name()).append("\n");

				} else {
					FunCommands.append(command.name).append(" - ").append(command.description).append(" - ").append(command.permission.name()).append("\n");

				}
				break;

			case USEFUL:
				if(!(command.permission == null)) {
					UsefulCommands.append(command.name).append(" - ").append(command.description).append(" - ").append(command.permission.name()).append("\n");

				} else {
					UsefulCommands.append(command.name).append(" - ").append(command.description).append(" - ").append(command.permission.name()).append("\n");

				}
				break;

			case MODERATE:
				if(!(command.permission == null)) {
					ModCommands.append(command.name).append(" - ").append(command.description).append(" - ").append(command.permission.name()).append("\n");

				} else {
					ModCommands.append(command.name).append(" - ").append(command.description).append(" - ").append(command.permission.name()).append("\n");

				}
				break;
			case BOTHELPER:
				if(!(command.permission == null)) {
					BotHelperCommands.append(command.name).append(" - ").append(command.description).append(" - ").append(command.permission.name()).append("\n");

				} else {
					BotHelperCommands.append(command.name).append(" - ").append(command.description).append(" - ").append(command.permission.name()).append("\n");

				}
				break;
		}
	}

	public  EmbedBuilder helpBuilder(Member m) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("Command Help");
		builder.setColor(Color.decode("#3498db"));
		builder.addField("Developer Commands", DevCommands.toString(), false);
		builder.addField("Bot Helper Commands", BotHelperCommands.toString(), false);
		builder.addField("Admin Commands", AdminCommands.toString(), false);
		builder.addField("Moderation Commands", ModCommands.toString(), false);
		builder.addField("Fun Commands", FunCommands.toString(), false);
		builder.addField("Useful Commands", UsefulCommands.toString(), false);
		builder.setFooter(m.getEffectiveName(), m.getUser().getEffectiveAvatarUrl());
		return builder;
	}
}
