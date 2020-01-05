package me.discordThomas.JarvisBot.commands.music;

import me.discordThomas.JarvisBot.commands.api.Categories;
import me.discordThomas.JarvisBot.commands.api.Command;
import me.discordThomas.JarvisBot.music.GuildMusicManager;
import me.discordThomas.JarvisBot.music.PlayerManager;
import me.discordThomas.JarvisBot.utils.CustomPermission;
import me.discordThomas.JarvisBot.utils.DataFields;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class StopCommand extends Command {
	public StopCommand() {
		super("stop");
		description = "Stops the music.";
		usage = "`" + DataFields.prefix + "stop" + "`";
		aliases = alias("s");
		category = Categories.MUSIC;
		permission = CustomPermission.DEV;
	}
	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		PlayerManager playerManager = PlayerManager.getInstance();
		GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());

		musicManager.scheduler.getQueue().clear();
		musicManager.player.stopTrack();
		musicManager.player.setPaused(false);

		event.getChannel().sendMessage("Stopping the music and clearing the queue!").queue();
	}
}
