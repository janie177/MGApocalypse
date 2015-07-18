package com.minegusta.mgapocalypse.util;

import com.google.common.collect.Maps;
import org.bukkit.Location;
import org.bukkit.Material;
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
        int radius = 15;
        for(int x = -radius; x <= radius; x++)
        {
            for(int y = trap.getY() + 2; y <= trap.getY() + 6; y++)
            {
                for(int z = -radius; z <= radius; z++)
                {
                    Location newLocation = trap.getLocation().clone().add(x,y,z);
                    if(newLocation.getBlock().getType() == Material.AIR && ent.getLocation().distance(newLocation) > 9)
                    {
                        locations.put(trap.getLocation(), trap.getLocation().clone().add(x,y,z));
                        return;
                    }
                }
            }
        }
        locations.put(trap.getLocation(), trap.getLocation().clone().add(10,3,10));
    }
}
