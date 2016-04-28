package com.minegusta.mgapocalypse.Tasks;

import com.minegusta.mgapocalypse.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class BroadCastTask {
    private static final String[] messages =
            {
                    "Need a world map? " + ChatColor.BLUE + "http://www.minegusta.net/wasteland.php",
                    "You are playing Wasteland! To return to the hub, use: " + ChatColor.YELLOW + "/Hub",
                    "To see your stats, use : " + ChatColor.YELLOW + "/Menu",
                    "Upgrade your perks! Use: " + ChatColor.YELLOW + "/Perk",
                    "Want cool donor perks? " + ChatColor.YELLOW + "http://store.minegusta.net",
                    "Vote for the server to earn a perkpoint! " + ChatColor.YELLOW + "http://www.minegusta.net/vote.php"
            };
    private static int index = 0;

    public static int start() {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.PLUGIN, () -> {

            Bukkit.getOnlinePlayers().forEach(p ->
            {
                p.sendMessage(ChatColor.RED + "- - - " + ChatColor.GOLD + "WasteLand" + ChatColor.RED + " - - -");
                p.sendMessage(ChatColor.LIGHT_PURPLE + messages[index]);
                p.sendMessage(ChatColor.RED + "- - - - - - - - -");
                index++;
            });
            if (index >= messages.length - 1) index = 0;
        }, 20 * 30, 20 * 180);
    }
}

