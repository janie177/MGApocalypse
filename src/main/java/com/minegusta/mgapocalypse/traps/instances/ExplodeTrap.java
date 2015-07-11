package com.minegusta.mgapocalypse.traps.instances;

import com.minegusta.mgapocalypse.traps.ITrap;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public class ExplodeTrap implements ITrap {
    @Override
    public boolean apply(Player p, Sign s) {
        int strength = 3;
        try {
            if(s.getLine(2).length() != 0) strength = Integer.parseInt(s.getLine(2));

        } catch (Exception ignored){}

        s.getWorld().createExplosion(s.getX(), s.getY(), s.getZ(), strength, false, false);
        return true;
    }

    @Override
    public String getMessage() {
        return ChatColor.DARK_RED + "Boom!";
    }

    @Override
    public int getCooldownTime() {
        return 120;
    }
}
