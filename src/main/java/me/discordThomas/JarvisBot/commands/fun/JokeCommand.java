package me.discordThomas.JarvisBot.commands.fun;

import me.discordThomas.JarvisBot.commands.api.Categories;
import me.discordThomas.JarvisBot.commands.api.Command;
import me.discordThomas.JarvisBot.commands.api.CommandManager;
import me.discordThomas.JarvisBot.utils.CustomPermission;
import me.discordThomas.JarvisBot.utils.DataFields;
import me.discordThomas.JarvisBot.utils.mysql.MySQLManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringEscapeUtils;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class JokeCommand extends Command {

    public JokeCommand(){
        super("joke");
        minArgs = 0;
        maxArgs = 0;
        aliases = alias("funny");
        description = "Tells a joke!";
        usage = "`" + DataFields.prefix + "joke`";
        category =  Categories.FUN;
        permission = CustomPermission.MEMBER;

    }

    @Override
    public void run(Member m, List<String> args, MessageReceivedEvent event) {
        if(DataFields.normalJokesList.size() > 0) {
            int jokeIndex = new Random().nextInt(DataFields.normalJokesList.size());
            String jokeText = DataFields.normalJokesList.get(jokeIndex);
            event.getChannel().sendMessage(jokeEmbed(event.getGuild(), jokeText)).queue();
        }
    }

    private MessageEmbed jokeEmbed(Guild g, String text) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("A funny joke!");
        builder.addField("Joke", StringEscapeUtils.unescapeJava(text), false);
        builder.setColor(Color.decode("#3498db"));
        builder.setFooter(g.getName(), g.getIconUrl());
        return builder.build();
    }

}