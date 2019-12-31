package me.discordThomas.JarvisBot.commands.fun;

import me.discordThomas.JarvisBot.commands.api.Categories;
import me.discordThomas.JarvisBot.commands.api.Command;
import me.discordThomas.JarvisBot.commands.api.CommandManager;
import me.discordThomas.JarvisBot.utils.CustomPermission;
import me.discordThomas.JarvisBot.utils.mysql.MySQLManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringEscapeUtils;

import java.awt.*;
import java.util.List;

public class JokeCommand extends Command {

    public JokeCommand(){
        super("joke");
        minArgs = 0;
        maxArgs = 0;
        aliases = alias("funny");
        description = "Tells a joke! | Usage: `.joke`";
        category =  Categories.FUN;
        permission = CustomPermission.MEMBER;

    }

    @Override
    public void run(Member m, List<String> args, MessageReceivedEvent event) {
        MySQLManager.select("SELECT * from normal_jokes ORDER BY RAND() LIMIT 1", resultSet -> {
            if(resultSet.next()) {
                event.getChannel().sendMessage(dailyFact(event.getGuild(), resultSet.getString("text"))).queue();
            } else {
                event.getChannel().sendMessage("Joke not found!").queue();
            }
        });
    }

    private MessageEmbed dailyFact(Guild g, String text) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("A funny joke!");
        builder.addField("Joke", StringEscapeUtils.unescapeJava(text), false);
        builder.setColor(Color.decode("#3498db"));
        builder.setFooter(g.getName(), g.getIconUrl());
        return builder.build();
    }

}