package com.minegusta.mgapocalypse.traps;

import com.google.common.collect.Maps;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentMap;

public class TrapManager {

    public static ConcurrentMap<Location, Long> cooldown = Maps.newConcurrentMap();

    public static void activate(Sign sign, Player p)
    {
        Trap trap = null;
        try
        {
            trap = Trap.valueOf(sign.getLine(1).toUpperCase());

        } catch (Exception ignored){}

        if(trap == null) return;

        if(trap.isCooledDown(sign.getLocation()))
        {
            trap.startCooldown(sign.getLocation());
            trap.apply(p, sign);
            p.sendMessage(trap.getMessage());
        }
    }
}
