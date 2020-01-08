package com.discordapp.JarvisBot.punishments;

import com.discordapp.JarvisBot.punishments.objects.PunishmentObject;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PunishmentCalculator {

	private static PunishmentCalculator instance;

	public Map<Long, String> getPunishment(List<PunishmentObject> previousPunishments, Guild guild, Member m) {
		Map<Long, String> punishmentList = new HashMap<>();

		previousPunishments.forEach(punishment -> {
		});

		return punishmentList;
	}
	public static PunishmentCalculator getInstance() {
		if(instance == null) instance = new PunishmentCalculator();
		return instance;
	}
}
