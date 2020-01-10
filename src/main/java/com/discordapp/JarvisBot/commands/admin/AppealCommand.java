package com.discordapp.JarvisBot.commands.admin;

import com.discordapp.JarvisBot.commands.api.Categories;
import com.discordapp.JarvisBot.commands.api.Command;
import com.discordapp.JarvisBot.punishments.PunishManager;
import com.discordapp.JarvisBot.utils.Common;
import com.discordapp.JarvisBot.utils.CustomPermission;
import com.discordapp.JarvisBot.utils.DataFields;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class AppealCommand extends Command {
    public AppealCommand() {
        super("appeal");
        maxArgs = 2;
        permission = CustomPermission.ADMIN;
        category = Categories.ADMIN;
    }

    @Override
    public void run(Member m, List<String> args, MessageReceivedEvent event) {

        if (Common.getInstance().parseLong(args.get(0))) {
            int id = Integer.parseInt(args.get(0));

            PunishManager manager = DataFields.managerHashMap.get(event.getGuild().getIdLong());
        }
    }
}