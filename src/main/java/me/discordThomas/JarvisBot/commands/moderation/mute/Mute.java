package me.discordThomas.JarvisBot.commands.moderation.mute;

import me.discordThomas.JarvisBot.utils.DataFields;
import me.discordThomas.JarvisBot.utils.Time;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

import java.awt.*;

public class Mute {

    private String reason;
    private Member staff;
    private long lastTime;
    private long muteTime;

    public Mute(String reason, Member staff, long lastTime, long muteTime) {
        setReason(reason);
        setStaff(staff);
        setLastTime(lastTime);
        setMuteTime(muteTime);
    }

    public static boolean isMuted(Member member) {
        return DataFields.muteList.containsKey(member);
    }

    public static void mute(Member member, Mute mute) {
        if (!isMuted(member)) {
            DataFields.muteList.put(member, mute);
        }
    }

    public static void unmute(Member member) {
        if (isMuted(member)) {
            DataFields.muteList.remove(member);
        }
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Member getStaff() {
        return staff;
    }

    public void setStaff(Member staff) {
        this.staff = staff;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public long getMuteTime() {
        return muteTime;
    }

    public void setMuteTime(long muteTime) {
        this.muteTime = muteTime;
    }

    public EmbedBuilder mutedMessage(){
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.RED);
        embedBuilder.addField("Reason", reason, false);
        embedBuilder.addField("Staff", staff.getAsMention(), false);
        long muteTimeLeft = ((lastTime) + muteTime) - (System.currentTimeMillis());
        embedBuilder.addField("Remaining Time", Time.format(muteTimeLeft), false);
        return embedBuilder;
    }
}
