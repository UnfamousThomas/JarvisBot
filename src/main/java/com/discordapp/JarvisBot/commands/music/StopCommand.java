package com.discordapp.JarvisBot.commands.music;

import com.discordapp.JarvisBot.commands.api.Categories;
import com.discordapp.JarvisBot.commands.api.Command;
import com.discordapp.JarvisBot.music.GuildMusicManager;
import com.discordapp.JarvisBot.utils.DataFields;
import com.discordapp.JarvisBot.music.PlayerManager;
import com.discordapp.JarvisBot.utils.CustomPermission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class StopCommand extends Command {
	public StopCommand() {
		super("stop");
		description = "Stops the music.";
		usage = "`" + DataFields.prefix + "stop" + "`";
		aliases = alias("s");
		category = Categories.MUSIC;
		permission = CustomPermission.DEV;
	}
	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		PlayerManager playerManager = PlayerManager.getInstance();
		GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());

		musicManager.scheduler.getQueue().clear();
		musicManager.player.stopTrack();
		musicManager.player.setPaused(false);

		event.getChannel().sendMessage("Stopping the music and clearing the queue!").queue();
	}
}
