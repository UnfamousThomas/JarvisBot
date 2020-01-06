package me.discordThomas.JarvisBot.utils;

import java.util.concurrent.TimeUnit;

public class Time {

    public static String format(long milliseconds) {
        StringBuilder stringBuilder = new StringBuilder();
        long uptime = milliseconds;
        long days = TimeUnit.MILLISECONDS.toDays(uptime);
        uptime -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(uptime);
        uptime -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(uptime);
        uptime -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(uptime);
        if (days > 0)
            stringBuilder.append(days).append("d");
        if (hours > 0)
            stringBuilder.append(hours).append("h");
        if (minutes > 0)
            stringBuilder.append(minutes).append("m");
        if (seconds > 0)
            stringBuilder.append(seconds).append("s");
        return stringBuilder.toString();
    }
}
