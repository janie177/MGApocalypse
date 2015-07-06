package com.minegusta.mgapocalypse.Tasks;

import com.minegusta.mgapocalypse.MGApocalypse;
import com.minegusta.mgapocalypse.Main;
import com.minegusta.mgapocalypse.files.MGPlayer;
import com.minegusta.mgapocalypse.util.WGManager;
import com.minegusta.mgapocalypse.util.WorldCheck;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class DrinkTask {
    public static int start() {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.PLUGIN, () -> {

            for (MGPlayer mgp : MGApocalypse.getMGPlayers()) {
                Player p = mgp.getPlayer();

                if (!mgp.getIfPlaying() || !WorldCheck.is(p.getWorld())) continue;
                if (p.getLevel() == 0) {
                    if (WGManager.canGetDamage(p)) {
                        p.damage(1.0);
                        p.sendMessage(ChatColor.RED + "You are dying of thirst!");
                    }
                } else {
                    p.setLevel(p.getLevel() - 1);
                    switch (p.getLevel()) {
                        case 10: {
                            p.sendMessage(ChatColor.RED + "You are beginning to feel thirsty.");
                        }
                        break;
                        case 5: {
                            p.sendMessage(ChatColor.RED + "Your own pee is starting to look tempting..");
                        }
                        break;
                        case 1: {
                            p.sendMessage(ChatColor.RED + "You need water soon!");
                        }
                        break;
                        case 0: {
                            p.sendMessage(ChatColor.RED + "You are completely dehydrated. Drink water!");
                        }
                        break;
                    }
                }
            }
        }, 20 * 10, 20 * 38);
    }
}
