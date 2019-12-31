package me.discordThomas.JarvisBot.commands.useful;

import me.discordThomas.JarvisBot.commands.api.Categories;
import me.discordThomas.JarvisBot.commands.api.Command;
import me.discordThomas.JarvisBot.utils.CustomPermission;
import me.discordThomas.JarvisBot.utils.DataFields;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class InfoCommand extends Command {
    public InfoCommand(){
        super("info");
        minArgs = 0;
        maxArgs = 0;
        description = "Tells you info about the bot. | Usage: `" + DataFields.prefix + "info`";
        category =  Categories.USEFUL;
        permission = CustomPermission.MEMBER;

    }
    @Override
    public void run(Member m, List<String> args, MessageReceivedEvent event) {
        event.getChannel().sendMessage(info(event.getGuild(), event.getJDA().getShardInfo().getShardTotal())).queue();

    }

    private MessageEmbed info(Guild g, int totalshards) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Server Info");
        builder.addField("Creators","UnfamousThomas#9462\n" + "\nSheep#3771", true);
        builder.addBlankField(false);
        builder.addField("Version","0.0.2", true);
        builder.addField("Shards", String.valueOf(totalshards), true);
        builder.setColor(Color.decode("#3498db"));
        builder.setFooter( g.getName(), g.getIconUrl());
        return builder.build();
    }

}
