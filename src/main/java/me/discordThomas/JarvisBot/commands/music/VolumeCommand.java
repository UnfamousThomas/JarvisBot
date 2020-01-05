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
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.List;

public class VolumeCommand extends Command {
	public VolumeCommand() {
		super("volume");
		permission = CustomPermission.DEV;
		usage = "`" + DataFields.prefix + "volume" + "`";
		description = "Makes the bot change volume.";
		aliases = alias("dc", "disc");
		category = Categories.MUSIC;
		minArgs = 1;
		maxArgs = 1;
	}
	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		TextChannel channel = event.getTextChannel();
		PlayerManager playerManager = PlayerManager.getInstance();
		GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
		AudioPlayer player = musicManager.player;
		AudioManager audioManager = event.getGuild().getAudioManager();


		if(audioManager.isConnected()) {
			player.setVolume(Integer.parseInt(args.get(0)));
			channel.sendMessage("Set music level to: " + args.get(0)).queue();
		} else {
			channel.sendMessage("Cant set volume while not connected.").queue();
		}
	}
}
