package com.discordapp.JarvisBot.commands.useful.info;

import com.discordapp.JarvisBot.commands.api.Categories;
import com.discordapp.JarvisBot.commands.api.Command;
import com.discordapp.JarvisBot.utils.Common;
import com.discordapp.JarvisBot.utils.CustomPermission;
import com.discordapp.JarvisBot.utils.DataFields;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserInfoCommand extends Command {
	public UserInfoCommand() {
		super("userinfo");
		minArgs = 0;
		maxArgs = 1;
		description = "Tells you info about a user.";
		category = Categories.USEFUL;
		permission = CustomPermission.MEMBER;
		usage = "`" + DataFields.prefix + "userinfo (username / id)`";
		aliases = alias("whoami");
	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		switch (args.size()) {
			case 0:
				event.getChannel().sendMessage(Userinfo(m, event.getGuild())).queue();
				break;
			case 1:
				Member targetMember = Common.getInstance().getUser(event.getMessage(), event.getTextChannel(), args.get(0));
				if(targetMember != null) {
					event.getChannel().sendMessage(Userinfo(targetMember, event.getGuild())).queue();
				}
		}
	}

	private MessageEmbed Userinfo(Member m, Guild g) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle(m.getEffectiveName() + " Info");
		builder.addField("Created At", m.getUser().getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME), true);
		builder.addField("Joined At", m.getTimeJoined().format(DateTimeFormatter.RFC_1123_DATE_TIME), false);
		if (m.getTimeBoosted() != null)
			builder.addField("Time boosted", m.getTimeBoosted().format(DateTimeFormatter.RFC_1123_DATE_TIME), true);
		builder.addBlankField(false);
		builder.setThumbnail(m.getUser().getEffectiveAvatarUrl());
		builder.setColor(Color.decode("#3498db"));
		builder.setFooter(m.getEffectiveName(), g.getIconUrl());
		return builder.build();
	}


}
