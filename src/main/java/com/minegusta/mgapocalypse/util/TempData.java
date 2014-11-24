package com.minegusta.mgapocalypse.util;

import com.google.common.collect.Maps;
import com.minegusta.mgapocalypse.scoreboards.StatusTags;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentMap;

public class TempData
{
    public static ConcurrentMap<String, Break> breakMap = Maps.newConcurrentMap();
    public static ConcurrentMap<String, Long> healMap = Maps.newConcurrentMap();
    public static ConcurrentMap<String, Integer> healerMap = Maps.newConcurrentMap();
    public static ConcurrentMap<String, Integer> killerMap = Maps.newConcurrentMap();
    public static ConcurrentMap<String, Long> healCoolDownMap = Maps.newConcurrentMap();
    public static ConcurrentMap<String, Long> diseaseMap = Maps.newConcurrentMap();
    public static ConcurrentMap<String, Long> bleedingMap = Maps.newConcurrentMap();

    public static int addKill(Player p)
    {
        String uuid = p.getUniqueId().toString();
        if(killerMap.containsKey(uuid))
        {
            int add = killerMap.get(uuid) + 1;
            killerMap.put(uuid, add);
            return add;
        }
        killerMap.put(uuid, 1);
        return 1;
    }

    public static int addHeal(Player p)
    {
        String uuid = p.getUniqueId().toString();
        if(healerMap.containsKey(uuid))
        {
            int add = healerMap.get(uuid) + 1;
            healerMap.put(uuid, add);
            return add;
        }
        healerMap.put(uuid, 1);
        return 1;
    }

    public static int getHeals(Player p)
    {
        if(healerMap.containsKey(p.getUniqueId().toString()))return healerMap.get(p.getUniqueId().toString());
        else return 0;
    }

    public static int getKills(Player p)
    {
        if(killerMap.containsKey(p.getUniqueId().toString()))return killerMap.get(p.getUniqueId().toString());
        else return 0;
    }

    public static void cleanPlayer(Player p)
    {
        String uuid = p.getUniqueId().toString();

        if(healMap.containsKey(uuid))healMap.remove(uuid);
        if(healerMap.containsKey(uuid))healerMap.remove(uuid);
        if(killerMap.containsKey(uuid))killerMap.remove(uuid);
        if(diseaseMap.containsKey(uuid))diseaseMap.remove(uuid);
        if(bleedingMap.containsKey(uuid))bleedingMap.remove(uuid);
        StatusTags.clear(p);
    }
}
