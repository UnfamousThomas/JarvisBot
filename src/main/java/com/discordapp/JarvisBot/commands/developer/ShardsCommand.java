package com.discordapp.JarvisBot.commands.developer;

import com.discordapp.JarvisBot.commands.api.Categories;
import com.discordapp.JarvisBot.utils.DataFields;
import com.discordapp.JarvisBot.commands.api.Command;
import com.discordapp.JarvisBot.utils.CustomPermission;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class ShardsCommand extends Command {


	public ShardsCommand() {
		super("shards");
		minArgs = 0;
		maxArgs = 0;
		description = "A command to view shard info.";
		usage = "`" + DataFields.prefix + "shards`";
		category = Categories.DEVELOPER;
		permission = CustomPermission.DEV;
	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent event) {
		event.getChannel().sendMessage(shardMaker(event.getGuild(), event.getJDA().getShardInfo().getShardId(), event.getJDA().getShardInfo().getShardTotal())).queue();
	}

	private MessageEmbed shardMaker(Guild g, int shardID, int totalshards) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("Shard Info");
		builder.addField("Shard ID", String.valueOf(shardID), true);
		builder.addField("Total shards", String.valueOf(totalshards), true);
		builder.setColor(Color.decode("#3498db"));
		builder.setFooter(g.getName(), g.getIconUrl());
		return builder.build();
	}

}
