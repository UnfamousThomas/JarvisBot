package com.discordapp.JarvisBot.punishments.objects;

import com.discordapp.JarvisBot.utils.mysql.MySQLManager;
import net.dv8tion.jda.api.entities.TextChannel;

public class PunishmentObject {
    public Long user;
    public TextChannel punishChannel;
    private boolean appealed;
    private String appealer;
    private Long appealedtime;
    private String appealedReason;
    private int type;
    private Degree degree;
    private Long staff;
    private String reason;
    private Long duration;
    private String evidence;
    private Long received;
    private Long endtime;
    private boolean active;
    int id;

    public PunishmentObject(Degree degree, Long user, Long staff, String reason, Long duration, TextChannel channel, Long received, boolean active, String evidence, int type) {
        this.degree = degree;
        this.user = user;
        this.staff = staff;
        this.reason = reason;
        this.duration = duration;
        this.punishChannel = channel;
        this.active = active;
        this.received = received;
        this.evidence = evidence;
        this.type = type;
        this.endtime = received + duration;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setReceived(Long received) {
        this.received = received;
    }

    public Degree getDegree() {
        return degree;
    }

    public String getReason() {
        return reason;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getEndtime() {
        return endtime;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public Long getAppealedtime() {
        return appealedtime;
    }

    public void setAppealedtime(Long appealedtime) {
        this.appealedtime = appealedtime;
    }

    public String getAppealer() {
        return appealer;
    }

    public void setAppealer(String appealer) {
        this.appealer = appealer;
    }

    public boolean isAppealed() {
        return appealed;
    }

    public void setAppealed(boolean appealed) {
        this.appealed = appealed;
    }

    public String getAppealedReason() {
        return appealedReason;
    }

    public void setAppealedReason(String appealedReason) {
        this.appealedReason = appealedReason;
    }

    public int getId() {
        return id;
    }

    public void activate() {
        this.active = true;

        MySQLManager.execute("INSERT INTO punishments (guildid, userid, staffid, degree, reason, evidence, type, duration, received, active) VALUES (?,?,?,?,?,?,?,?,?,?)",
                this.punishChannel.getGuild().getIdLong(),
                this.user,
                this.staff,
                this.degree.toString(),
                this.reason,
                this.evidence,
                this.type,
                this.duration,
                this.received,
                this.active
        );
        MySQLManager.select("SELECT * FROM punishments WHERE guildid=? AND userid=? AND staffid=? AND degree=? AND reason=? AND evidence=? AND type=? AND duration=? AND received=? AND active=?", resultSet -> {
                this.setId(resultSet.getInt("id"));
        },
                this.punishChannel.getGuild().getIdLong(),
                this.user,
                this.staff,
                this.degree.toString(),
                this.reason,
                this.evidence,
                this.type,
                this.duration,
                this.received,
                this.active);
    }
    //TODO: FIGURE OUT HOW TO GET ID FROM DATABASE AND ASSIGN IT TO OBJECT
}
