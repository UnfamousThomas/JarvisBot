package com.discordapp.JarvisBot.commands.music;

import com.discordapp.JarvisBot.commands.api.Categories;
import com.discordapp.JarvisBot.commands.api.Command;
import com.discordapp.JarvisBot.music.GuildMusicManager;
import com.discordapp.JarvisBot.music.PlayerManager;
import com.discordapp.JarvisBot.utils.CustomPermission;
import com.discordapp.JarvisBot.utils.DataFields;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class PauseCommand extends Command {
	public PauseCommand() {
		super("pause");
		permission = CustomPermission.DEV;
		category = Categories.MUSIC;
		description = "Pause the music!";
		usage = DataFields.prefix + "pause";
	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		TextChannel channel = event.getTextChannel();
		PlayerManager playerManager = PlayerManager.getInstance();
		GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
		AudioPlayer player = musicManager.player;

		if(player.isPaused()) {
			player.setPaused(false);
			channel.sendMessage("Unpaused!").queue();
		} else {
			player.setPaused(true);
			channel.sendMessage("Paused!").queue();
		}
	}
}
