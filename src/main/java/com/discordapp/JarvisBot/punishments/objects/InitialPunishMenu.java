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
    public TextChannel channel;
    public User target;
    private ButtonMenu.Builder builder;
    public Degree degree;
    private boolean cancelled;

    private String oneUnicode = EmojiParser.parseToUnicode(":one:");
    private String twoUnicode = EmojiParser.parseToUnicode(":two:");
    private String threeUnicode = EmojiParser.parseToUnicode(":three:");
    private String fourUnicode = EmojiParser.parseToUnicode(":four:");
    private String fiveUnicode = EmojiParser.parseToUnicode(":five:");
    private String newspaperUnicode = EmojiParser.parseToUnicode(":newspaper:");
    private String cancelUnicode = EmojiParser.parseToUnicode(":x:");

    public InitialPunishMenu(Member m, TextChannel channel, User target, EventWaiter waiter) {
        this.target = target;
        this.channel = channel;
        this.m = m;
        this.cancelled = false;

        builder = new ButtonMenu.Builder()
                .addChoice(EmojiParser.parseToUnicode(":one:"))
                .addChoice(EmojiParser.parseToUnicode(":two:"))
                .addChoice(EmojiParser.parseToUnicode(":three:"))
                .addChoice(EmojiParser.parseToUnicode(":four:"))
                .addChoice(EmojiParser.parseToUnicode(":five:"))
                .addChoice(EmojiParser.parseToUnicode(":newspaper:"))
                .addChoice(cancelUnicode)
                .setText("Choose users " + target.getName() + " punishment! Click " + cancelUnicode + "to cancel.")
                .setEventWaiter(waiter);
    }

    public ButtonMenu getMenu() {
        builder.setAction(reactionEmote -> {
           String reaction = reactionEmote.getEmoji();

           if(reaction.equals(newspaperUnicode)) {
               this.degree = Degree.WARNING;
           }
           if(reaction.equals(oneUnicode)) {
               this.degree = Degree.DEGREE1;
           }

           if(reaction.equals(twoUnicode)) {
               this.degree = Degree.DEGREE2;
           }

           if(reaction.equals(threeUnicode)) {
               this.degree = Degree.DEGREE3;
           }

           if(reaction.equals(fourUnicode)) {
               this.degree = Degree.DEGREE4;
           }

           if(reaction.equals(fiveUnicode)) {
               this.degree = Degree.DEGREE5;
           }

           if(reaction.equals(cancelUnicode)) {
               this.cancelled = true;
           }
        });

        builder.setFinalAction(message -> {
            if (!cancelled) {
                PunishManager manager = DataFields.managerHashMap.get(channel.getGuild().getIdLong());

            manager.staffReason.put(m.getIdLong(), this);

            channel.sendMessage("Please enter a reason.").queue();
        } else {
                channel.sendMessage("Punishment has been cancelled ):").queue();
            }
        });

        return builder.build();
    }


}