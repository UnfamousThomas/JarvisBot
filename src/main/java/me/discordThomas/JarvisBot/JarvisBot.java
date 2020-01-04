package me.discordThomas.JarvisBot;

import me.discordThomas.JarvisBot.commands.admin.LeaveCommand;
import me.discordThomas.JarvisBot.commands.api.CommandManager;
import me.discordThomas.JarvisBot.commands.api.HelpCommand;
import me.discordThomas.JarvisBot.commands.bothelper.AddCommand;
import me.discordThomas.JarvisBot.commands.developer.*;
import me.discordThomas.JarvisBot.commands.fun.DadJokeCommand;
import me.discordThomas.JarvisBot.commands.fun.JokeCommand;
import me.discordThomas.JarvisBot.commands.fun.dailyfact.DailyFactsCommand;
import me.discordThomas.JarvisBot.commands.moderation.ClearCommand;
import me.discordThomas.JarvisBot.commands.useful.InviteCommand;
import me.discordThomas.JarvisBot.commands.useful.PingCommand;
import me.discordThomas.JarvisBot.commands.useful.info.BotInfoCommand;
import me.discordThomas.JarvisBot.commands.useful.info.BotVersionCommand;
import me.discordThomas.JarvisBot.commands.useful.info.ServerInfoCommand;
import me.discordThomas.JarvisBot.commands.useful.info.UserInfoCommand;
import me.discordThomas.JarvisBot.listeners.GuildEventsListener;
import me.discordThomas.JarvisBot.listeners.onGuildMessageReactionAdd;
import me.discordThomas.JarvisBot.listeners.onReady;
import me.discordThomas.JarvisBot.utils.DataFields;
import me.discordThomas.JarvisBot.utils.LoadingMethods;
import me.discordThomas.JarvisBot.utils.Logger;
import me.discordThomas.JarvisBot.utils.ReadPropertyFile;
import me.discordThomas.JarvisBot.utils.mysql.MySQLManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class JarvisBot {
	public static String[] devids = null;

	public static void main(String[] args) throws Exception {
		List<JDA> instanceList = new ArrayList<>();
		try {
			ReadPropertyFile readPropertyFile = new ReadPropertyFile();
			Map<String, String> results = readPropertyFile.getPropValues();
			String key = results.get("key");
			String pass = results.get("mysqlpass");
			int shards = Integer.parseInt(results.get("shards"));
			devids = results.get("devs").split(",");
			DataFields.setPrefix(results.get("prefix"));
			JDABuilder shardBuilder = new JDABuilder(key);
			CommandManager.init(shardBuilder);
			shardBuilder.setStatus(OnlineStatus.ONLINE);
			shardBuilder.addEventListeners(new onReady());
			shardBuilder.addEventListeners(new onGuildMessageReactionAdd());
			shardBuilder.addEventListeners(new GuildEventsListener());
			CommandManager.registerCommands(
					new ShardsCommand(),
					new HelpCommand(),
					new DailyFactsCommand(),
					new ServerInfoCommand(),
					new UnicodeCommand(),
					new JokeCommand(),
					new DadJokeCommand(),
					new ClearCommand(),
					new PingCommand(),
					new HelperCommand(),
					new AddCommand(),
					new InviteCommand(),
					new LeaveCommand(),
					new UserInfoCommand(),
					new BotInfoCommand(),
					new BlacklistCommand(),
					new BotVersionCommand(),
					new ListCommand()

			);
			for (int i = 0; i < shards; i++) {
				shardBuilder.useSharding(i, shards);
				instanceList.add(shardBuilder.build());
				//TODO: IMPLEMENT MUSIC (LAVAPLAYER)
				//TODO: ONCE WE HAVE OVER 1k GUILDS - Implement usage of Lavalink to use multiple nodes for music
			}

			MySQLManager.init("164.132.207.169", "JarvisDC", "Jarvis", pass);
			MySQLManager.createTable("daily_facts", "id INT NOT NULL AUTO_INCREMENT , animal TEXT NOT NULL , fact TEXT NOT NULL , date DATE NOT NULL , PRIMARY KEY (id)");
			MySQLManager.createTable("normal_jokes", "`id` INT NOT NULL AUTO_INCREMENT , `text` TEXT NOT NULL , PRIMARY KEY (`id`)");
			MySQLManager.createTable("dad_jokes", "`id` INT NOT NULL AUTO_INCREMENT , `text` TEXT NOT NULL , PRIMARY KEY (`id`)");
			MySQLManager.createTable("bothelpers", " `id` INT NOT NULL AUTO_INCREMENT , `userid` BIGINT NOT NULL , `username` TEXT NOT NULL , PRIMARY KEY (`id`)");
			MySQLManager.createTable("guildbot_settings", " `id` INT NOT NULL AUTO_INCREMENT , `guild` BIGINT NOT NULL , `settings` TEXT NOT NULL , `value` TEXT NOT NULL , PRIMARY KEY (`id`)");
			MySQLManager.createTable("blacklisted_users", "`id` INT NOT NULL AUTO_INCREMENT , `userid` BIGINT NOT NULL , `reason` TEXT NOT NULL , PRIMARY KEY (`id`)");
			MySQLManager.createTable("bot_version", " `version` INT NOT NULL");
			new LoadingMethods().loadDadJokes();
			new LoadingMethods().loadNormalJokes();
			new LoadingMethods().loadAnimals();

			MySQLManager.select("SELECT * FROM bothelpers", resultSet -> {
				while (resultSet.next()) {
					DataFields.botHelperList.put(resultSet.getLong("userid"), resultSet.getString("username"));
				}
			});
			AtomicInteger version = new AtomicInteger();
			MySQLManager.select("SELECT * FROM bot_version", resultSet -> {
				if (resultSet.next()) {
					version.set(resultSet.getInt("version"));

					DataFields.version = "#" + (version.get() + 1);
					MySQLManager.execute("UPDATE bot_version SET version=?",
							(version.get() + 1));
				}
			});
		} catch (Exception e) {
			Logger.log(Logger.Level.ERROR, "An error occured starting up.", e);
		} finally {
			instanceList.forEach(jda -> jda.getPresence().setActivity(Activity.of(Activity.ActivityType.LISTENING, ".help | Using " + jda.getShardInfo().getShardTotal() + " shards.")));
		}
	}

}
