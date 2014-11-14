package com.minegusta.mgapocalypse.util;

import com.minegusta.mgapocalypse.Main;
import com.minegusta.mgapocalypse.config.DefaultConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RespawnLater
{
    private Player p;

    public RespawnLater(Player p)
    {
        this.p = p;
        start();
    }

    private void start()
    {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.PLUGIN, new Runnable() {
            @Override
            public void run() {
                p.teleport(DefaultConfig.getMainSpawn());
            }
        }, 20);
    }

}

