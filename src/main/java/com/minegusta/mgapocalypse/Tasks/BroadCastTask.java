package com.minegusta.mgapocalypse.Tasks;

import com.minegusta.mgapocalypse.Main;
import com.minegusta.mgapocalypse.util.WorldCheck;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BroadCastTask
{
    public static int start()
    {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.PLUGIN, new Runnable() {
            @Override
            public void run()
            {
                for(String w : WorldCheck.getWorlds())
                {
                    for(Player p : Bukkit.getWorld(w).getPlayers())
                    {
                        p.sendMessage(ChatColor.RED +"- - - " + ChatColor.GOLD + "WasteLand" + ChatColor.RED + " - - -");
                        p.sendMessage(ChatColor.GRAY + "You are playing Wasteland! To return to the hub, use:");
                        p.sendMessage(ChatColor.YELLOW + "/Hub");
                        p.sendMessage(ChatColor.RED + "- - - - - - - - -");
                    }
                }
            }
        },20 * 30, 20 * 120);
    }
}

