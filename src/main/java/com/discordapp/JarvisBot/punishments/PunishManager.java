package com.discordapp.JarvisBot.punishments;

import com.discordapp.JarvisBot.punishments.objects.Degree;
import com.discordapp.JarvisBot.punishments.objects.InitialPunishMenu;
import com.discordapp.JarvisBot.punishments.objects.PunishmentObject;
import com.discordapp.JarvisBot.utils.mysql.MySQLManager;
import net.dv8tion.jda.api.entities.Guild;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PunishManager {
    public PunishManager(Guild guild) {
       Map<Long, List<PunishmentObject>> tempUserPunishments = new HashMap<>();
        MySQLManager.select("SELECT * FROM punishments WHERE guildid=?", resultSet -> {
            while (resultSet.next()) {
                List<PunishmentObject> objects;
                long staffid = resultSet.getLong("staffid");
                long userid = resultSet.getLong("userid");
                if(tempUserPunishments.containsKey(userid)) {
                    objects = tempUserPunishments.get(userid);
                } else {
                    objects = new ArrayList<>();
                }
                Degree degree = Degree.valueOf(resultSet.getString("degree"));
                String reason = resultSet.getString("reason");
                String evidence = resultSet.getString("evidence");
                int intType = resultSet.getInt("type");
                long duration = resultSet.getLong("duration");
                long received = resultSet.getLong("received");
                boolean active = resultSet.getBoolean("active");
                int id = resultSet.getInt("id");

                long total = duration + received;
                PunishmentObject object;
                if(total >= System.currentTimeMillis()) {
                    MySQLManager.execute("UPDATE punishments SET active=? WHERE id=?",
                            false,
                            id);
                    object = new PunishmentObject(degree, userid, staffid, reason, duration, null, received, false, evidence, intType);
                } else {
                    object = new PunishmentObject(degree, userid, staffid, reason, duration, null, received, active, evidence, intType);
                }
                boolean isAppealed = resultSet.getBoolean("appealed");
                String appealedBy = resultSet.getString("appealedby");
                String appeal_reason = resultSet.getString("appeal_reason");
                Long appealed_at = resultSet.getLong("appealed_at");

                object.setAppealed(isAppealed);

                object.setId(id);
                if(isAppealed) {
                    object.setAppealedReason(appeal_reason);
                    object.setAppealedtime(appealed_at);
                    object.setAppealer(appealedBy);
                }
                objects.add(object);
                tempUserPunishments.put(userid, objects);
            }

            userPunishments = tempUserPunishments;
        }, guild.getIdLong());
    }

    public Map<Long, List<PunishmentObject>> userPunishments = new HashMap<>();
    public Map<Long, InitialPunishMenu> staffReason = new HashMap<>();
    public Map<Long, PunishmentObject> staffEvidence = new HashMap<>();


}
