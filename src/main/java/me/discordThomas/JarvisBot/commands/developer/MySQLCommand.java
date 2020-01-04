package me.discordThomas.JarvisBot.commands.developer;

import me.discordThomas.JarvisBot.commands.api.Categories;
import me.discordThomas.JarvisBot.commands.api.Command;
import me.discordThomas.JarvisBot.utils.CustomPermission;
import me.discordThomas.JarvisBot.utils.DataFields;
import me.discordThomas.JarvisBot.utils.mysql.MySQLManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class MySQLCommand extends Command {

	public MySQLCommand() {
		super("sql");
		aliases = alias("mysql", "db");
		description = "Used to interact with sql";
		usage = DataFields.prefix + "sql" + "[type] [query]";
		minArgs = 2;
		permission = CustomPermission.DEV;
		category = Categories.DEVELOPER;
	}
	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		String type = args.get(0);
		switch (type.toLowerCase()) {
			case "select":
				String good = getWithoutType(args);
				try {
					System.out.println(good);
					MySQLManager.select(good, resultSet -> {
						StringBuilder builder = new StringBuilder().append("```");
						if(resultSet != null) {
							resultSet.last();
							builder.append("Query for: ").append(good).append(" returned ").append(resultSet.getRow()).append(" results\n");
						} else {
							builder.append("Query for: ").append(good).append(" could not be executed (no rows found)");
						}
						builder.append("```");
						event.getChannel().sendMessage(builder.toString()).queue();
					});
				} catch (Exception ex) {
					event.getChannel().sendMessage("```" + "An error occured:\n" + ex.toString() + "```").queue();
				}
				break;

			case "execute":
				try  {
					StringBuilder builder = new StringBuilder().append("```");
					int count = MySQLManager.execute(getWithoutType(args));
					builder.append("Update for: ").append(getWithoutType(args)).append(" - Completed.").append("\n");

					builder.append("```");
					event.getChannel().sendMessage(builder.toString()).queue();
				} catch (Exception ex) {
					event.getChannel().sendMessage("```" + "An error occured:\n" + ex.toString() + "```").queue();
				}
		}
	}
	private String getWithoutType(List<String> args) {
		args.remove(0);
		return String.join(" ", args);
	}

}

