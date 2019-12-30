package me.discordThomas.JarvisBot.listeners;

import me.discordThomas.JarvisBot.commands.api.CommandManager;
import me.discordThomas.JarvisBot.commands.api.HelpCommand;
import me.discordThomas.JarvisBot.commands.developer.ShardsCommand;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;

public class onReady extends ListenerAdapter {

	@Override
	public void onReady(ReadyEvent event) {
		event.getJDA().getPresence().setActivity(Activity.playing(" on " + event.getGuildTotalCount() + " guilds with " + event.getJDA().getShardInfo().getShardTotal() + " shards."));


	}
}
