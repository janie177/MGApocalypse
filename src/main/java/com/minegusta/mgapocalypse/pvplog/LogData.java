package com.minegusta.mgapocalypse.pvplog;

import com.google.common.collect.Maps;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public class LogData {
    public static ConcurrentMap<String, PvpBot> logMap = Maps.newConcurrentMap();

    public static void add(Player p, PvpBot bot) {
        logMap.put(p.getUniqueId().toString(), bot);
    }

    public static void remove(Player p) {
        if (logMap.containsKey(p.getUniqueId().toString())) logMap.remove(p.getUniqueId().toString());
    }

    public static void remove(UUID uuid) {
        if (logMap.containsKey(uuid.toString())) logMap.remove(uuid.toString());
    }

    public static PvpBot get(Player p) {
        return logMap.get(p.getUniqueId().toString());
    }

    public static boolean contains(Player p) {
        return logMap.containsKey(p.getUniqueId().toString());
    }

    public static int remainingTime(Player p) {
        return logMap.get(p.getUniqueId().toString()).getRemainingTime();
    }
}
