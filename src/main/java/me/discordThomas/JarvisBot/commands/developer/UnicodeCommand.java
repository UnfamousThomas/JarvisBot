package me.discordThomas.JarvisBot.commands.developer;

import me.discordThomas.JarvisBot.commands.api.Categories;
import me.discordThomas.JarvisBot.commands.api.Command;
import me.discordThomas.JarvisBot.commands.api.CommandManager;
import me.discordThomas.JarvisBot.utils.CustomPermission;
import me.discordThomas.JarvisBot.utils.DataFields;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class UnicodeCommand extends Command {


	public UnicodeCommand() {
		super("unicode");
		minArgs = 0;
		maxArgs = 0;
		description = "Displays unicode info for developers. | Usage: `" + DataFields.prefix + "unicode`";
		category = Categories.DEVELOPER;
		permission = CustomPermission.DEV;
	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		if(DataFields.unicodeMap.containsKey(event.getAuthor().getIdLong())) {
			event.getChannel().sendMessage("Previous unicode message deactivated.").queue();
		}

		event.getChannel().sendMessage("React with the emoji you wish to transform into unicode!").queue(message -> {
			DataFields.unicodeMap.put(event.getAuthor().getIdLong(), message.getIdLong());
		});
	}


}
