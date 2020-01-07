package com.discordapp.JarvisBot.listeners;

import com.discordapp.JarvisBot.punishments.PunishManager;
import com.discordapp.JarvisBot.utils.DataFields;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class onReady extends ListenerAdapter {

	@Override
	public void onReady(ReadyEvent event) {
		//event.getJDA().getPresence().setActivity(Activity.of(Activity.ActivityType.DEFAULT, ".help | Using " + event.getJDA().getShardInfo().getShardTotal() + " shards."));

		DataFields.guildsList.addAll(event.getJDA().getGuilds());

		event.getJDA().getGuilds().forEach(guild -> {
			PunishManager punishManager = new PunishManager(guild);
			DataFields.managerHashMap.put(guild.getIdLong(), punishManager);
		});
	}


}
