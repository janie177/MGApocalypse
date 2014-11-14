package com.minegusta.mgapocalypse.util;

import com.minegusta.mgapocalypse.Main;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;

public class SmokeGrenade
{
    private final Location l;

    public SmokeGrenade(Location l)
    {
        this.l = l;
        start();
    }

    private void start()
    {
        for(int i = 0; i <= 20 * 10; i++)
        {
            if(i % 5 == 0)
            {
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.PLUGIN, new Runnable() {
                    @Override
                    public void run() {
                        l.getWorld().spigot().playEffect(l, Effect.LARGE_SMOKE, 0, 0, 3, 2, 3, 1, 130, 30);
                    }
                }, i);
            }
        }
    }


}
