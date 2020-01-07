package com.discordapp.JarvisBot.punishments.objects;

import com.discordapp.JarvisBot.punishments.PunishManager;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.menu.*;
import com.vdurmont.emoji.EmojiParser;
import com.discordapp.JarvisBot.utils.DataFields;
import com.discordapp.JarvisBot.utils.Logger;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.List;

public class InitialPunishMenu {
    public Member m;
    private List<String> previousPunishments;
    public TextChannel channel;
    public User target;
    private ButtonMenu.Builder builder;
    private EventWaiter waiter;
    public Degree degree;

    private String oneUnicode = EmojiParser.parseToUnicode(":one:");
    private String twoUnicode = EmojiParser.parseToUnicode(":two:");
    private String threeUnicode = EmojiParser.parseToUnicode(":three:");
    private String fourUnicode = EmojiParser.parseToUnicode(":four:");
    private String fiveUnicode = EmojiParser.parseToUnicode(":five:");
    private String newspaperUnicode = EmojiParser.parseToUnicode(":newspaper:");

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
               this.degree = Degree.WARNING;
           }
           if(reaction.equals(oneUnicode)) {
               Logger.log(Logger.Level.INFO, "1");
               this.degree = Degree.DEGREE1;
           }

           if(reaction.equals(twoUnicode)) {
               Logger.log(Logger.Level.INFO, "2");
               this.degree = Degree.DEGREE2;
           }

           if(reaction.equals(threeUnicode)) {
               Logger.log(Logger.Level.INFO, "3");
               this.degree = Degree.DEGREE3;
           }

           if(reaction.equals(fourUnicode)) {
               Logger.log(Logger.Level.INFO, "4");
               this.degree = Degree.DEGREE4;
           }

           if(reaction.equals(fiveUnicode)) {
               Logger.log(Logger.Level.INFO, "5");
               this.degree = Degree.DEGREE5;
           }
        });

        builder.setFinalAction(message -> {
            PunishManager manager = DataFields.managerHashMap.get(channel.getGuild().getIdLong());

            manager.staffReason.put(m.getIdLong(), this);

            channel.sendMessage("Please enter a reason.").queue();
        });

        return builder.build();
    }


}