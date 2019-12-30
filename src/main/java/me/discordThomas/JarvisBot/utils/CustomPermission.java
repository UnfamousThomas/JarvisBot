package me.discordThomas.JarvisBot.utils;


import me.discordThomas.JarvisBot.JarvisBot;
import net.dv8tion.jda.api.Permission;

public enum  CustomPermission {
	ADMIN(Permission.ADMINISTRATOR),
	MODERATOR(Permission.KICK_MEMBERS),
	HELPER(Permission.NICKNAME_CHANGE),
	MEMBER(Permission.MESSAGE_READ),
	DEV(null);
	CustomPermission(Permission perm) {
		this.perm = perm;
	}
	public Permission perm;
	public String[] returnDev() {
		return JarvisBot.devids;
	}
}
