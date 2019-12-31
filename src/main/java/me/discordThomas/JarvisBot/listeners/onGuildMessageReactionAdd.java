package me.discordThomas.JarvisBot.listeners;

import me.discordThomas.JarvisBot.commands.api.CommandManager;
import me.discordThomas.JarvisBot.utils.DataFields;
import me.discordThomas.JarvisBot.utils.mysql.MySQLManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class onGuildMessageReactionAdd extends ListenerAdapter {

	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {



		if(event.getUser().isBot()) return;

		if(DataFields.unicodeMap.get(event.getUserIdLong()) != null && DataFields.unicodeMap.get(event.getUserIdLong()) == event.getMessageIdLong()) {
			event.getChannel().sendMessage(event.getReactionEmote().getAsCodepoints()).queue();
			return;
		}

		if(DataFields.factsMap.get(event.getUserIdLong()) != null && DataFields.factsMap.get(event.getUserIdLong()) == event.getMessageIdLong()) {
			if(event.getReactionEmote().getAsCodepoints().equals("U+1f411")) {
				//Sheep:
				fact("Sheep", event.getMember());
			}

			if(event.getReactionEmote().getAsCodepoints().equals("U+1f991")) {
				//Squuid:
				fact("Squid", event.getMember());

			}

			if(event.getReactionEmote().getAsCodepoints().equals("U+1f414")) {
				//Chicken:
				fact("Chicken", event.getMember());

			}
			event.getReaction().removeReaction(event.getUser()).queue();
		}


	}

	public void fact(String animal, Member m) {
		animal = animal.toUpperCase();

		String finalAnimal = animal;
		MySQLManager.select("Select * from daily_facts WHERE animal=? AND date=CURRENT_DATE", resultSet -> {
			if(resultSet.next()) {
			String fact = resultSet.getString("fact");
			m.getUser().openPrivateChannel().queue(channel -> {
				channel.sendMessage("Your daily fact for the animal " + finalAnimal.toLowerCase() + " is:").queue();
				channel.sendMessage(fact).queue();
			});
		} else {
				m.getUser().openPrivateChannel().queue(channel -> {
					channel.sendMessage("Fact about " + finalAnimal + " not found.").queue();
				});
			}

		}, animal);
	}
}
