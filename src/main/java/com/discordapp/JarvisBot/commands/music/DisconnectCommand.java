package com.discordapp.JarvisBot.commands.music;

import com.discordapp.JarvisBot.commands.api.Categories;
import com.discordapp.JarvisBot.commands.api.Command;
import com.discordapp.JarvisBot.utils.DataFields;
import com.discordapp.JarvisBot.utils.CustomPermission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.List;

public class DisconnectCommand extends Command {
	public DisconnectCommand() {
		super("disconnect");
		permission = CustomPermission.DEV;
		usage = "`" + DataFields.prefix + "disconnect" + "`";
		description = "Makes the bot leave a channel.";
		aliases = alias("dc", "disc");
		category = Categories.MUSIC;
	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		TextChannel channel = event.getTextChannel();
		AudioManager audioManager = event.getGuild().getAudioManager();

		if(!audioManager.isConnected()) {
			channel.sendMessage("Already not connected to a channel.").queue();
			return;
		}

		VoiceChannel voiceChannel = audioManager.getConnectedChannel();

		if(!voiceChannel.getMembers().contains(m)) {
			channel.sendMessage("You have to be in the same voice channel as the bot to use this command").queue();
			return;
		}

		audioManager.closeAudioConnection();
		DataFields.botJoinChannel.remove(event.getGuild().getIdLong());
		channel.sendMessage("Disconnected from your channel. Queue will remain in memory until restart.").queue();
	}
}
