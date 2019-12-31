package me.discordThomas.JarvisBot.commands.moderation;

import me.discordThomas.JarvisBot.commands.api.Categories;
import me.discordThomas.JarvisBot.commands.api.Command;
import me.discordThomas.JarvisBot.utils.CustomPermission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class ClearCommand extends Command {
	public ClearCommand() {
		super("clear");
		maxArgs = 2;
		description = "A command to clear the chat! | If no args specified, clears 10 messages in current channel. Usage: `.clear (amount) (channel)`";
		category = Categories.MODERATE;
		permission = CustomPermission.MODERATOR;
	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		switch (args.size()) {
			case 0:
				event.getTextChannel().getHistory().retrievePast(11).queue(messages -> messages.forEach(message -> {
					if(message != null) {
						message.delete().queue();
					}
				}));
				break;

			case 1:
				String amount = args.get(0);
				try {
					int amountInt = Integer.parseInt(amount);
					event.getTextChannel().getHistory().retrievePast(amountInt + 1).queue(messages -> messages.forEach(message -> {
								if(message != null) {
									message.delete().queue();
								}
					}));
				} catch (Exception ex) {
					event.getChannel().sendMessage("Something went wrong. Try again!").queue();
				}
				break;

			case 2:
				String amountString = args.get(0);
				String channel = args.get(1);
				TextChannel textChannel = null;
				if(event.getMessage().getMentionedChannels().size() != 0) {
					textChannel = event.getMessage().getMentionedChannels().get(0);
				} else if(event.getMessage().getMentionedChannels().size() >= 1){
					textChannel = event.getGuild().getTextChannelsByName(channel, true).get(0);
				}

				if(textChannel == null) {
					event.getChannel().sendMessage("Error finding channel. Try again: `.clear (amount) (channel)`").queue();
					return;
				}
				try {
					int amountInt = Integer.parseInt(amountString);
					textChannel.getHistory().retrievePast(amountInt).queue(messages -> messages.forEach(message -> {
						if(message != null) {
							message.delete().queue();
						}
					}));
				} catch (Exception ex) {
					event.getChannel().sendMessage("Something went wrong. Try again!").queue();
				}
				break;
		}
	}
}
