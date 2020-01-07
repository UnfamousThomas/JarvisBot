package com.discordapp.JarvisBot.commands.developer;

import com.discordapp.JarvisBot.commands.api.Categories;
import com.discordapp.JarvisBot.commands.api.Command;
import com.discordapp.JarvisBot.utils.CustomPermission;
import com.discordapp.JarvisBot.utils.DataFields;
import com.discordapp.JarvisBot.utils.mysql.MySQLManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class HelperCommand extends Command {


	public HelperCommand() {
		super("helper");
		minArgs = 2;
		maxArgs = 3;
		description = "Modifies helpers in the database.";
		usage = "`" + DataFields.prefix + "helper [add/remove] [userID] (name)`";
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
					String name = args.get(2);

					MySQLManager.execute("INSERT INTO `bothelpers` (`userid`, `username`) VALUES (?, ?)",
							userID,
							name);

					DataFields.botHelperList.put(userID, name);
					event.getChannel().sendMessage("Bot helper has been added.").queue();
				} catch (Exception ex) {
					event.getChannel().sendMessage("Something went wrong. Try again!").queue();
				}
				break;

			case "remove":
				try {
					long userID = Long.parseLong(args.get(1));

					MySQLManager.execute("DELETE FROM `bothelpers` WHERE userid=?", userID);
					DataFields.botHelperList.remove(userID);
					event.getChannel().sendMessage("Bot helper has been removed.").queue();

				} catch (Exception ex) {
					event.getChannel().sendMessage("Something went wrong. Try again!").queue();

				}
		}
	}
}
