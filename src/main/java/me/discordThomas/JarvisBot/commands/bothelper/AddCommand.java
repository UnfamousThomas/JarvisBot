package me.discordThomas.JarvisBot.commands.bothelper;

import me.discordThomas.JarvisBot.commands.api.Categories;
import me.discordThomas.JarvisBot.commands.api.Command;
import me.discordThomas.JarvisBot.commands.fun.dailyfact.Animal;
import me.discordThomas.JarvisBot.utils.CustomPermission;
import me.discordThomas.JarvisBot.utils.DataFields;
import me.discordThomas.JarvisBot.utils.mysql.MySQLManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class AddCommand extends Command {
	public AddCommand() {
		super("add");
		minArgs = 2;
		description = "Adds things. | Usage: `" + DataFields.prefix + "add [dadjoke/joke/fact] (animal) (date) [content]`";
		category = Categories.BOTHELPER;
		permission = CustomPermission.BOTHELPER;
	}
	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		String type = args.get(0).toLowerCase();

		switch (type) {
			case "dadjoke":
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
					MySQLManager.execute("INSERT INTO `dad_jokes` ( `text`) VALUES (?)",
							sb.toString());

					m.getUser().openPrivateChannel().queue(channel -> channel.sendMessage("Dad Joke: ```" + sb.toString() + "``` has been added.").queue());

					DataFields.dadJokesList.add(sb.toString());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				break;

			case "joke":
				args.remove(0);
				StringBuilder stringBuilder = new StringBuilder();

				int j = 0;
				while (j < args.size() - 1) {
					stringBuilder.append(args.get(j));
					stringBuilder.append(" ");
					j++;
				}
				stringBuilder.append(args.get(j));

				try {
					MySQLManager.execute("INSERT INTO `normal_jokes` ( `text`) VALUES (?)",
							stringBuilder.toString());

					m.getUser().openPrivateChannel().queue(channel -> channel.sendMessage("Joke: `" + stringBuilder.toString() + "` has been added.").queue());
					DataFields.normalJokesList.add(stringBuilder.toString());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				break;

			case "fact":
				args.remove(0);

				String animal = args.get(0);
				String date = args.get(1);
				args.remove(0);
				args.remove(0);

				StringBuilder stringBuilder1 = new StringBuilder();

				int ij = 0;
				while (ij < args.size() - 1) {
					stringBuilder1.append(args.get(ij));
					stringBuilder1.append(" ");
					ij++;
				}
				stringBuilder1.append(args.get(ij));

				try {
					MySQLManager.execute("INSERT INTO `daily_facts` ( `animal`, `fact`, `date`) VALUES (?,?, DATE(?))",
							animal.toUpperCase(),
							stringBuilder1.toString(),
							date);
					//yyyy - mm - dd
					DataFields.factsStringMap.put(Animal.valueOf(animal.toUpperCase()), stringBuilder1.toString());
					m.getUser().openPrivateChannel().queue(channel -> channel.sendMessage("Fact for animal " + animal + " has been added for date: " + date + " with text: " + stringBuilder1.toString() + ".").queue());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				break;

		}
	}
}

