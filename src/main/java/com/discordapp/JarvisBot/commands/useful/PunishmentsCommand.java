package com.discordapp.JarvisBot.commands.useful;

import com.discordapp.JarvisBot.commands.api.Categories;
import com.discordapp.JarvisBot.commands.api.Command;
import com.discordapp.JarvisBot.punishments.PunishManager;
import com.discordapp.JarvisBot.punishments.objects.PunishmentObject;
import com.discordapp.JarvisBot.utils.CustomPermission;
import com.discordapp.JarvisBot.utils.DataFields;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PunishmentsCommand extends Command {
    public PunishmentsCommand() {
        super("punishments");
        minArgs = 0;
        maxArgs = 0;
        usage ="todo";
        description = "todo";
        aliases = alias("mypun", "punishmen", "mypuni");
        permission = CustomPermission.MEMBER;
        category = Categories.USEFUL;
    }

    @Override
    public void run(Member m, List<String> args, MessageReceivedEvent event) {
        PunishManager manager = DataFields.managerHashMap.get(event.getGuild().getIdLong());

        if(!manager.userPunishments.get(m.getIdLong()).isEmpty()) {
            List<PunishmentObject> objects = new ArrayList<>(manager.userPunishments.get(m.getIdLong()));

            m.getUser().openPrivateChannel().queue(channel -> channel.sendMessage(makeEmbed(objects, event.getGuild(), false).build()).queue());
        } else {
            m.getUser().openPrivateChannel().queue(channel -> channel.sendMessage(makeEmbed(null, event.getGuild(), true).build()).queue());
        }

    }

    private EmbedBuilder makeEmbed(List<PunishmentObject> list, Guild guild, boolean empty) {

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setThumbnail(guild.getIconUrl());
        embedBuilder.setColor(Color.pink);
        embedBuilder.setTitle("Previous punishments - " + guild.getName());

        if(!empty) {
            StringBuilder stringBuilder = new StringBuilder();
            for (PunishmentObject punishmentObject : list) {
                stringBuilder.append("\n").append("ID:").append(punishmentObject.getId()).append(" - ").append(punishmentObject.getDegree().getName()).append(": ").append(punishmentObject.getReason()).append(" - Appealed: ").append(punishmentObject.isAppealed());
                if (punishmentObject.isAppealed()) {
                    stringBuilder.append(", reason:").append(punishmentObject.getAppealedReason()).append(" by: ").append(punishmentObject.getAppealer());
                }
            }
            embedBuilder.addField("Punishments", stringBuilder.toString(), false);
        } else {
            embedBuilder.addField("Punishments", "No punishments found!", false);
        }

        return embedBuilder;
    }
}
