package com.minegusta.mgapocalypse.dotmanagers;

import com.minegusta.mgapocalypse.util.TempData;
import org.bukkit.entity.Player;

public class DiseaseManager
{
    public static void infect(Player p)
    {
        TempData.diseaseMap.put(p.getUniqueId().toString(), System.currentTimeMillis());
    }

    public static void cure(Player p)
    {
        if(TempData.diseaseMap.containsKey(p.getUniqueId().toString()))
        {
            TempData.diseaseMap.remove(p.getUniqueId().toString());
        }
    }
}
