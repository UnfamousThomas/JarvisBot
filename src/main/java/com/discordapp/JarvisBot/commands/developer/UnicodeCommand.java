package com.discordapp.JarvisBot.commands.developer;

import com.discordapp.JarvisBot.commands.api.Categories;
import com.discordapp.JarvisBot.commands.api.Command;
import com.discordapp.JarvisBot.utils.CustomPermission;
import com.discordapp.JarvisBot.utils.DataFields;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class UnicodeCommand extends Command {


	public UnicodeCommand() {
		super("unicode");
		minArgs = 0;
		maxArgs = 0;
		description = "Displays unicode info for developers.";
		usage = "`" + DataFields.prefix + "unicode`";
		category = Categories.DEVELOPER;
		permission = CustomPermission.DEV;
	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		if (DataFields.unicodeMap.containsKey(event.getAuthor().getIdLong())) {
			event.getChannel().sendMessage("Previous unicode message deactivated.").queue();
		}

		event.getChannel().sendMessage("React with the emoji you wish to transform into unicode!").queue(message -> {
			DataFields.unicodeMap.put(event.getAuthor().getIdLong(), message.getIdLong());
		});
	}


}
