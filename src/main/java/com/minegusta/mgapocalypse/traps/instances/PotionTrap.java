package com.minegusta.mgapocalypse.traps.instances;

import com.minegusta.mgapocalypse.traps.ITrap;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionTrap implements ITrap {
    @Override
    public boolean apply(Player p, Sign s) {
        int duration = 7;
        PotionEffectType type = PotionEffectType.BLINDNESS;

        try
        {
            if(s.getLine(3).length() != 0) type = PotionEffectType.getByName(s.getLine(3));
            if(s.getLine(2).length() != 0) duration = Integer.parseInt(s.getLine(2));
        } catch (Exception ignored){}

        p.addPotionEffect(new PotionEffect(type, duration * 20, 0, false, true));
        return true;
    }

    @Override
    public String getMessage() {
        return ChatColor.GOLD + "A potion is sprayed on you!";
    }

    @Override
    public int getCooldownTime() {
        return 30;
    }
}
