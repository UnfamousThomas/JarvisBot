package com.discordapp.JarvisBot.commands.moderation;

import com.discordapp.JarvisBot.commands.api.Categories;
import com.discordapp.JarvisBot.commands.api.Command;
import com.discordapp.JarvisBot.punishments.PunishManager;
import com.discordapp.JarvisBot.utils.CustomPermission;
import com.discordapp.JarvisBot.utils.DataFields;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

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

        manager.userPunishments.get(m.getIdLong()).forEach(punishmentObject -> {
            event.getChannel().sendMessage(punishmentObject.user + " - " + punishmentObject.active).queue();
        });
    }
}
