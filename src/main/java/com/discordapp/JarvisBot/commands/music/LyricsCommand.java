package com.discordapp.JarvisBot.commands.music;

import com.discordapp.JarvisBot.commands.api.Categories;
import com.discordapp.JarvisBot.commands.api.Command;
import com.discordapp.JarvisBot.music.GuildMusicManager;
import com.discordapp.JarvisBot.music.PlayerManager;
import com.discordapp.JarvisBot.utils.CustomPermission;
import com.discordapp.JarvisBot.utils.DataFields;
import com.discordapp.JarvisBot.utils.LyricsManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.List;

public class LyricsCommand extends Command {
	public LyricsCommand() {
		super("lyrics");
		description = "Shows songs lyrics";
		usage = "`" + DataFields.prefix + "lyrics" + "`";
		permission = CustomPermission.DEV;
		category = Categories.MUSIC;
		minArgs = 0;
		maxArgs = 0;
	}
	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		LyricsManager lyricsManager = LyricsManager.getInstance();
		TextChannel channel = event.getTextChannel();
		PlayerManager playerManager = PlayerManager.getInstance();
		GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
		AudioPlayer player = musicManager.player;
		AudioManager audioManager = event.getGuild().getAudioManager();

		if(!audioManager.isConnected()) {
			channel.sendMessage("Have to be connected to display lyrics!").queue();
			return;
		}

		if(player.getPlayingTrack() == null) {
			channel.sendMessage("Have to have a song playing to display its lyrics!").queue();
			return;
		}
		AudioTrackInfo info = player.getPlayingTrack().getInfo();
		String title = info.title;
		String author = info.author;

		String lyrics = lyricsManager.getLyrics(title, author);

		channel.sendMessage("```" + lyrics + "``` ").queue();
	}
}
