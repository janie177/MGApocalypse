package com.minegusta.mgapocalypse.traps.instances;

import com.minegusta.mgapocalypse.traps.ITrap;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public class FireTrap implements ITrap {
    @Override
    public void apply(Player p, Sign s) {

        p.setFireTicks(20 * 5);
    }

    @Override
    public String getMessage() {
        return ChatColor.DARK_RED + "A trap sets you on fire!";
    }

    @Override
    public int getCooldownTime() {
        return 6;
    }
}
