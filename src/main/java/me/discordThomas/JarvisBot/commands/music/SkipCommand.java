package me.discordThomas.JarvisBot.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import me.discordThomas.JarvisBot.commands.api.Categories;
import me.discordThomas.JarvisBot.commands.api.Command;
import me.discordThomas.JarvisBot.music.GuildMusicManager;
import me.discordThomas.JarvisBot.music.PlayerManager;
import me.discordThomas.JarvisBot.music.TrackScheduler;
import me.discordThomas.JarvisBot.utils.CustomPermission;
import me.discordThomas.JarvisBot.utils.DataFields;
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

		scheduler.nextTrack();

		channel.sendMessage("Skipping current track.").queue();
	}
}
