package me.discordThomas.JarvisBot.commands.developer;

import me.discordThomas.JarvisBot.commands.api.Categories;
import me.discordThomas.JarvisBot.commands.api.Command;
import me.discordThomas.JarvisBot.commands.api.CommandManager;
import me.discordThomas.JarvisBot.utils.CustomPermission;
import me.discordThomas.JarvisBot.utils.DataFields;
import me.discordThomas.JarvisBot.utils.mysql.MySQLManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class AddFactCommand extends Command {


	public AddFactCommand() {
		super("addfact");
		minArgs = 3;
		description = "A command to add facts! | Date format: `yyyy-mm-dd`. Usage: `" + DataFields.prefix + "addfact [animal] [date] [fact]`";
		category = Categories.BOTHELPER;
		permission = CustomPermission.BOTHELPER;
	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		String animal = args.get(0);
		String date = args.get(1);
		args.remove(0);
		args.remove(0);
		StringBuilder sb = new StringBuilder();

		int i = 0;
		while (i < args.size() - 1) {
			sb.append(args.get(i));
			sb.append(" ");
			i++;
		}
		sb.append(args.get(i));

		try {
			MySQLManager.execute("INSERT INTO `daily_facts` ( `animal`, `fact`, `date`) VALUES (?,?, DATE(?))",
					animal.toUpperCase(),
					sb.toString(),
					date);
			//yyyy - mm - dd

			m.getUser().openPrivateChannel().queue(channel -> channel.sendMessage("Fact for animal " + animal + " has been added for date: " + date + " with text: " + sb.toString() + ".").queue());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


}
