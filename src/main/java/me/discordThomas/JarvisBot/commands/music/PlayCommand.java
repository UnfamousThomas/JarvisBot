package me.discordThomas.JarvisBot.commands.music;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import me.discordThomas.JarvisBot.commands.api.Categories;
import me.discordThomas.JarvisBot.commands.api.Command;
import me.discordThomas.JarvisBot.music.PlayerManager;
import me.discordThomas.JarvisBot.utils.CustomPermission;
import me.discordThomas.JarvisBot.utils.DataFields;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

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
		usage = DataFields.prefix + "play [song name]";
		permission = CustomPermission.DEV;
		description = "hey!";
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
