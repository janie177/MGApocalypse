package com.minegusta.mgapocalypse.traps.instances;

import com.minegusta.mgapocalypse.traps.ITrap;
import com.minegusta.mgapocalypse.util.RandomNumber;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class MobTrap implements ITrap
{
    @Override
    public boolean apply(Player p, Sign s)
    {
        int amount = 3;
        EntityType type = EntityType.ZOMBIE;
        Location location = new Location(p.getWorld(), p.getLocation().getX() + RandomNumber.get(20) - 10, p.getLocation().getY(), p.getLocation().getZ() + RandomNumber.get(20) - 10);

        location = getAir(location);
        if(location.getBlock().getType() != Material.AIR)
        {
            return false;
        }

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

    private Location getAir(Location start)
    {
        if(start.getBlock().getType() != Material.AIR)
        {
            Block b = start.getBlock();
            for(int i = 1; i < 15; i++)
            {
                Block get = b.getRelative(BlockFace.UP, i);
                if(get.getType() == Material.AIR)
                {
                    return get.getLocation();
                }
            }
        }
        return start;
    }

    @Override
    public String getMessage() {
        return ChatColor.DARK_GREEN + "Enemies appeared nearby!";
    }

    @Override
    public int getCooldownTime() {
        return 20;
    }
}
