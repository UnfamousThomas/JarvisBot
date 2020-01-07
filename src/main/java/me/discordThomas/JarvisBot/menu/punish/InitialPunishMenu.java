package me.discordThomas.JarvisBot.menu.punish;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.menu.*;
import com.sun.org.apache.xpath.internal.operations.Or;
import com.vdurmont.emoji.EmojiParser;
import me.discordThomas.JarvisBot.utils.Logger;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class InitialPunishMenu {
    Member m;
    List<String> previousPunishments;
    TextChannel channel;
    User target;
    ButtonMenu.Builder builder;
    EventWaiter waiter;
    Integer degree;

    String oneUnicode = EmojiParser.parseToUnicode(":one:");
    String twoUnicode = EmojiParser.parseToUnicode(":two:");
    String threeUnicode = EmojiParser.parseToUnicode(":three:");
    String fourUnicode = EmojiParser.parseToUnicode(":four:");
    String fiveUnicode = EmojiParser.parseToUnicode(":five:");
    String newspaperUnicode = EmojiParser.parseToUnicode(":newspaper:");

    public InitialPunishMenu(Member m, TextChannel channel, User target, EventWaiter waiter) {
        this.target = target;
        this.channel = channel;
        this.m = m;
        this.previousPunishments = new ArrayList<>();
        this.waiter = waiter;

        System.out.println(this.waiter);
        builder = new ButtonMenu.Builder()
                .addChoice(EmojiParser.parseToUnicode(":one:"))
                .addChoice(EmojiParser.parseToUnicode(":two:"))
                .addChoice(EmojiParser.parseToUnicode(":three:"))
                .addChoice(EmojiParser.parseToUnicode(":four:"))
                .addChoice(EmojiParser.parseToUnicode(":five:"))
                .addChoice(EmojiParser.parseToUnicode(":newspaper:"))
                .setText("Choose user: " + target.getName() + " punishment!")
                .setEventWaiter(this.waiter);
    }

    public ButtonMenu getMenu() {
        builder.setAction(reactionEmote -> {
           String reaction = reactionEmote.getEmoji();

           if(reaction.equals(newspaperUnicode)) {
               Logger.log(Logger.Level.INFO, "Warning!");
               this.degree = 0;
           }
           if(reaction.equals(oneUnicode)) {
               Logger.log(Logger.Level.INFO, "1");
               this.degree = 1;
           }

           if(reaction.equals(twoUnicode)) {
               Logger.log(Logger.Level.INFO, "2");
               this.degree = 2;
           }

           if(reaction.equals(threeUnicode)) {
               Logger.log(Logger.Level.INFO, "3");
               this.degree = 3;
           }

           if(reaction.equals(fourUnicode)) {
               Logger.log(Logger.Level.INFO, "4");
               this.degree = 4;
           }

           if(reaction.equals(fiveUnicode)) {
               Logger.log(Logger.Level.INFO, "5");
               this.degree = 5;
           }
        });

        builder.setFinalAction(message -> {
            Paginator paginator = new Paginator.Builder()
                    .addItems(this.degree + " is the degree.")
                    .setEventWaiter(this.waiter)
                    .setItemsPerPage(1)
                    .showPageNumbers(false)
                    .setText("test")
                    .setTimeout(1, TimeUnit.MINUTES)
                    .wrapPageEnds(true)
                    .setUsers(m.getUser())
                    .setColor(Color.lightGray)
                    .setColumns(1)
                    .build();
            paginator.display(message.getChannel());
        });

        return builder.build();
    }

    public int getDegree() {
        return this.degree;
    }


}