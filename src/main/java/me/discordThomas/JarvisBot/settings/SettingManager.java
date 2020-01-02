package me.discordThomas.JarvisBot.settings;

import java.util.HashMap;
import java.util.List;

public class SettingManager {

	public HashMap<Long, List<SettingObject>> SettingsMap;

	public List<SettingObject> getGuildSettings(long id) {
		return SettingsMap.get(id);
	}


}
