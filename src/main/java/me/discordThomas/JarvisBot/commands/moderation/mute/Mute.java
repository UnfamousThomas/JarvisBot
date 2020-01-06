package me.discordThomas.JarvisBot.commands.moderation.mute;

import me.discordThomas.JarvisBot.utils.Time;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

import java.awt.*;

public class Mute {

    private String reason;
    private Member staff;
    private long lastTime;
    private long muteTime;
    private Guild server;

    public Mute(String reason, Member staff, long lastTime, long muteTime, Guild server) {
        setReason(reason);
        setStaff(staff);
        setLastTime(lastTime);
        setMuteTime(muteTime);
        setServer(server);
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

    public Guild getServer() {
        return server;
    }

    public void setServer(Guild server) {
        this.server = server;
    }

    public EmbedBuilder mutedMessage() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        String staffAsMention = getStaff() != null ? getStaff().getAsMention() : "Jarvis";
        embedBuilder.setColor(Color.RED);
        embedBuilder.setTitle("Muted");
        embedBuilder.addField("Server", getServer().getName(), false);
        embedBuilder.addField("Reason", getReason(), false);
        embedBuilder.addField("Staff", staffAsMention, false);
        long remainingTime = (getLastTime() + getMuteTime()) - (System.currentTimeMillis());
        embedBuilder.addField("Remaining Time", Time.format(remainingTime), false);
        return embedBuilder;
    }

    public EmbedBuilder unmutedMessage() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        String staffAsMention = getStaff() != null ? getStaff().getAsMention() : "Jarvis";
        embedBuilder.setColor(Color.RED);
        embedBuilder.setTitle("Unmuted");
        embedBuilder.addField("Server", getServer().getName(), false);
        embedBuilder.addField("Staff", staffAsMention, false);
        long remainingTime = (getLastTime() + getMuteTime()) - (System.currentTimeMillis());
        embedBuilder.addField("Remaining Time", Time.format(remainingTime), false);
        return embedBuilder;
    }
}
