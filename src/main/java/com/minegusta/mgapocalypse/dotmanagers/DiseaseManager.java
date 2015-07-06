package com.minegusta.mgapocalypse.dotmanagers;

import com.minegusta.mgapocalypse.MGApocalypse;
import com.minegusta.mgapocalypse.files.MGPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class DiseaseManager {
    public static void infect(Player p) {
        MGPlayer mgp = MGApocalypse.getMGPlayer(p);
        p.sendMessage(ChatColor.RED + "You don't feel so well...");
        mgp.setInfected(true);
    }

    public static void cure(Player p) {
        MGPlayer mgp = MGApocalypse.getMGPlayer(p);
        if (mgp.isInfected()) {
            p.sendMessage(ChatColor.GREEN + "You feel cured!");
            mgp.setInfected(false);
        }
    }
}
