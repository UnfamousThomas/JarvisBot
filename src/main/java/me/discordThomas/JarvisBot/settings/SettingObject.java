package me.discordThomas.JarvisBot.settings;

public class SettingObject {
	SettingObject(long guild, SettingType type, String value) {
		this.guildID = guild;
		this.type = type;
		this.value = value;
	}

	private long guildID;
	private SettingType type;
	private String value;

	public long getGuildID() {
		return guildID;
	}

	public String getName() {
		return type.name();
	}

	public String getValue() {
		return value;
	}

	public void setGuildID(long guildID) {
		this.guildID = guildID;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
