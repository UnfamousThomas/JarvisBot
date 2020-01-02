package me.discordThomas.JarvisBot.utils;


import me.discordThomas.JarvisBot.JarvisBot;

public enum CustomPermission {
	ADMIN("ADMINISTRATOR"),
	MODERATOR("KICK_MEMBERS"),
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
