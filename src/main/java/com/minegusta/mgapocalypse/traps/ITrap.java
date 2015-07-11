package com.minegusta.mgapocalypse.traps;

import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public interface ITrap
{
    boolean apply(Player p, Sign s);

    String getMessage();

    int getCooldownTime();

    default boolean isCooledDown(Location l)
    {
        if(!TrapManager.cooldown.containsKey(l))return true;
        if(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - TrapManager.cooldown.get(l)) > getCooldownTime())
        {
            TrapManager.cooldown.remove(l);
            return true;
        }
        return false;
    }

    default void startCooldown(Location l)
    {
        TrapManager.cooldown.put(l, System.currentTimeMillis());
    }
}
