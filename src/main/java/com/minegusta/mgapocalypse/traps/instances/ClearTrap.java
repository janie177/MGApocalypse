package com.minegusta.mgapocalypse.traps.instances;

import com.minegusta.mgapocalypse.traps.ITrap;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class ClearTrap implements ITrap
{
    @Override
    public boolean apply(Player p, Sign s) {
        for(PotionEffect effect : p.getActivePotionEffects())
        {
            p.removePotionEffect(effect.getType());
        }
        return true;
    }

    @Override
    public String getMessage() {
        return ChatColor.GOLD + "Your potioneffects have been removed!";
    }

    @Override
    public int getCooldownTime() {
        return 0;
    }
}
