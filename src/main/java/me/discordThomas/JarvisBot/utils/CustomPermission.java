package me.discordThomas.JarvisBot.utils;


import me.discordThomas.JarvisBot.JarvisBot;
import net.dv8tion.jda.api.Permission;

public enum CustomPermission {
	ADMIN("ADMINISTRATOR"),
	MODERATOR("KICK_MEMBERS"),
	HELPER("NICKNAME_CHANGE"),
	MEMBER("MESSAGE_READ"),
	BOTHELPER("BOTHELPER"),
	DEV("DEV");
	CustomPermission(String perm) {
		this.perm = perm;
	}
	public String perm;
	public String[] returnDev() { return JarvisBot.devids;
	}

}
