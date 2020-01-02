package me.discordThomas.JarvisBot.commands.fun.dailyfact;

import me.discordThomas.JarvisBot.commands.api.Categories;
import me.discordThomas.JarvisBot.commands.api.Command;
import me.discordThomas.JarvisBot.commands.api.CommandManager;
import me.discordThomas.JarvisBot.utils.CustomPermission;
import me.discordThomas.JarvisBot.utils.DataFields;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class DailyFactsCommand extends Command {

    public DailyFactsCommand(){
        super("dailyfact");
        minArgs = 0;
        maxArgs = 0;
        aliases = alias("df","fact");
        description = "Tells daily facts about the selected animal.";
        usage = "`" + DataFields.prefix + "dailyfact`";
        category =  Categories.FUN;
        permission = CustomPermission.MEMBER;

    }

    @Override
    public void run(Member m, List<String> args, MessageReceivedEvent event) {
        if(DataFields.factsMap.containsKey(event.getAuthor().getIdLong())) {
            event.getChannel().sendMessage("Previous unicode message deactivated.").queue();
        }

        event.getChannel().sendMessage("Choose which animal fact you would like to hear about.").queue();
        event.getChannel().sendMessage(dailyFact(event.getGuild())).queue(message -> {
            message.addReaction("\uD83D\uDC14").queue(); //Chicken
            message.addReaction("\uD83E\uDD91").queue(); //Squid
            message.addReaction("\uD83D\uDC11").queue(); //Sheep
            DataFields.factsMap.put(event.getAuthor().getIdLong(), message.getIdLong());
        });

    }

    private MessageEmbed dailyFact(Guild g) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Animal Selector");
        builder.addField(":chicken:","Chicken", false);
        builder.addField(":squid:","Squid", false);
        builder.addField(":sheep:","Sheep", false);

        builder.setColor(Color.decode("#3498db"));
        builder.setFooter(g.getName(), g.getIconUrl());
        return builder.build();
    }

}