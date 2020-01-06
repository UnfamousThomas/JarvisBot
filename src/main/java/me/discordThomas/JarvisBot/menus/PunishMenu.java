package me.discordThomas.JarvisBot.menus;


import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.menu.Menu;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class PunishMenu extends Menu {
    protected PunishMenu(Set<User> users, Set<Role> roles, String data) {
        super(new EventWaiter(), users, roles, 2,TimeUnit.MINUTES);
    }

    @Override
    public void display(MessageChannel messageChannel) {
        messageChannel.sendMessage("test plz").queue();
    }

    @Override
    public void display(Message message) {

    }
}
