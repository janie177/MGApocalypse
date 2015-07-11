package com.minegusta.mgapocalypse.traps.instances;

import com.minegusta.mgapocalypse.traps.ITrap;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public class UFire implements ITrap{
    @Override
    public boolean apply(Player p, Sign s) {

        int duration = 5;

        try {
            if(s.getLine(2).length() != 0) duration = Integer.parseInt(s.getLine(2));
        } catch (Exception ignored){}

        p.setFireTicks(20 * duration);
        p.getWorld().spigot().playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 0, 0, 2, 2, 2, 1/5, 30, 40);
        return true;
    }

    @Override
    public String getMessage() {
        return ChatColor.DARK_RED + "A trap sets you on fire!";
    }

    @Override
    public int getCooldownTime() {
        return 0;
    }
}
