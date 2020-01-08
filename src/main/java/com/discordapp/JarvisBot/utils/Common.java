package com.discordapp.JarvisBot.utils;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.apache.commons.lang3.StringUtils;


public class Common {
	private static Common instance;

	public Member getUser(Message message, TextChannel textChannel, String target) {
		Member member;
		if(message.getMentionedMembers().size() == 1) {
			member = message.getMentionedMembers().get(0);
			return member;
		}
		else if(parseLong(target)) {
			member = textChannel.getGuild().getMemberById(Long.parseLong(target));
			if(member == null) {
				textChannel.sendMessage("Error finding user.").queue();
			return null;
			} else {
				return member;
			}

		} else {
			try {
				String name = StringUtils.substring(target, 0, target.length() - 5);
				String discriminator = target.substring(name.length());
				discriminator = discriminator.substring(1);
				System.out.println(name + " " + discriminator);
				member = textChannel.getGuild().getMemberByTag(name, discriminator);

				if(member == null) {
					textChannel.sendMessage("Error finding user.").queue();
					return null;
				} else {
					return member;
				}
			} catch (Exception ignored) {
				textChannel.sendMessage("Error finding user.").queue();
				return null;
			}
		}
	}

	private boolean parseLong(String arg) {
		try {
			Long.parseLong(arg);
			return true;
		} catch (Exception ignored) {
			return false;
		}
	}

	public static Common getInstance() {
		if(instance == null) {
			instance = new Common();
		}

 		return instance;
	}
}
