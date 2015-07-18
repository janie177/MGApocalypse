package com.minegusta.mgapocalypse.traps.instances;

import com.minegusta.mgapocalypse.traps.ITrap;
import com.minegusta.mgapocalypse.util.RandomNumber;
import com.minegusta.mgapocalypse.util.SpawnLocationFinder;
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
        Location location = SpawnLocationFinder.get(s, p);

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

    @Override
    public String getMessage() {
        return ChatColor.DARK_GREEN + "Enemies appeared nearby!";
    }

    @Override
    public int getCooldownTime() {
        return 20;
    }
}
