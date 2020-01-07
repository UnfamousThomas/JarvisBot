package me.discordThomas.JarvisBot.menu.punish;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.List;

public class InitialPunishMenu {
    Member m;
    List<String> previousPunishments;
    TextChannel channel;
    User target;
    public InitialPunishMenu(Member m, TextChannel channel, User target) {
        this.target = target;
        this.channel = channel;
        this.m = m;
        this.previousPunishments = new ArrayList<>();
    }
}
