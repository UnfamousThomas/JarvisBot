package me.discordThomas.JarvisBot.commands.music;

import me.discordThomas.JarvisBot.commands.api.Categories;
import me.discordThomas.JarvisBot.commands.api.Command;
import me.discordThomas.JarvisBot.utils.DataFields;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class PlayCommand extends Command {

	public PlayCommand() {
		super("play");
		this.category = Categories.MUSIC;
		description = "Play music! Boom bom!";
		usage = DataFields.prefix + "play [song name]";
		minArgs = 1;
	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {

	}
}
