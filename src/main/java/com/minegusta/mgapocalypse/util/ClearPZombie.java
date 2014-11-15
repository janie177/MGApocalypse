package com.minegusta.mgapocalypse.util;


import com.minegusta.mgapocalypse.Main;
import org.bukkit.Bukkit;

public class ClearPZombie
{
    private String uuid;

    public ClearPZombie(String uuid)
    {
        this.uuid = uuid;
        start();
    }

    private void start()
    {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.PLUGIN, new Runnable() {
            @Override
            public void run() {
                if(TempData.deathMap.containsKey(uuid))
                {
                    TempData.deathMap.remove(uuid);
                }
            }
        }, 20  * 120);
    }
}
