package com.minegusta.mgapocalypse.util;

import com.minegusta.mgapocalypse.Main;
import com.minegusta.mgapocalypse.config.DefaultConfig;
import com.minegusta.mgapocalypse.config.SavedLocationsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Break
{
    private Player p;
    private int TASK, TASK2;
    private double health;
    private double x;
    private double y;
    private double z;

    public Break(Player p)
    {
        this.p = p;
        this.x = p.getLocation().getX();
        this.y = p.getLocation().getY();
        this.z = p.getLocation().getZ();
        this.health = p.getHealth();
    }


    public void start()
    {
        p.sendMessage(ChatColor.DARK_GREEN + "- - - - - - - - - - - - - - - -");
        p.sendMessage(ChatColor.GREEN + "You will be returned to the spawn in 10 seconds.");
        p.sendMessage(ChatColor.GREEN + "Your location will be saved.");
        p.sendMessage(ChatColor.GREEN + "Please do not move or fight.");
        p.sendMessage(ChatColor.DARK_GREEN + "- - - - - - - - - - - - - - - -");

        startCountdown();
    }

    private void startCountdown()
    {
        TASK = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.PLUGIN, new Runnable() {
            @Override
            public void run()
            {
                Bukkit.getScheduler().cancelTask(TASK2);
                if(Math.abs(health - p.getHealth()) > 1 || Math.abs(x - p.getLocation().getX()) > 2 || Math.abs(y - p.getLocation().getY()) > 2 || Math.abs(z - p.getLocation().getZ()) > 2)
                {
                    SavedLocationsManager.setLocation(p.getUniqueId(), p.getLocation());
                    p.sendMessage(ChatColor.DARK_GREEN + "- - - - - - - - - - - - - - - -");
                    p.sendMessage(ChatColor.GREEN + "You have returned to the spawn.");
                    p.sendMessage(ChatColor.GREEN + "Your location will be saved.");
                    p.sendMessage(ChatColor.GREEN + "Please do not move or fight.");
                    p.sendMessage(ChatColor.DARK_GREEN + "- - - - - - - - - - - - - - - -");
                    p.teleport(DefaultConfig.getMainSpawn());
                }
                else
                {
                    p.sendMessage(ChatColor.DARK_GREEN + "- - - - - - - - - - - - - - - -");
                    p.sendMessage(ChatColor.GREEN + "You either moved or changed health.");
                    p.sendMessage(ChatColor.GREEN + "Your location has not been saved.");
                    p.sendMessage(ChatColor.GREEN + "Please try again and don't perform actions.");
                    p.sendMessage(ChatColor.DARK_GREEN + "- - - - - - - - - - - - - - - -");
                }
                TempData.breakMap.remove(p.getUniqueId().toString());
            }
        }, 20 * 10);

        TASK2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.PLUGIN, new Runnable() {
            @Override
            public void run()
            {
                p.sendMessage(ChatColor.GRAY + "Preparing teleport...");
            }
        }, 0, 20);
    }

    public void cancel()
    {
        Bukkit.getScheduler().cancelTask(TASK);
        Bukkit.getScheduler().cancelTask(TASK2);
        p.sendMessage(ChatColor.DARK_GREEN + "- - - - - - - - - - - - - - - -");
        p.sendMessage(ChatColor.GREEN + "Returning to hub was cancelled.");
        p.sendMessage(ChatColor.GREEN + "You are not allowed to move or receive damage");
        p.sendMessage(ChatColor.GREEN + "during this process. Please try again.");
        p.sendMessage(ChatColor.DARK_GREEN + "- - - - - - - - - - - - - - - -");
        TempData.breakMap.remove(p.getUniqueId().toString());
    }
}
