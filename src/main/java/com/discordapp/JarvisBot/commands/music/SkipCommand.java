package com.discordapp.JarvisBot.commands.music;

import com.discordapp.JarvisBot.commands.api.Categories;
import com.discordapp.JarvisBot.commands.api.Command;
import com.discordapp.JarvisBot.music.GuildMusicManager;
import com.discordapp.JarvisBot.music.PlayerManager;
import com.discordapp.JarvisBot.music.TrackScheduler;
import com.discordapp.JarvisBot.utils.DataFields;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.discordapp.JarvisBot.utils.CustomPermission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class SkipCommand extends Command {

	public SkipCommand() {
		super("skip");
		description = "Skip current music";
		usage = "`" + DataFields.prefix + "skip" + "`";
		permission = CustomPermission.DEV;
		category = Categories.MUSIC;
	}
	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		TextChannel channel = event.getTextChannel();
		PlayerManager playerManager = PlayerManager.getInstance();
		GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
		TrackScheduler scheduler = musicManager.scheduler;
		AudioPlayer player = musicManager.player;

		if(player.getPlayingTrack() == null) {
			channel.sendMessage("No tracks playing right now!").queue();
			return;
		}

		channel.sendMessage("Skipping current song: " + player.getPlayingTrack().getInfo().title).queue();

		scheduler.nextTrack();
	}
}
