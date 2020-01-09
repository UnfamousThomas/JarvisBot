package com.discordapp.JarvisBot.listeners;

import com.discordapp.JarvisBot.punishments.PunishManager;
import com.discordapp.JarvisBot.punishments.objects.InitialPunishMenu;
import com.discordapp.JarvisBot.utils.DataFields;
import com.discordapp.JarvisBot.punishments.objects.PunishmentObject;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.*;

public class MessageEvent extends ListenerAdapter {

	public void onMessageReceived(MessageReceivedEvent e) {
		if(e.getChannel().getType() != ChannelType.TEXT) return;
		if(e.getAuthor().isBot()) return;
		if(e.getAuthor().isFake()) return;
		Long id = Objects.requireNonNull(e.getMember()).getIdLong();
		Guild guild = e.getGuild();
		Map<Long, PunishManager> hashMap = DataFields.managerHashMap;
		PunishManager punishManager = (PunishManager) hashMap.get(guild.getIdLong());

		if(punishManager.staffReason.get(id) != null) {
			String reason = e.getMessage().getContentRaw();
			InitialPunishMenu punishMenu = punishManager.staffReason.get(id);
			//Calculate duration
			if(e.getTextChannel() != punishMenu.channel) return;
			PunishmentObject object = new PunishmentObject(punishMenu.degree, punishMenu.target.getIdLong(), punishMenu.m.getIdLong(), reason, 1L, punishMenu.channel, System.currentTimeMillis(), true, null, 0);

			punishManager.staffReason.remove(id);

			punishManager.staffEvidence.put(id, object);

			e.getTextChannel().sendMessage("Please enter evidence in the form of text.").queue();
			e.getMessage().delete().queue();
			return;
		}

		if(punishManager.staffEvidence.get(id) != null) {
			PunishmentObject object = punishManager.staffEvidence.get(id);

			if(object.punishChannel != e.getTextChannel()) return;
			String evidence = e.getMessage().getContentRaw();
			object.setEvidence(evidence);
			object.setReceived(System.currentTimeMillis());
			List<PunishmentObject> objectList;
			if(punishManager.userPunishments.get(id) != null) {
				objectList = punishManager.userPunishments.get(id);
			} else {
				objectList = new ArrayList<>();
			}
			objectList.add(object);
			punishManager.userPunishments.put(object.user, objectList);

			e.getTextChannel().sendMessage("User punished.").queue();

			e.getMessage().delete().queue();

			object.setActive(true);
			object.activate();

			punishManager.staffEvidence.remove(id);
			return;
		}


	}
}
