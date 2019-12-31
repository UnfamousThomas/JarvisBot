package me.discordThomas.JarvisBot.listeners;

import me.discordThomas.JarvisBot.commands.api.CommandManager;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class onGuildMessageReactionAdd extends ListenerAdapter {

	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {

		if(event.getUser().isBot()) return;
		if(CommandManager.instance.unicodeMap.get(event.getUserIdLong()) != null && CommandManager.instance.unicodeMap.get(event.getUserIdLong()) == event.getMessageIdLong()) {
			event.getChannel().sendMessage(event.getReactionEmote().getAsCodepoints()).queue();
			return;
		}

		if(CommandManager.instance.factsMap.get(event.getUserIdLong()) != null && CommandManager.instance.factsMap.get(event.getUserIdLong()) == event.getMessageIdLong()) {
			if(event.getReactionEmote().getAsCodepoints().equals("U+1f411")) {
				//Sheep:

			}

			if(event.getReactionEmote().getAsCodepoints().equals("U+1f991")) {
				//Squuid:
			}

			if(event.getReactionEmote().getAsCodepoints().equals("U+1f414")) {
				//Chicken:

			}
			event.getReaction().removeReaction(event.getUser()).queue();
		}


	}
}
