package com.discordapp.JarvisBot.commands.music;

import com.discordapp.JarvisBot.music.GuildMusicManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import com.discordapp.JarvisBot.commands.api.Categories;
import com.discordapp.JarvisBot.commands.api.Command;
import com.discordapp.JarvisBot.music.PlayerManager;
import com.discordapp.JarvisBot.utils.CustomPermission;
import com.discordapp.JarvisBot.utils.DataFields;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class QueueCommand extends Command {
	public QueueCommand() {
		super("queue");
		aliases = alias("q");
		usage = "`" + DataFields.prefix + "queue" + "`";
		description = "Shows you the songs currently in the queue";
		category = Categories.MUSIC;
		permission = CustomPermission.DEV;
	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		TextChannel channel = event.getTextChannel();
		PlayerManager playerManager = PlayerManager.getInstance();
		GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
		if (!musicManager.scheduler.getQueue().isEmpty()) {
			StringBuilder stringBuilder = new StringBuilder().append("Current songs:").append("\n");
			AudioTrackInfo nowInfo = musicManager.player.getPlayingTrack().getInfo();
			stringBuilder.append("Playing now: ").append(nowInfo.title).append(" - ").append(nowInfo.author).append("\n");
			musicManager.scheduler.getQueue().forEach(audiotrack -> {
				AudioTrackInfo info = audiotrack.getInfo();
				stringBuilder.append(info.title).append(" - ").append(info.author).append("\n");
			});
			channel.sendMessage(stringBuilder.toString()).queue();
		} else {
			if(musicManager.player.getPlayingTrack() != null) {
					StringBuilder stringBuilder = new StringBuilder().append("Current songs:").append("\n");
					AudioTrackInfo nowInfo = musicManager.player.getPlayingTrack().getInfo();
					stringBuilder.append("Playing now: ").append(nowInfo.title).append(" - ").append(nowInfo.author).append("\n");
					channel.sendMessage(stringBuilder.toString()).queue();
			} else {
				channel.sendMessage("No songs found!").queue();
			}
		}

	}
}
