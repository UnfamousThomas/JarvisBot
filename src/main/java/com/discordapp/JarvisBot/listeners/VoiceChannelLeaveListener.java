package com.discordapp.JarvisBot.listeners;

import com.discordapp.JarvisBot.music.GuildMusicManager;
import com.discordapp.JarvisBot.music.PlayerManager;
import com.discordapp.JarvisBot.utils.DataFields;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class VoiceChannelLeaveListener extends ListenerAdapter {

	public void onGuildVoiceLeave(GuildVoiceLeaveEvent e) {
		PlayerManager playerManager = PlayerManager.getInstance();
		GuildMusicManager musicManager = playerManager.getGuildMusicManager(e.getGuild());
		AudioManager audioManager = e.getGuild().getAudioManager();

		if(e.getChannelLeft().getMembers().size() == 1) {
			if(audioManager.isConnected()) {
				VoiceChannel channel = audioManager.getConnectedChannel();

				if(channel == e.getChannelLeft()) {
					audioManager.closeAudioConnection();
					TextChannel channel1 = DataFields.botJoinChannel.get(e.getGuild().getIdLong());
					channel1.sendMessage("As no one was in my voice channel I have left!").queue();
					DataFields.botJoinChannel.remove(e.getGuild().getIdLong());

				}
			}
		}
	}
}
