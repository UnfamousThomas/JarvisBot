package me.discordThomas.JarvisBot.commands.useful.info;

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
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserInfoCommand extends Command {
    public UserInfoCommand(){
        super("userinfo");
        minArgs = 0;
        maxArgs = 1;
        description = "Tells you info about a user. | Usage: `" + DataFields.prefix + "userinfo (username / id)`";
        category =  Categories.USEFUL;
        permission = CustomPermission.MEMBER;

    }
    @Override
    public void run(Member m, List<String> args, MessageReceivedEvent event) {
        switch (args.size()) {
            case 0:
                event.getChannel().sendMessage(Userinfo(event.getMember(), event.getGuild())).queue();
                break;
            case 1:
                if(event.getMessage().getMentionedMembers().size() > 0) {
                    Member mentioned = event.getMessage().getMentionedMembers().get(0);
                    event.getChannel().sendMessage(Userinfo(mentioned, event.getGuild())).queue();
                } else {
                    try {
                        long userID = Long.parseLong(args.get(0));
                        Member target = event.getGuild().getMemberById(userID);
                        if(target == null) {
                            event.getChannel().sendMessage("User not found. Try again.").queue();
                            return;
                        }
                        event.getChannel().sendMessage(Userinfo(target, event.getGuild())).queue();
                    } catch (Exception ex) {
                        String username = args.get(0);
                        if(!(event.getGuild().getMembersByName(username, true).size() > 0)) {
                            event.getChannel().sendMessage("User not found. Try again.").queue();
                            return;
                        }
                        Member target = event.getGuild().getMembersByName(username, true).get(0);
                        if(target != null) {
                            try {
                                event.getChannel().sendMessage(Userinfo(target, event.getGuild())).queue();
                            } catch (Exception e) {
                                event.getChannel().sendMessage("Something went wrong! Try again.").queue();
                            }
                        } else {
                            event.getChannel().sendMessage("Could not find user. Try again!").queue();
                        }
                    }
                }
        }
    }

    private MessageEmbed Userinfo(Member m, Guild g) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(m.getEffectiveName() + " Info");
        builder.addField("Created At", m.getUser().getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME), true);
        builder.addField("Joined At",m.getTimeJoined().format(DateTimeFormatter.RFC_1123_DATE_TIME), false);
        if(m.getTimeBoosted() != null) builder.addField("Time boosted", m.getTimeBoosted().format(DateTimeFormatter.RFC_1123_DATE_TIME), true);
        builder.addBlankField(false);
        builder.setThumbnail(m.getUser().getEffectiveAvatarUrl());
        builder.setColor(Color.decode("#3498db"));
        builder.setFooter(m.getEffectiveName(), g.getIconUrl());
        return builder.build();
    }

    private String convertVerificationLevel(Guild.VerificationLevel verificationLevel) {
        String[] names = verificationLevel.name().toLowerCase().split("_");
        StringBuilder out = new StringBuilder();

        for(String name : names) {
            out.append(Character.toUpperCase(name.charAt(0))).append(name.substring(1)).append(" ");
        }

        return out.toString().trim();
    }


}
