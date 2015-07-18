package com.minegusta.mgapocalypse.util;

import com.google.common.collect.Maps;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.LivingEntity;

import java.util.concurrent.ConcurrentMap;

public class SpawnLocationFinder
{

    private static ConcurrentMap<Location, Location> locations = Maps.newConcurrentMap();

    public static Location get(Sign trap, LivingEntity ent)
    {
        if(!locations.containsKey(trap.getLocation()))
        {
            add(trap, ent);
        }
        return locations.get(trap.getLocation());
    }

    private static void add(Sign trap, LivingEntity ent)
    {
        int radius = 12;
        for(int x = -radius; x <= radius; x++)
        {
            for(int y = trap.getY() + 2; y <= trap.getY() + 6; y++)
            {
                for(int z = -radius; z <= radius; z++)
                {
                    Block b = trap.getBlock().getRelative(x, y, z);
                    if(b.getType() == Material.AIR && ent.getLocation().distance(b.getLocation()) > 9)
                    {
                        locations.put(trap.getLocation(), b.getLocation());
                        return;
                    }
                }
            }
        }
        locations.put(trap.getLocation(), new Location(trap.getLocation().getWorld(), trap.getLocation().getX() + 8, trap.getLocation().getY() + 3, trap.getLocation().getZ() + 8));
    }
}
