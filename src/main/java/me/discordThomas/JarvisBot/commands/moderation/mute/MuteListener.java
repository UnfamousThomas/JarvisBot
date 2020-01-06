package me.discordThomas.JarvisBot.commands.moderation.mute;

import me.discordThomas.JarvisBot.utils.DataFields;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;

public class MuteListener extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {
        Member member = event.getMember();
        if (!Mute.isMuted(member))
            return;
        Mute mute = DataFields.muteList.get(member);
        long muteTimeLeft = ((mute.getLastTime()) + mute.getMuteTime()) - (System.currentTimeMillis());
        if (muteTimeLeft < 0) {
            Mute.unmute(member);
            event.getChannel().sendMessage("You can't send message you are muted").queue();
        } else {
            event.getMessage().delete().queue();
            Objects.requireNonNull(event.getMember()).getUser().openPrivateChannel().queue(privateChannel -> {
                privateChannel.sendMessage(mute.mutedMessage().build()).queue();
              //  mute.mutedMessage().clear();
            });
        }
    }
}
