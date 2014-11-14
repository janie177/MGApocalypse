package com.minegusta.mgapocalypse.dotmanagers;


import com.minegusta.mgapocalypse.util.TempData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BleedingManager
{
    public static void bleed(Player p)
    {
        p.sendMessage(ChatColor.RED + "Ouch! You're bleeding!");
        TempData.bleedingMap.put(p.getUniqueId().toString(), System.currentTimeMillis());
    }

    public static void bandage(Player p, boolean heal)
    {
        if(heal && (p.getMaxHealth() > p.getHealth()))p.setHealth(p.getHealth() + 1.0);
        if(TempData.bleedingMap.containsKey(p.getUniqueId().toString()))
        {
            TempData.bleedingMap.remove(p.getUniqueId().toString());
        }
    }
}
