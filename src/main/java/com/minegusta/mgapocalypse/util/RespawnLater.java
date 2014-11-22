package com.minegusta.mgapocalypse.util;

import com.minegusta.mgapocalypse.Main;
import com.minegusta.mgapocalypse.config.DefaultConfig;
import com.minegusta.mgapocalypse.kills.ZombieKills;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
                p.sendMessage(ChatColor.GREEN + "You had " + ChatColor.DARK_PURPLE + ZombieKills.get(p) + ChatColor.GREEN + " zombie kills.");
                int points = ZombieKills.get(p) / 2;
                p.sendMessage(ChatColor.GOLD + "You earned a total of " + ChatColor.YELLOW + points + ChatColor.GOLD + " credits.");
                ZombieKills.set(p, 0);
            }
        }, 20);
    }

}

