package com.minegusta.mgapocalypse.kills;

import com.google.common.collect.Maps;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentMap;

public class ZombieKills
{
    private static ConcurrentMap<String, Integer> zombieMap = Maps.newConcurrentMap();

    public static void set(Player p, int kills)
    {
        zombieMap.put(p.getUniqueId().toString(), kills);
    }

    public static void add(Player p)
    {
        int old = 0;
        if(zombieMap.containsKey(p.getUniqueId().toString())) old = zombieMap.get(p.getUniqueId().toString());
        set(p, old + 1);
        p.sendMessage(ChatColor.GOLD + "You now have " + ChatColor.DARK_PURPLE + Integer.toString(get(p)) + ChatColor.GOLD + " zombie kills.");
    }

    public static int get(Player p)
    {
        if(zombieMap.containsKey(p.getUniqueId().toString()))
        {
            return zombieMap.get(p.getUniqueId().toString());
        }
        return 0;
    }
}
