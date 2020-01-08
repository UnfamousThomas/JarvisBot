package com.discordapp.JarvisBot.commands.music;

import com.discordapp.JarvisBot.commands.api.Categories;
import com.discordapp.JarvisBot.commands.api.Command;
import com.discordapp.JarvisBot.music.GuildMusicManager;
import com.discordapp.JarvisBot.music.PlayerManager;
import com.discordapp.JarvisBot.utils.CustomPermission;
import com.discordapp.JarvisBot.utils.DataFields;
import com.google.common.base.Splitter;
import com.jagrosh.jlyrics.Lyrics;
import com.jagrosh.jlyrics.LyricsClient;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LyricsCommand extends Command {
	private LyricsClient client = new LyricsClient();
	public LyricsCommand() {
		super("lyrics");
		description = "Shows songs lyrics";
		usage = "`" + DataFields.prefix + "lyrics" + "`";
		permission = CustomPermission.DEV;
		category = Categories.MUSIC;
	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		String title;
		TextChannel channel = event.getTextChannel();
		PlayerManager playerManager = PlayerManager.getInstance();
		GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
		AudioPlayer player = musicManager.player;
		AudioManager audioManager = event.getGuild().getAudioManager();

		if (!audioManager.isConnected()) {
			channel.sendMessage("Have to be connected to display lyrics!").queue();
			return;
		}

		AudioTrackInfo info = player.getPlayingTrack().getInfo();
		if(args.size() >= 1) {
			title = String.join(" ", args);
		} else {
			if (!audioManager.isConnected()) {
				channel.sendMessage("Have to be connected to display lyrics!").queue();
				return;
			}
			title = info.title;
		}
		CompletableFuture<Lyrics> lyric = client.getLyrics(title);

		if (lyric == null) {

			channel.sendMessage("Could not find lyrics for: " + title).queue();
		} else {

			lyric.thenAccept(lyrics ->
			{
				if (lyrics == null) {
					channel.sendMessage("Lyrics for `" + title + "` could not be found!").queue();
					return;
				}

				EmbedBuilder eb = new EmbedBuilder()
						.setAuthor(lyrics.getAuthor())
						.setColor(event.getGuild().getSelfMember().getColor())
						.setTitle(lyrics.getTitle(), lyrics.getURL());
				if (lyrics.getContent().length() > 15000) {
					channel.sendMessage("Lyrics for `" + title + "` found but likely not correct: " + lyrics.getURL()).queue();
				} else if (lyrics.getContent().length() > 2000) {
					String content = lyrics.getContent().trim();
					while (content.length() > 2000) {
						int index = content.lastIndexOf("\n\n", 2000);
						if (index == -1)
							index = content.lastIndexOf("\n", 2000);
						if (index == -1)
							index = content.lastIndexOf(" ", 2000);
						if (index == -1)
							index = 2000;
						channel.sendMessage(eb.setDescription(content.substring(0, index).trim()).build()).queue();
						content = content.substring(index).trim();
						eb.setAuthor(null).setTitle(null, null);
					}
					channel.sendMessage(eb.setDescription(content).build()).queue();
				} else
					channel.sendMessage(eb.setDescription(lyrics.getContent()).build()).queue();
			});
		}
	}
}
