package com.minegusta.mgapocalypse.traps.instances;

import com.minegusta.mgapocalypse.Main;
import com.minegusta.mgapocalypse.traps.ITrap;
import com.minegusta.mgapocalypse.util.WorldCheck;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class SoundTrap implements ITrap {
    @Override
    public boolean apply(Player p, Sign s) {

        int duration = 1;
        Sound sound = Sound.CLICK;

        try
        {
            if(s.getLine(2).length() != 0) duration = Integer.parseInt(s.getLine(2));
            if(s.getLine(3).length() != 0) sound = Sound.valueOf(s.getLine(3));
        } catch (Exception igored){}

        startSound(duration, p, sound);
        return true;
    }

    private void startSound(int duration, final Player p, Sound sound) {
        for (int i = 0; i <= duration; i++) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () ->
            {
                if(p == null || !p.isOnline() || !WorldCheck.is(p.getWorld()))return;
                p.getWorld().playSound(p.getLocation(), sound, 10, 1);
            }, 20 * i);
        }
    }

    @Override
    public String getMessage() {
        return ChatColor.RED + "Something starts raining down on you!";
    }

    @Override
    public int getCooldownTime() {
        return 10;
    }
}
