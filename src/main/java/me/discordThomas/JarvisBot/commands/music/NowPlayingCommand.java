package me.discordThomas.JarvisBot.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import me.discordThomas.JarvisBot.commands.api.Categories;
import me.discordThomas.JarvisBot.commands.api.Command;
import me.discordThomas.JarvisBot.music.GuildMusicManager;
import me.discordThomas.JarvisBot.music.PlayerManager;
import me.discordThomas.JarvisBot.music.TrackScheduler;
import me.discordThomas.JarvisBot.utils.CustomPermission;
import me.discordThomas.JarvisBot.utils.DataFields;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NowPlayingCommand extends Command {
	public NowPlayingCommand() {
		super("nowplaying");
		aliases = alias("np");
		usage = "`" + DataFields.prefix + "nowplaying" + "`";
		description = "Shows you the song currently playing.";
		category = Categories.MUSIC;
		permission = CustomPermission.DEV;
	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		TextChannel channel = event.getTextChannel();
		PlayerManager playerManager = PlayerManager.getInstance();
		GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
		AudioPlayer player = musicManager.player;

		if(player.getPlayingTrack() == null) {
			channel.sendMessage("Not playing anything right now!").queue();
		return;
		}

		AudioTrackInfo info = player.getPlayingTrack().getInfo();

		channel.sendMessage(currentPlaying(info.title, info.uri, player.isPaused(), formatTime(player.getPlayingTrack().getPosition()), formatTime(player.getPlayingTrack().getDuration())).build()).queue();
	}

	private EmbedBuilder currentPlaying(String title, String url, boolean ispaused, String currentTime, String totalTime) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.addField("Playing:", "", false);
		builder.setColor(Color.decode("#3498db"));
		builder.addField("", "[" + title + "](" + url + ")", false);
		if(!ispaused) {
			builder.addField("","▶ " + currentTime + " - " + totalTime, false);
		} else {
			builder.addField("", "\u23F8 " +currentTime + " - " + totalTime, false);
		}
		return builder;
	}

	private String formatTime(long timeInMillis) {
		final long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
		final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
		final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}

}
