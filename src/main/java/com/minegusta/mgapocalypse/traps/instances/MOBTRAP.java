package com.minegusta.mgapocalypse.traps.instances;

import com.minegusta.mgapocalypse.traps.ITrap;
import com.minegusta.mgapocalypse.util.RandomNumber;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.*;

public class MobTrap implements ITrap
{
    @Override
    public boolean apply(Player p, Sign s)
    {
        int amount = 3;
        EntityType type = EntityType.ZOMBIE;
        Location location = new Location(p.getWorld(), p.getLocation().getX() + RandomNumber.get(20) - 10, p.getLocation().getY(), p.getLocation().getZ() + RandomNumber.get(20) - 10);

        try
        {
            if(s.getLine(3).length() != 0) type = EntityType.valueOf(s.getLine(3));
            if(s.getLine(2).length() != 0) amount = Integer.parseInt(s.getLine(2));
        } catch (Exception ignored){}

        if(!type.isAlive()) type = EntityType.ZOMBIE;

        if(!type.isSpawnable())
        {
            return false;
        }


        for (int i = 0; i < amount; i++)
        {
            Entity spawned = p.getWorld().spawnEntity(location, type);
            if(type == EntityType.WOLF)
            {
                ((Wolf)spawned).setAngry(true);
            }
        }
        return true;
    }

    @Override
    public String getMessage() {
        return ChatColor.DARK_GREEN + "Enemies have spawned nearby!";
    }

    @Override
    public int getCooldownTime() {
        return 20;
    }
}
