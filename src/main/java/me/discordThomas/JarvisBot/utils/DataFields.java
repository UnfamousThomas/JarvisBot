package me.discordThomas.JarvisBot.utils;

import me.discordThomas.JarvisBot.commands.fun.dailyfact.Animal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataFields {

	public static HashMap<Long, Long> unicodeMap = new HashMap<>();

	public static HashMap<Long, Long> factsMap = new HashMap<>();

	public static List<Long> botHelperList = new ArrayList<>();

	public static List<String> dadJokesList = new ArrayList<>();

	public static List<String> normalJokesList = new ArrayList<>();

	public static HashMap<Animal, String> factsStringMap = new HashMap<>(3);

	public static List<Long> blacklistedPeopleList = new ArrayList<>();

	public static String prefix = ".";

	public static void setPrefix(String newPrefix) {
		if(newPrefix != null) {
			prefix = newPrefix;
		}
	}

	public static void addBotHelper(long userID) {
		botHelperList.add(userID);
	}

	public static void addBlacklist(long userID) { blacklistedPeopleList.add(userID); }
 }
