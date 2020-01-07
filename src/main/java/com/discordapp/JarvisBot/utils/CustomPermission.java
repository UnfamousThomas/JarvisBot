package com.discordapp.JarvisBot.utils;


import com.discordapp.JarvisBot.JarvisBot;

public enum CustomPermission {
	ADMIN("ADMINISTRATOR"),
	MODERATOR("KICK_MEMBERS"),
	MEMBER("MESSAGE_READ"),
	BOTHELPER("BOTHELPER"),
	DEV("DEV");

	public String perm;

	CustomPermission(String perm) {
		this.perm = perm;
	}

	public String[] returnDev() {
		return JarvisBot.devids;
	}

}
