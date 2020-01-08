package com.discordapp.JarvisBot.commands.moderation;

import com.discordapp.JarvisBot.commands.api.Categories;
import com.discordapp.JarvisBot.commands.api.Command;
import com.discordapp.JarvisBot.punishments.objects.InitialPunishMenu;
import com.discordapp.JarvisBot.utils.CustomPermission;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class PunishCommand extends Command {
	EventWaiter waiter;
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
	//TODO: MAKE IT WORK GOODER
	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		Member member;
		if(event.getMessage().getMentionedMembers().size() == 1) {
			member = event.getMessage().getMentionedMembers().get(0);
			sendMenu(m, event.getTextChannel(), member.getUser(), waiter);
			return;
		}
		else if(event.getGuild().getMemberById(args.get(0)) != null) {
			member = event.getGuild().getMemberById(args.get(0));
			sendMenu(m, event.getTextChannel(), member.getUser(), waiter);
			return;
		} else {
			String name = StringUtils.substring(args.get(0), 0, args.size() - 5);
			String discriminator = args.get(0).substring(args.get(0).length() - name.length());
			discriminator = discriminator.substring(1);
			member = event.getGuild().getMemberByTag(name, discriminator);
			sendMenu(m, event.getTextChannel(), member.getUser(), waiter);
		}
		if(member == null) {
			event.getChannel().sendMessage("Error finding user.").queue();
		} else {
			final InitialPunishMenu punishMenu = new InitialPunishMenu(event.getMember(), event.getTextChannel(), member.getUser(), waiter);
			punishMenu.getMenu().display(event.getTextChannel());
		}
	}

	public void sendMenu(Member m, TextChannel tc, User target, EventWaiter waiter) {
		final InitialPunishMenu punishMenu = new InitialPunishMenu(m, tc, target, waiter);
		punishMenu.getMenu().display(tc);
	}
}
