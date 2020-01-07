package me.discordThomas.JarvisBot.menu.punish;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.menu.ButtonMenu;
import com.vdurmont.emoji.EmojiParser;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.List;

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

    public InitialPunishMenu(Member m, TextChannel channel, User target, EventWaiter waiter) {
        this.target = target;
        this.channel = channel;
        this.m = m;
        this.previousPunishments = new ArrayList<>();
        this.waiter = waiter;

        builder = new ButtonMenu.Builder()
                .addChoice(EmojiParser.parseToUnicode(":one:"))
                .addChoice(EmojiParser.parseToUnicode(":two:"))
                .addChoice(EmojiParser.parseToUnicode(":three:"))
                .addChoice(EmojiParser.parseToUnicode(":four:"))
                .addChoice(EmojiParser.parseToUnicode(":five:"))
                .setText("Choose user: " + target.getName() + " degree!")
                .setEventWaiter(waiter);
    }

    public ButtonMenu getMenu() {
        builder.setAction(reactionEmote -> {
           String reaction = reactionEmote.getEmoji();

           if(reaction.equals(oneUnicode)) {
               channel.sendMessage("One").queue();
               this.degree = 1;
           }

           if(reaction.equals(twoUnicode)) {
               channel.sendMessage("two").queue();
               this.degree = 2;
           }

           if(reaction.equals(threeUnicode)) {
               channel.sendMessage("three").queue();
               this.degree = 3;
           }

           if(reaction.equals(fourUnicode)) {
               channel.sendMessage("four").queue();
               this.degree = 4;
           }

           if(reaction.equals(fiveUnicode)) {
               channel.sendMessage("five").queue();
               this.degree = 5;
           }
        });

        return builder.build();
    }


}
