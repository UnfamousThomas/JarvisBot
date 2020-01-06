package me.discordThomas.JarvisBot.commands.moderation;

import me.discordThomas.JarvisBot.commands.api.Categories;
import me.discordThomas.JarvisBot.commands.api.Command;
import me.discordThomas.JarvisBot.utils.CustomPermission;
import me.discordThomas.JarvisBot.utils.DataFields;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ClearCommand extends Command {

	public ClearCommand() {
		super("clear");
		maxArgs = 2;
		description = "A command to clear the chat! | If no args specified, clears 10 messages in current channel.";
		usage = "`" + DataFields.prefix + "clear (amount) (channel)`";
		category = Categories.MODERATE;
		permission = CustomPermission.MODERATOR;
	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		switch (args.size()) {
			case 0:
				event.getTextChannel().getHistory().retrievePast(11).queue(messages -> messages.forEach(message -> {
					if (message != null) {
						message.delete().queue();
					}
				}));
				event.getChannel().sendMessage("11 messages have been deleted from this channel.").queue(message -> message.delete().queueAfter(1, TimeUnit.MINUTES));
				break;
			case 1:
				String amount = args.get(0);
				try {
					int amountInt = Integer.parseInt(amount);
					event.getTextChannel().getHistory().retrievePast(amountInt + 1).queue(messages -> messages.forEach(message -> {
						if (message != null) {
							message.delete().queue();
						}
					}));
					event.getChannel().sendMessage(amount + " messages have been deleted from " + event.getTextChannel().getAsMention()).queue(message -> message.delete().queueAfter(1, TimeUnit.MINUTES));

				} catch (Exception ex) {
					event.getChannel().sendMessage("Something went wrong. Try again!").queue();
				}
				break;
			case 2:
				String amountString = args.get(0);
				String channel = args.get(1);
				TextChannel textChannel = null;
				if (event.getMessage().getMentionedChannels().size() != 0) {
					textChannel = event.getMessage().getMentionedChannels().get(0);
				} else if (event.getMessage().getMentionedChannels().size() >= 1) {
					textChannel = event.getGuild().getTextChannelsByName(channel, true).get(0);
				}

				if (textChannel == null) {
					event.getChannel().sendMessage("Error finding channel. Try again: `.clear (amount) (channel)`").queue();
					return;
				}
				try {
					int amountInt = Integer.parseInt(amountString);
					textChannel.getHistory().retrievePast(amountInt).queue(messages -> messages.forEach(message -> {
						if (message != null) {
							message.delete().queue();
						}
					}));

					event.getChannel().sendMessage(amountString + " messages have been deleted from " + textChannel.getAsMention()).queue(message -> message.delete().queueAfter(1, TimeUnit.MINUTES));
				} catch (Exception ex) {
					event.getChannel().sendMessage("Something went wrong. Try again!").queue();
				}
				break;
		}
	}
}
