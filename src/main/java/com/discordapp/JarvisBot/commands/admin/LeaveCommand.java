package com.discordapp.JarvisBot.commands.admin;

import com.discordapp.JarvisBot.commands.api.Categories;
import com.discordapp.JarvisBot.commands.api.Command;
import com.discordapp.JarvisBot.utils.CustomPermission;
import com.discordapp.JarvisBot.utils.DataFields;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class LeaveCommand extends Command {
	public LeaveCommand() {
		super("leave");
		minArgs = 0;
		description = "Leaves the discord. | Usage: `" + DataFields.prefix + "leave`";
		category = Categories.ADMIN;
		permission = CustomPermission.ADMIN;
		usage = "`" + DataFields.prefix + " leave`";
	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		event.getGuild().leave().queue();
	}
}
