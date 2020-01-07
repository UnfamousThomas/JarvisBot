package me.discordThomas.JarvisBot.menu.punish;

import java.util.HashMap;

public class PunishManager {
    private static PunishManager instance;

    public static PunishManager getInstance() {

        if(instance == null) instance = new PunishManager();

        return instance;
    }
}
