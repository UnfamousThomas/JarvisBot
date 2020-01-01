package me.discordThomas.JarvisBot.commands.api;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

import java.awt.*;

public class HelpBuilder {
	private StringBuilder DevCommands = new StringBuilder();
	private StringBuilder AdminCommands = new StringBuilder();
	private StringBuilder FunCommands = new StringBuilder();
	private StringBuilder UsefulCommands = new StringBuilder();
	private StringBuilder ModCommands = new StringBuilder();
	private StringBuilder BotHelperCommands = new StringBuilder();

	public void addCommand(Command command) {
		switch (command.category) {
			case DEVELOPER:
				if(!(command.permission == null)) {
					DevCommands.append("\n").append("*").append(command.name.toUpperCase()).append("*").append(" - ").append(command.description).append(" - ").append(command.permission.name());
				} else {
					DevCommands.append("\n").append("*").append(command.name.toUpperCase()).append("*").append(" - ").append(command.description).append(" - ").append(command.permission.name());				}
				break;

			case ADMIN:
				if(!(command.permission == null)) {
					AdminCommands.append("\n").append("*").append(command.name.toUpperCase()).append("*").append(" - ").append(command.description).append(" - ").append(command.permission.name());

				} else {
					AdminCommands.append("\n").append("*").append(command.name.toUpperCase()).append("*").append(" - ").append(command.description).append(" - ").append(command.permission.name());

				}
				break;

			case FUN:
				if(!(command.permission == null)) {
					FunCommands.append("\n").append("*").append(command.name.toUpperCase()).append("*").append(" - ").append(command.description).append(" - ").append(command.permission.name());

				} else {
					FunCommands.append("\n").append("*").append(command.name.toUpperCase()).append("*").append(" - ").append(command.description).append(" - ").append(command.permission.name());

				}
				break;

			case USEFUL:
				if(!(command.permission == null)) {
					UsefulCommands.append("\n").append("*").append(command.name.toUpperCase()).append("*").append(" - ").append(command.description).append(" - ").append(command.permission.name());

				} else {
					UsefulCommands.append("\n").append("*").append(command.name.toUpperCase()).append("*").append(" - ").append(command.description).append(" - ").append(command.permission.name());

				}
				break;

			case MODERATE:
				if(!(command.permission == null)) {
					ModCommands.append("\n").append("*").append(command.name.toUpperCase()).append("*").append(" - ").append(command.description).append(" - ").append(command.permission.name());

				} else {
					ModCommands.append("\n").append("*").append(command.name.toUpperCase()).append("*").append(" - ").append(command.description).append(" - ").append(command.permission.name());

				}
				break;
			case BOTHELPER:
				if(!(command.permission == null)) {
					BotHelperCommands.append("\n").append("*").append(command.name.toUpperCase()).append("*").append(" - ").append(command.description).append(" - ").append(command.permission.name());

				} else {
					BotHelperCommands.append("\n").append("*").append(command.name.toUpperCase()).append("*").append(" - ").append(command.description).append(" - ").append(command.permission.name());

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
