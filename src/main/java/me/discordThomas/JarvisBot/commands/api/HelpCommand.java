package me.discordThomas.JarvisBot.commands.api;

import me.discordThomas.JarvisBot.utils.CustomPermission;
import me.discordThomas.JarvisBot.utils.DataFields;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class HelpCommand extends Command {

	public HelpCommand() {
		super("help");
		minArgs = 0;
		maxArgs = 1;
		description = "A command to view commands. | Usage: `" + DataFields.prefix + "help`";
		category = Categories.USEFUL;
		permission = CustomPermission.MEMBER;
		aliases = alias("ah");
	}
	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		event.getChannel().sendMessage("Sending you a DM!").queue();
		HelpBuilder builder = CommandManager.helpBuilder;
		EmbedBuilder embedBuilder = builder.helpBuilder(m);
		event.getMember().getUser().openPrivateChannel().queue(channel -> {
			channel.sendMessage(embedBuilder.build()).queue();
		});
	}
}
