package me.discordThomas.JarvisBot;

import me.discordThomas.JarvisBot.listeners.onReady;
import me.discordThomas.JarvisBot.utils.ReadPropertyFile;
import me.discordThomas.JarvisBot.utils.mysql.MySQLManager;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;

public class JarvisBot {
	public static void main(String[] args) throws Exception {
		ReadPropertyFile properties = new ReadPropertyFile();
		String key = properties.getPropValues().get("key");
		String pass = properties.getPropValues().get("mysqlpass");
		int shards = Integer.parseInt(properties.getPropValues().get("shards"));
		JDABuilder shardBuilder = new JDABuilder(key);
		shardBuilder.setStatus(OnlineStatus.ONLINE);
		shardBuilder.addEventListeners(new onReady());

		for(int i = 0; i < shards; i++) {
			shardBuilder.useSharding(i, shards)
					.build();
		}

		MySQLManager.init("164.132.207.169", "JarvisDC", "Jarvis", pass);
		MySQLManager.createTable("guild_perms"," `guildID` BIGINT NOT NULL , `modRoles` TEXT NOT NULL , `adminRoles` TEXT NOT NULL , `ownerRoles` TEXT NOT NULL, `ownerID` BIGINT NOT NULL DEFAULT '206383620531683328'");
	}
}
