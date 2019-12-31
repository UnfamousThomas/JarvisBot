package me.discordThomas.JarvisBot.commands.developer;

import me.discordThomas.JarvisBot.commands.api.Categories;
import me.discordThomas.JarvisBot.commands.api.Command;
import me.discordThomas.JarvisBot.utils.CustomPermission;
import me.discordThomas.JarvisBot.utils.mysql.MySQLManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class AddJokeCommand extends Command {


	public AddJokeCommand() {
		super("addjoke");
		minArgs = 3;
		description = "Adds dad jokes to the database.| Usage: `.addjoke [joke]`";
		category = Categories.DEVELOPER;
		permission = CustomPermission.DEV;
	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		StringBuilder sb = new StringBuilder();

		int i = 0;
		while (i < args.size() - 1) {
			sb.append(args.get(i));
			sb.append(" ");
			i++;
		}
		sb.append(args.get(i));

		try {
			MySQLManager.execute("INSERT INTO `normal_jokes` ( `text`) VALUES (?)",
					sb.toString());

			m.getUser().openPrivateChannel().queue(channel -> channel.sendMessage("Joke: ```" + sb.toString() + "``` has been added.").queue());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


}
