package com.minegusta.mgapocalypse.traps.instances;

import com.minegusta.mgapocalypse.Main;
import com.minegusta.mgapocalypse.traps.ITrap;
import com.minegusta.mgapocalypse.util.WorldCheck;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class EntityRainTrap implements ITrap {
    @Override
    public boolean apply(Player p, Sign s) {

        int duration = 1;
        EntityType type = EntityType.ARROW;

        try
        {
            if(s.getLine(2).length() != 0) duration = Integer.parseInt(s.getLine(2));
            if(s.getLine(3).length() != 0) type = EntityType.valueOf(s.getLine(3));
        } catch (Exception igored){}

        if(!type.isSpawnable())
        {
            return false;
        }

        startRain(duration, p, type);
        return true;
    }

    private void startRain(int duration, final Player p, EntityType type) {
        for (int i = 0; i <= duration * 4; i++) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () ->
            {
                if(p == null || !p.isOnline() || !WorldCheck.is(p.getWorld()))return;
                p.getWorld().spawnEntity(p.getLocation(), type);
            }, 5 * i);
        }
    }

    @Override
    public String getMessage() {
        return ChatColor.RED + "Something starts raining down on you!";
    }

    @Override
    public int getCooldownTime() {
        return 30;
    }
}
