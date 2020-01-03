package me.discordThomas.JarvisBot.commands.developer;

import me.discordThomas.JarvisBot.commands.api.Categories;
import me.discordThomas.JarvisBot.commands.api.Command;
import me.discordThomas.JarvisBot.utils.CustomPermission;
import me.discordThomas.JarvisBot.utils.DataFields;
import me.discordThomas.JarvisBot.utils.mysql.MySQLManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class BlacklistCommand extends Command {
	public BlacklistCommand() {
		super("blacklist");
		minArgs = 2;
		description = "Modifies blacklisted people in the database. | Usage: `" + DataFields.prefix + "blaclist [add/remove] [userID] (reason)`";
		usage = "`" + DataFields.prefix + "blaclist [add/remove] [userID] (reason)`";
		category = Categories.DEVELOPER;
		permission = CustomPermission.DEV;
	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		String type = args.get(0).toLowerCase();

		switch (type) {
			case "add":
				try {
					long userID = Long.parseLong(args.get(1));
					args.remove(0);
					args.remove(0);
					String reason = String.join(" ", args);
					MySQLManager.execute("INSERT INTO `blacklisted_users` (`userid`, `reason`) VALUES (?, ?)",
							userID,
							reason);

					DataFields.blacklistedPeopleList.put(userID, reason);
					event.getChannel().sendMessage("User has been blacklisted.").queue();
				} catch (Exception ex) {
					event.getChannel().sendMessage("Something went wrong. Try again!").queue();
				}
				break;

			case "remove":
				try {
					long userID = Long.parseLong(args.get(1));
					MySQLManager.execute("DELETE FROM `blacklisted_users` WHERE userid=?", userID);
					DataFields.blacklistedPeopleList.remove(userID);

					event.getChannel().sendMessage("Blacklisted user has been removed.").queue();

				} catch (Exception ex) {
					event.getChannel().sendMessage("Something went wrong. Try again!").queue();
				}
		}
	}
}
