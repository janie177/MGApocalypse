package com.minegusta.mgapocalypse.Tasks;

import com.google.common.collect.Maps;
import com.minegusta.mgapocalypse.Main;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.concurrent.ConcurrentMap;

public class PerkMenuTask
{
    public static ConcurrentMap<Inventory, Boolean> invs = Maps.newConcurrentMap();

    private static int id = -1;

    public static void start()
    {
        id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.PLUGIN, ()->
        {
            invs.keySet().stream().forEach(inv ->
            {
                if(inv.getViewers().size() == 0) invs.remove(inv);
                else
                {
                    for(int i = 45; i < 54; i++)
                    {
                        int newDurability = inv.getItem(i).getDurability() + 1;
                        if(newDurability > 15) newDurability = 1;
                        inv.getItem(i).setDurability((short) newDurability);
                    }
                }
            });
        }, 5, 5);
    }

    public static void stop()
    {
        if (id != -1)
        {
            Bukkit.getScheduler().cancelTask(id);
        }
    }
}
