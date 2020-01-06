package me.discordThomas.JarvisBot.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import me.discordThomas.JarvisBot.commands.api.Categories;
import me.discordThomas.JarvisBot.commands.api.Command;
import me.discordThomas.JarvisBot.music.GuildMusicManager;
import me.discordThomas.JarvisBot.music.PlayerManager;
import me.discordThomas.JarvisBot.utils.CustomPermission;
import me.discordThomas.JarvisBot.utils.DataFields;
import me.discordThomas.JarvisBot.utils.LyricsManager;
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
