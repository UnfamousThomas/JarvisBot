package me.discordThomas.JarvisBot.settings;

import net.dv8tion.jda.api.JDABuilder;

import java.util.HashMap;
import java.util.List;

public class SettingManager {

	public HashMap<Long, List<SettingObject>> SettingsMap;

	public List<SettingObject> getGuildSettings(long id) {
		return SettingsMap.get(id);
	}

	public void loadSettings(JDABuilder builder) {
	}

}
