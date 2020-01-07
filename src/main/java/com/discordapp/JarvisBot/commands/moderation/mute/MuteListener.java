package com.discordapp.JarvisBot.commands.moderation.mute;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MuteListener extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {
        Member member = event.getMember();
        MuteManager muteManager = new MuteManager(member);
        if (!event.isFromGuild())
            return;
        if (!muteManager.isMuted())
            return;
        long remainingTime = (muteManager.getMute().getLastTime() + muteManager.getMute().getMuteTime()) - (System.currentTimeMillis());
        if (remainingTime > 0) {
            event.getMessage().delete().queue();
            assert member != null;
            member.getUser().openPrivateChannel().queue(channel -> channel.sendMessage(muteManager.getMute().mutedMessage().build()).queue());
        } else {
            assert member != null;
            member.getUser().openPrivateChannel().queue(channel -> channel.sendMessage(muteManager.getMute().unmutedMessage().build()).queue());
            muteManager.unmuteMember();
        }
    }
}
