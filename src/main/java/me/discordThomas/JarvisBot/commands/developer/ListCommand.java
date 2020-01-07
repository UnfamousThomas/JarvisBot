package me.discordThomas.JarvisBot.commands.developer;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.menu.ButtonMenu;
import com.jagrosh.jdautilities.menu.Menu;
import com.jagrosh.jdautilities.menu.Paginator;
import com.vdurmont.emoji.EmojiParser;
import me.discordThomas.JarvisBot.commands.api.Categories;
import me.discordThomas.JarvisBot.commands.api.Command;
import me.discordThomas.JarvisBot.menu.punish.InitialPunishMenu;
import me.discordThomas.JarvisBot.utils.CustomPermission;
import me.discordThomas.JarvisBot.utils.DataFields;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.PermissionException;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ListCommand extends Command {
	private final EventWaiter waiter;
	public ListCommand(EventWaiter waiter) {
		super("list");
		minArgs = 1;
		maxArgs = 1;
		description = "Modifies helpers in the database.";
		usage = "`" + DataFields.prefix + "list [type]`";
		category = Categories.DEVELOPER;
		permission = CustomPermission.DEV;

		this.waiter = waiter;
	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		String type = args.get(0);
		switch (type) {

			case "guild":
			case "guilds":
				event.getChannel().sendMessage(listGuilds()).queue();
				break;

			case "blacklisted":
			case "blacklist":
				event.getChannel().sendMessage(blackListed()).queue();
				break;

			case "bothelper":
			case "helpers":
				event.getChannel().sendMessage(helperListed()).queue();
				break;
			case "test":
				final InitialPunishMenu punishMenu = new InitialPunishMenu(event.getMember(), event.getTextChannel(), event.getAuthor(), waiter);
				punishMenu.getMenu().display(event.getTextChannel());
				break;
			default:
				event.getChannel().sendMessage("Invalid list type.").queue();
		}
	}

	private String listGuilds() {
		StringBuilder stringBuilder = new StringBuilder().append("```").append("Guilds:\n");

		DataFields.guildsList.forEach(guild -> {
			stringBuilder.append(guild.getName()).append(" - ").append(guild.getId()).append("\n");
		});
		stringBuilder.append("```");


		return stringBuilder.toString();
	}

	private String blackListed() {
		StringBuilder stringBuilder = new StringBuilder().append("```").append("Blacklisted users:\n");
		for (Map.Entry<Long, String> entry : DataFields.blacklistedPeopleList.entrySet()) {
			Long userID = entry.getKey();
			String reason = entry.getValue();
			stringBuilder.append(userID).append(" - ").append(reason).append("\n");
		}
		if (DataFields.blacklistedPeopleList.isEmpty()) {
			stringBuilder.append("No blacklisted people, YAY!");
		}
		stringBuilder.append("```");

		return stringBuilder.toString();
	}

	private String helperListed() {
		StringBuilder stringBuilder = new StringBuilder().append("```").append("Bothelpers:\n");
		;
		for (Map.Entry<Long, String> entry : DataFields.botHelperList.entrySet()) {
			Long userID = entry.getKey();
			String name = entry.getValue();
			stringBuilder.append(userID).append(" - ").append(name).append("\n");
		}
		stringBuilder.append("```");

		return stringBuilder.toString();
	}
}
