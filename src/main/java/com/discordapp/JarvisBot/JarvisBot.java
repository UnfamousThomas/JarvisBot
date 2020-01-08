package com.discordapp.JarvisBot;

import com.discordapp.JarvisBot.commands.admin.LeaveCommand;
import com.discordapp.JarvisBot.commands.api.CommandManager;
import com.discordapp.JarvisBot.commands.api.HelpCommand;
import com.discordapp.JarvisBot.commands.bothelper.AddCommand;
import com.discordapp.JarvisBot.commands.developer.*;
import com.discordapp.JarvisBot.commands.fun.DadJokeCommand;
import com.discordapp.JarvisBot.commands.fun.JokeCommand;
import com.discordapp.JarvisBot.commands.fun.dailyfact.DailyFactsCommand;
import com.discordapp.JarvisBot.commands.moderation.ClearCommand;
import com.discordapp.JarvisBot.commands.useful.PreviousPunishments;
import com.discordapp.JarvisBot.commands.moderation.PunishCommand;
import com.discordapp.JarvisBot.commands.moderation.mute.MuteCommand;
import com.discordapp.JarvisBot.commands.moderation.mute.MuteListener;
import com.discordapp.JarvisBot.commands.music.*;
import com.discordapp.JarvisBot.commands.useful.InviteCommand;
import com.discordapp.JarvisBot.commands.useful.PingCommand;
import com.discordapp.JarvisBot.commands.useful.info.BotInfoCommand;
import com.discordapp.JarvisBot.commands.useful.info.BotVersionCommand;
import com.discordapp.JarvisBot.commands.useful.info.ServerInfoCommand;
import com.discordapp.JarvisBot.commands.useful.info.UserInfoCommand;
import com.discordapp.JarvisBot.listeners.*;
import com.discordapp.JarvisBot.utils.DataFields;
import com.discordapp.JarvisBot.utils.LoadingMethods;
import com.discordapp.JarvisBot.utils.Logger;
import com.discordapp.JarvisBot.utils.ReadPropertyFile;
import com.discordapp.JarvisBot.utils.mysql.MySQLManager;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
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
        EventWaiter waiter;
        List<JDA> instanceList = new ArrayList<>();
        try {
            ReadPropertyFile readPropertyFile = new ReadPropertyFile();
            Map<String, String> results = readPropertyFile.getPropValues();
            String key = results.get("key");
            String pass = results.get("mysqlpass");
            int shards = Integer.parseInt(results.get("shards"));
            devids = results.get("devs").split(",");
            DataFields.setPrefix(results.get("prefix"));
            DataFields.youtubeAPIKey = results.get("youtubekey");
            JDABuilder shardBuilder = new JDABuilder(key);
            CommandManager.init(shardBuilder);
            shardBuilder.setStatus(OnlineStatus.ONLINE);
            shardBuilder.addEventListeners(new onReady());
            shardBuilder.addEventListeners(new onGuildMessageReactionAdd());
            shardBuilder.addEventListeners(new GuildEventsListener());
            shardBuilder.addEventListeners(new GuildMemberJoin());
            shardBuilder.addEventListeners(new GuildMemberLeave());
            shardBuilder.addEventListeners(new MuteListener());
            shardBuilder.addEventListeners(new MessageEvent());
            shardBuilder.addEventListeners(new VoiceChannelLeaveListener());
            waiter = new EventWaiter();
            shardBuilder.addEventListeners(waiter);
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
                    new ListCommand(waiter),
                    new MySQLCommand(),
                    new PlayCommand(),
                    new JoinCommand(),
                    new DisconnectCommand(),
                    new StopCommand(),
                    new SkipCommand(),
                    new NowPlayingCommand(),
                    new PauseCommand(),
                    new QueueCommand(),
                    new VolumeCommand(),
                    new MuteCommand(),
                    new LyricsCommand(),
                    new PunishCommand(waiter),
                    new PreviousPunishments()
            );

            MySQLManager.init("164.132.207.169", "JarvisDC", "Jarvis", pass);
            MySQLManager.createTable("daily_facts", "id INT NOT NULL AUTO_INCREMENT , animal TEXT NOT NULL , fact TEXT NOT NULL , date DATE NOT NULL , PRIMARY KEY (id)");
            MySQLManager.createTable("normal_jokes", "`id` INT NOT NULL AUTO_INCREMENT , `text` TEXT NOT NULL , PRIMARY KEY (`id`)");
            MySQLManager.createTable("dad_jokes", "`id` INT NOT NULL AUTO_INCREMENT , `text` TEXT NOT NULL , PRIMARY KEY (`id`)");
            MySQLManager.createTable("bothelpers", " `id` INT NOT NULL AUTO_INCREMENT , `userid` BIGINT NOT NULL , `username` TEXT NOT NULL , PRIMARY KEY (`id`)");
            MySQLManager.createTable("guildbot_settings", " `id` INT NOT NULL AUTO_INCREMENT , `guild` BIGINT NOT NULL , `settings` TEXT NOT NULL , `value` TEXT NOT NULL , PRIMARY KEY (`id`)");
            MySQLManager.createTable("blacklisted_users", "`id` INT NOT NULL AUTO_INCREMENT , `userid` BIGINT NOT NULL , `reason` TEXT NOT NULL , PRIMARY KEY (`id`)");
            MySQLManager.createTable("punishments", "`id` INT NOT NULL AUTO_INCREMENT , `guildid` BIGINT NOT NULL , `userid` BIGINT NOT NULL , `staffid` BIGINT NOT NULL , `degree` TEXT NOT NULL , `reason` TEXT NOT NULL , `evidence` TEXT NOT NULL , `type` INT NOT NULL , `duration` BIGINT NOT NULL, `received` BIGINT NOT NULL, `active` BOOLEAN NOT NULL, `appealed` BOOLEAN NOT NULL DEFAULT FALSE, `appealedby` TEXT NULL, `appeal_reason` TEXT NULL, `appealed_at` BIGINT NULL, PRIMARY KEY (`id`)");
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

            for (int i = 0; i < shards; i++) {
                shardBuilder.useSharding(i, shards);
                instanceList.add(shardBuilder.build());
                //TODO: ONCE WE HAVE OVER 1k GUILDS - Implement usage of Lavalink to use multiple nodes for music
            }
        } catch (Exception e) {
            Logger.log(Logger.Level.ERROR, "An error occurred starting up.", e);
        } finally {
            instanceList.forEach(jda -> jda.getPresence().setActivity(Activity.of(Activity.ActivityType.LISTENING, ".help | Using " + jda.getShardInfo().getShardTotal() + " shards.")));
        }
    }

}
