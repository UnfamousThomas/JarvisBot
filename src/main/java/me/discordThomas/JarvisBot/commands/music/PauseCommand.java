package me.discordThomas.JarvisBot.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import me.discordThomas.JarvisBot.commands.api.Categories;
import me.discordThomas.JarvisBot.commands.api.Command;
import me.discordThomas.JarvisBot.music.GuildMusicManager;
import me.discordThomas.JarvisBot.music.PlayerManager;
import me.discordThomas.JarvisBot.utils.CustomPermission;
import me.discordThomas.JarvisBot.utils.DataFields;
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
