package com.minegusta.mgapocalypse.traps;

import com.minegusta.mgapocalypse.traps.instances.ZombieSpawn;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public enum Trap {

    ZOMBIE(new ZombieSpawn());

    private ITrap trap;

    private Trap(ITrap trap)
    {
        this.trap = trap;
    }

    public void apply(Player p, Sign sign)
    {
        trap.apply(p, sign);
    }

    public String getMessage()
    {
        return trap.getMessage();
    }

    public int getCooldownTime()
    {
        return trap.getCooldownTime();
    }

    public boolean isCooledDown(Location l)
    {
        return trap.isCooledDown(l);
    }

    public void startCooldown(Location l)
    {
        trap.startCooldown(l);
    }

}
