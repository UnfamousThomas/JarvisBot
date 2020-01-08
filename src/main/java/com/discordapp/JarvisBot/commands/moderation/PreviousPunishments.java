package com.discordapp.JarvisBot.commands.moderation;

import com.discordapp.JarvisBot.commands.api.Categories;
import com.discordapp.JarvisBot.commands.api.Command;
import com.discordapp.JarvisBot.punishments.PunishManager;
import com.discordapp.JarvisBot.punishments.objects.PunishmentObject;
import com.discordapp.JarvisBot.utils.CustomPermission;
import com.discordapp.JarvisBot.utils.DataFields;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;

public class PreviousPunishments extends Command {
    public PreviousPunishments() {
        super("previouspunishments");
        minArgs = 1;
        maxArgs = 1;
        usage ="todo";
        description = "todo";
        permission = CustomPermission.DEV;
        category = Categories.MODERATE;
    }

    @Override
    public void run(Member m, List<String> args, MessageReceivedEvent event) {
        PunishManager manager = DataFields.managerHashMap.get(event.getGuild().getIdLong());

        List<PunishmentObject> objects = new ArrayList<>(manager.userPunishments.get(m.getIdLong()));

        event.getChannel().sendMessage(makeEmbed(objects).build()).queue();
    }

    private EmbedBuilder makeEmbed(List<PunishmentObject> list) {
        StringBuilder stringBuilder = new StringBuilder();

        list.forEach(punishmentObject -> {
            stringBuilder.append("\n").append(punishmentObject.getDegree().getName()).append(" ").append(punishmentObject.getReason()).append(" - ");
        });

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Previous punishments");
        embedBuilder.addField("Punishments", stringBuilder.toString(), false);

        return embedBuilder;
    }
}
