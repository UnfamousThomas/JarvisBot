package me.discordThomas.JarvisBot.commands.admin;

import me.discordThomas.JarvisBot.commands.api.Categories;
import me.discordThomas.JarvisBot.commands.api.Command;
import me.discordThomas.JarvisBot.utils.CustomPermission;
import me.discordThomas.JarvisBot.utils.DataFields;
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
	}
	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		event.getGuild().leave().queue();
	}
}
