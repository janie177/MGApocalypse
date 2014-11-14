package com.minegusta.mgapocalypse.dotmanagers;

import com.minegusta.mgapocalypse.util.TempData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class DiseaseManager
{
    public static void infect(Player p)
    {
        p.sendMessage(ChatColor.RED + "You don't feel so well...");
        TempData.diseaseMap.put(p.getUniqueId().toString(), System.currentTimeMillis());
    }

    public static void cure(Player p)
    {
        if(TempData.diseaseMap.containsKey(p.getUniqueId().toString()))
        {
            p.sendMessage(ChatColor.GREEN + "You feel cured!");
            TempData.diseaseMap.remove(p.getUniqueId().toString());
        }
    }
}
