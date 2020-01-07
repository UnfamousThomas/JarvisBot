package com.discordapp.JarvisBot.commands.music;

import com.discordapp.JarvisBot.commands.api.Categories;
import com.discordapp.JarvisBot.commands.api.Command;
import com.discordapp.JarvisBot.music.PlayerManager;
import com.discordapp.JarvisBot.utils.CustomPermission;
import com.discordapp.JarvisBot.utils.DataFields;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

import javax.annotation.Nullable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PlayCommand extends Command {
	private final YouTube youTube;

	public PlayCommand() {
		super("play");
		this.category = Categories.MUSIC;
		description = "Play music! Boom bom!";
		usage = DataFields.prefix + "play [song name/yt link]";
		permission = CustomPermission.DEV;
		description = "Queue songs for the bot to play.";
		minArgs = 1;

		YouTube temp = null;


		try {
			temp = new YouTube.Builder(
					GoogleNetHttpTransport.newTrustedTransport(),
					JacksonFactory.getDefaultInstance(),
					null
			)
					.setApplicationName("Jarvis Bot Music Query")
					.build();
		} catch (Exception e) {
			e.printStackTrace();
		}

		youTube = temp;
	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		TextChannel channel = event.getTextChannel();
		AudioManager audioManager = event.getGuild().getAudioManager();

		if(!(audioManager.isConnected())) {
			GuildVoiceState memberVoiceState = event.getMember().getVoiceState();

			if(memberVoiceState != null && !memberVoiceState.inVoiceChannel()) {
				channel.sendMessage("Please join a voice channel first.").queue();
				return;
			}
			VoiceChannel voiceChannel = memberVoiceState.getChannel();
			Member selfMember = event.getGuild().getSelfMember();

			if(!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)) {
				channel.sendMessageFormat("I am missing permisison to join %s", voiceChannel).queue();
				return;
			}

			audioManager.openAudioConnection(voiceChannel);
			channel.sendMessage("Joining your voice channel.").queue();
		}
		if (args.isEmpty()) {
			channel.sendMessage("Please provide some arguments").queue();

			return;
		}

		String input = String.join(" ", args);

		if (!isUrl(input)) {
			String ytSearched = searchYoutube(input);

			if (ytSearched == null) {
				channel.sendMessage("Youtube returned no results").queue();

				return;
			}

			input = ytSearched;
		}

		PlayerManager manager = PlayerManager.getInstance();

		manager.loadAndPlay(channel, input);
	}

	private boolean isUrl(String input) {
		try {
			new URL(input);
			return true;
		} catch (MalformedURLException ignored) {
			return false;
		}
	}

	@Nullable
	private String searchYoutube(String query) {
		try {
			List<SearchResult> results = youTube.search()
					.list("id,snippet")
					.setQ(query)
					.setMaxResults(1L)
					.setType("video")
					.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
					.setKey(DataFields.youtubeAPIKey)
					.execute()
					.getItems();

			if (!results.isEmpty()) {
				String videoId = results.get(0).getId().getVideoId();

				return "https://www.youtube.com/watch?v=" + videoId;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	}
