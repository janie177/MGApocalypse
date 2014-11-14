package com.minegusta.mgapocalypse.dotmanagers;


import com.minegusta.mgapocalypse.util.TempData;
import org.bukkit.entity.Player;

public class BleedingManager
{
    public static void bleed(Player p)
    {
        TempData.bleedingMap.put(p.getUniqueId().toString(), System.currentTimeMillis());
    }

    public static void bandage(Player p)
    {
        if(!(p.getMaxHealth() == p.getHealth()))p.setHealth(p.getHealth() + 1.0);
        if(TempData.bleedingMap.containsKey(p.getUniqueId().toString()))
        {
            TempData.bleedingMap.remove(p.getUniqueId().toString());
        }
    }
}
