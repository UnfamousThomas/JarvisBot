package me.discordThomas.JarvisBot.utils;

import me.discordThomas.JarvisBot.commands.fun.dailyfact.Animal;
import net.dv8tion.jda.api.entities.Guild;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataFields {

	public static HashMap<Long, Long> unicodeMap = new HashMap<>();

	public static HashMap<Long, Long> factsMap = new HashMap<>();

	public static HashMap<Long, String> botHelperList = new HashMap<>();

	public static List<String> dadJokesList = new ArrayList<>();

	public static List<String> normalJokesList = new ArrayList<>();

	public static HashMap<Animal, String> factsStringMap = new HashMap<>(3);

	public static HashMap<Long, String> blacklistedPeopleList = new HashMap<>();

	public static List<Guild> guildsList = new ArrayList<>();

	public static String prefix = ".";

	public static String version = "#1";

	public static String youtubeAPIKey;

	public static String lyricsAPIKey;

	public static void setPrefix(String newPrefix) {
		if (newPrefix != null) {
			prefix = newPrefix;
		}
	}


}
