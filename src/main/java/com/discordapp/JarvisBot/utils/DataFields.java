package com.discordapp.JarvisBot.utils;

import com.discordapp.JarvisBot.commands.fun.dailyfact.Animal;
import com.discordapp.JarvisBot.punishments.PunishManager;
import com.vdurmont.emoji.EmojiParser;
import com.discordapp.JarvisBot.commands.moderation.mute.Mute;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataFields {

    public static HashMap<Member, Mute> muteList = new HashMap<>();

    public static HashMap<Long, Long> unicodeMap = new HashMap<>();

    public static HashMap<Long, Long> factsMap = new HashMap<>();

    public static HashMap<Long, String> botHelperList = new HashMap<>();

    public static List<String> dadJokesList = new ArrayList<>();

    public static List<String> normalJokesList = new ArrayList<>();

    public static HashMap<Animal, String> factsStringMap = new HashMap<>(3);

    public static HashMap<Long, String> blacklistedPeopleList = new HashMap<>();

    public static HashMap<Long, PunishManager> managerHashMap = new HashMap<>();
    public static List<Guild> guildsList = new ArrayList<>();

    public static String prefix = ".";

    public static String version = "#1";

    public static String youtubeAPIKey;

    public static String lyricsAPIKey;

    public static final String oneUnicode = EmojiParser.parseToUnicode(":one:");
    public static final String twoUnicode = EmojiParser.parseToUnicode(":two:");
    public static final String threeUnicode = EmojiParser.parseToUnicode(":three:");
    public static final String fourUnicode = EmojiParser.parseToUnicode(":four:");

    public static void setPrefix(String newPrefix) {
        if (newPrefix != null) {
            prefix = newPrefix;
        }
    }
}
