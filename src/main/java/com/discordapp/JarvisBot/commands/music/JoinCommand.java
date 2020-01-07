package com.discordapp.JarvisBot.commands.music;

import com.discordapp.JarvisBot.commands.api.Categories;
import com.discordapp.JarvisBot.utils.DataFields;
import com.discordapp.JarvisBot.commands.api.Command;
import com.discordapp.JarvisBot.utils.CustomPermission;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.List;

public class JoinCommand extends Command {

	public JoinCommand() {
		super("join");
		description = "Make the bot join a channel.";
		usage = "`" + DataFields.prefix + "join" + "`";
		permission = CustomPermission.DEV;
		category = Categories.MUSIC;
	}
	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		TextChannel channel = event.getTextChannel();
		AudioManager audioManager = event.getGuild().getAudioManager();

		if(audioManager.isConnected()) {
			channel.sendMessage("Already connected to a channel.").queue();
			return;
		}

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
}
