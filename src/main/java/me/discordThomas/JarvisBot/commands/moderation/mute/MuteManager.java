package me.discordThomas.JarvisBot.commands.moderation.mute;

import me.discordThomas.JarvisBot.utils.DataFields;
import net.dv8tion.jda.api.entities.Member;

public class MuteManager {

    private Member member;

    public MuteManager(Member member) {
        this.member = member;
    }

    public boolean isMuted() {
        return DataFields.muteList.containsKey(member);
    }

    public Mute getMute() {
        return DataFields.muteList.getOrDefault(member, null);
    }

    public void muteMember(Mute mute) {
        DataFields.muteList.put(member, mute);
    }

    public void unmuteMember() {
        DataFields.muteList.remove(member);
    }
}
