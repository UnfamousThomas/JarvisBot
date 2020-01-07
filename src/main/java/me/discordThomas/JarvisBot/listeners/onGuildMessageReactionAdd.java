package me.discordThomas.JarvisBot.listeners;

import me.discordThomas.JarvisBot.commands.fun.dailyfact.Animal;
import me.discordThomas.JarvisBot.utils.DataFields;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;

public class onGuildMessageReactionAdd extends ListenerAdapter {

	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {


		if (event.getUser().isBot()) return;

		if (DataFields.unicodeMap.get(event.getUserIdLong()) != null && DataFields.unicodeMap.get(event.getUserIdLong()) == event.getMessageIdLong()) {
			event.getChannel().sendMessage(event.getReactionEmote().getAsCodepoints()).queue();
			//System.out.println(EmojiParser.parseFromUnicode(event.getReactionEmote().getEmoji(), ));
			return;
		}

		if (DataFields.factsMap.get(event.getUserIdLong()) != null && DataFields.factsMap.get(event.getUserIdLong()) == event.getMessageIdLong()) {
			if (event.getReactionEmote().getAsCodepoints().equals("U+1f411")) {
				//Sheep:
				fact(Animal.CHICKEN, event.getMember());
			}

			if (event.getReactionEmote().getAsCodepoints().equals("U+1f991")) {
				//Squuid:
				fact(Animal.SQUID, event.getMember());

			}

			if (event.getReactionEmote().getAsCodepoints().equals("U+1f414")) {
				//Chicken:
				fact(Animal.CHICKEN, event.getMember());

			}
			event.getReaction().removeReaction(event.getUser()).queue();
		}


	}

	private void fact(Animal animal, Member m) {
		if (DataFields.factsStringMap.get(animal) != null) {
			String fact = DataFields.factsStringMap.get(animal);
			m.getUser().openPrivateChannel().queue(channel -> {
				channel.sendMessage("Your daily fact for the animal " + animal.name().toLowerCase() + " is:").queue();
				channel.sendMessage(fact).queue();
			});
		} else {
			m.getUser().openPrivateChannel().queue(channel -> {
				channel.sendMessage("Fact about " + animal.name().toLowerCase() + " not found.").queue();
			});
		}
	}

}
