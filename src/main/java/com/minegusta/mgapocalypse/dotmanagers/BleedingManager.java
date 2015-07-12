package com.minegusta.mgapocalypse.dotmanagers;


import com.minegusta.mgapocalypse.MGApocalypse;
import com.minegusta.mgapocalypse.files.MGPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BleedingManager {
    public static void bleed(Player p) {
        MGPlayer mgp = MGApocalypse.getMGPlayer(p);
        p.sendMessage(ChatColor.RED + "Ouch! You're bleeding!");
        mgp.setBleeding(true);
    }

    public static void bandage(Player p, boolean heal) {
        MGPlayer mgp = MGApocalypse.getMGPlayer(p);

        if (heal && (p.getMaxHealth() - p.getHealth() >= 1)) p.setHealth(p.getHealth() + 1.0);

        if (mgp.isBleeding()) {
            mgp.setBleeding(false);
        }
    }
}
