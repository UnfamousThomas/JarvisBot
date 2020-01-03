package me.discordThomas.JarvisBot.listeners;

import me.discordThomas.JarvisBot.utils.DataFields;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class onReady extends ListenerAdapter {

	@Override
	public void onReady(ReadyEvent event) {
		event.getJDA().getPresence().setActivity(Activity.of(Activity.ActivityType.DEFAULT, ".help | Using " + event.getJDA().getShardInfo().getShardTotal() + " shards."));

		event.getJDA().getGuilds().forEach(guild -> {
			DataFields.guildsList.add(guild);
		});
		}
}
