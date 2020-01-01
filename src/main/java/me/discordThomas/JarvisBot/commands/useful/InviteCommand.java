package me.discordThomas.JarvisBot.commands.useful;

import me.discordThomas.JarvisBot.commands.api.Categories;
import me.discordThomas.JarvisBot.commands.api.Command;
import me.discordThomas.JarvisBot.utils.CustomPermission;
import me.discordThomas.JarvisBot.utils.DataFields;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class InviteCommand extends Command {
	public InviteCommand() {
		super("invite");
		description = "Get the bots invite. | Usage: `" + DataFields.prefix + "invite`";
		category = Categories.USEFUL;
		permission = CustomPermission.MEMBER;
	}
	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		event.getChannel().sendMessage(inviteBuilder().build()).queue();
	}

	public EmbedBuilder inviteBuilder() {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setColor(Color.decode("#3498db"));
		builder.setTitle("Jarvis Invite");
		builder.addField("Invite:", "[Link](https://discordapp.com/api/oauth2/authorize?client_id=658780975496691716&permissions=8&scope=bot)", false);
		builder.setColor(Color.decode("#6a9a1f"));
		return builder;
	}
}
