package me.discordThomas.JarvisBot.commands.api;

import me.discordThomas.JarvisBot.utils.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

import java.awt.*;

public class HelpBuilder {
	private StringBuilder DevCommands = new StringBuilder().append("\n");
	static StringBuilder AdminCommands = new StringBuilder().append("\n");
	static StringBuilder FunCommands = new StringBuilder().append("\n");
	static StringBuilder UsefulCommands = new StringBuilder().append("\n");
	static StringBuilder ModCommands = new StringBuilder().append("\n");

	public void addCommand(Command command) {
		switch (command.category) {
			case DEVELOPER:
				if(!(command.permission == null)) {
					DevCommands.append(command.name).append(" - ").append(command.description).append(" - ").append(command.permission.name()).append("\n");
					Logger.log(Logger.Level.INFO, "test1");
				} else {
					DevCommands.append(command.name).append(" - ").append(command.description).append(" - ").append(command.permission.name()).append("\n");
					Logger.log(Logger.Level.INFO, "test2");
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
					Logger.log(Logger.Level.INFO, "test3");

				} else {
					UsefulCommands.append(command.name).append(" - ").append(command.description).append(" - ").append(command.permission.name()).append("\n");
					Logger.log(Logger.Level.INFO, "test4");

				}
				break;

			case MODERATE:
				if(!(command.permission == null)) {
					ModCommands.append(command.name).append(" - ").append(command.description).append(" - ").append(command.permission.name()).append("\n");

				} else {
					ModCommands.append(command.name).append(" - ").append(command.description).append(" - ").append(command.permission.name()).append("\n");

				}
				break;
		}
	}

	public  EmbedBuilder helpBuilder(Member m) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("Help!");
		builder.setColor(Color.decode("#3498db"));
		builder.addField("Developer Commands", DevCommands.toString(), false);
		builder.addField("Admin Commands", AdminCommands.toString(), false);
		builder.addField("Fun Commands", FunCommands.toString(), false);
		builder.addField("Moderation Commands", ModCommands.toString(), false);
		builder.addField("Useful Commands", UsefulCommands.toString(), false);
		builder.setFooter(m.getEffectiveName(), m.getUser().getEffectiveAvatarUrl());
		return builder;
	}
}
