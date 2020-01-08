package com.discordapp.JarvisBot.punishments.objects;

import com.discordapp.JarvisBot.utils.mysql.MySQLManager;
import net.dv8tion.jda.api.entities.TextChannel;

public class PunishmentObject {
	 private Degree degree;
	 public Long user;
	 private Long staff;
	 private String reason;
	 private Long duration;
	 private String evidence;
	 public TextChannel punishChannel;
	 public Long received;
	 public Long endtime;
	 public boolean active;
	 int type;

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

	 public void setEvidence(String evidence) {
	 	this.evidence = evidence;
	 }

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public String getType() {
		if(this.type == 0) return "warn";
		if(this.type == 1) return "mute";
		if(this.type == 2) return "ban";
		else return null;
	}

	public Long getEndtime() {
	 	return endtime;
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
	}
}
