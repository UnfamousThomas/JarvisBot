package com.discordapp.JarvisBot.commands.moderation;

import com.discordapp.JarvisBot.commands.api.Categories;
import com.discordapp.JarvisBot.commands.api.Command;
import com.discordapp.JarvisBot.punishments.objects.InitialPunishMenu;
import com.discordapp.JarvisBot.utils.Common;
import com.discordapp.JarvisBot.utils.CustomPermission;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class PunishCommand extends Command {
	private EventWaiter waiter;
	public PunishCommand(EventWaiter waiter) {
		super("punish");
		aliases = alias("p", "pu");
		category = Categories.MODERATE;
		description = "Punish rulebreakers.";
		usage = "punish";
		permission = CustomPermission.DEV;
		minArgs = 1;
		maxArgs = 1;
		this.waiter = waiter;
	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		Member member = Common.getInstance().getUser(event.getMessage(), event.getTextChannel(), args.get(0));

		if(member != null) {
			sendMenu(m, event.getTextChannel(), member.getUser(), waiter);
		}
	}

	private void sendMenu(Member m, TextChannel tc, User target, EventWaiter waiter) {
		final InitialPunishMenu punishMenu = new InitialPunishMenu(m, tc, target, waiter);
		punishMenu.getMenu().display(tc);
	}

}
