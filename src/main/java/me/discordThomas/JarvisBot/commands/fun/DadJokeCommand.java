package me.discordThomas.JarvisBot.commands.fun;

import me.discordThomas.JarvisBot.commands.api.Categories;
import me.discordThomas.JarvisBot.commands.api.Command;
import me.discordThomas.JarvisBot.utils.CustomPermission;
import me.discordThomas.JarvisBot.utils.DataFields;
import me.discordThomas.JarvisBot.utils.mysql.MySQLManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringEscapeUtils;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class DadJokeCommand extends Command {


	public DadJokeCommand() {
		super("dadjoke");
		minArgs = 0;
		description = "A command for dad jokes.";
		usage = "`" + DataFields.prefix + "dadjoke`";
		category = Categories.FUN;
		permission = CustomPermission.MEMBER;
	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		if(DataFields.dadJokesList.size() > 0) {
			int jokeIndex = new Random().nextInt(DataFields.dadJokesList.size());
			String jokeText = DataFields.dadJokesList.get(jokeIndex);
			event.getChannel().sendMessage(jokeEmbed(event.getGuild(), jokeText)).queue();
		}
	}

	private MessageEmbed jokeEmbed(Guild g, String text) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("A bad dad joke!");
		builder.addField("Joke", StringEscapeUtils.unescapeJava(text), false);
		builder.setColor(Color.decode("#3498db"));
		builder.setFooter(g.getName(), g.getIconUrl());
		return builder.build();
	}

}