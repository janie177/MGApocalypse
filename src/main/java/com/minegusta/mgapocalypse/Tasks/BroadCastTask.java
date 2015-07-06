package com.minegusta.mgapocalypse.Tasks;

import com.minegusta.mgapocalypse.Main;
import com.minegusta.mgapocalypse.util.WorldCheck;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BroadCastTask {
    private static final String[] messages =
            {
                    "Need a world map? " + ChatColor.DARK_BLUE + "http://www.minegusta.com/map.php",
                    "You are playing Wasteland! To return to the hub, use: " + ChatColor.YELLOW + "/Hub",
                    "To see your stats, use : " + ChatColor.YELLOW + "/Info",
                    "Upgrade your perks! Use: " + ChatColor.YELLOW + "/Perk",
                    "Want cool donor perks? " + ChatColor.YELLOW + "http://store.minegusta.com"
            };
    private static int index = 0;

    public static int start() {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.PLUGIN, new Runnable() {
            @Override
            public void run() {
                for (String w : WorldCheck.getWorlds()) {
                    for (Player p : Bukkit.getWorld(w).getPlayers()) {
                        p.sendMessage(ChatColor.RED + "- - - " + ChatColor.GOLD + "WasteLand" + ChatColor.RED + " - - -");
                        p.sendMessage(messages[index]);
                        p.sendMessage(ChatColor.RED + "- - - - - - - - -");
                        index++;
                        if (index + 1 > messages.length) index = 0;
                    }
                }
            }
        }, 20 * 30, 20 * 180);
    }
}

