package com.minegusta.mgapocalypse.traps.instances;

import com.minegusta.mgapocalypse.traps.ITrap;
import com.minegusta.mgapocalypse.util.RandomNumber;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class MobTrap implements ITrap
{
    @Override
    public void apply(Player p, Sign s)
    {
        int amount = 3;
        EntityType type = EntityType.ZOMBIE;
        Location location = new Location(p.getWorld(), p.getLocation().getX() + RandomNumber.get(30) - 15, p.getLocation().getY(), p.getLocation().getZ() + RandomNumber.get(30) - 15);

        try
        {
            if(s.getLine(3).length() != 0) type = EntityType.valueOf(s.getLine(3));
            if(s.getLine(2).length() != 0) amount = Integer.parseInt(s.getLine(2));
        } catch (Exception ignored){}

        if(!type.isAlive()) type = EntityType.ZOMBIE;

        for (int i = 0; i < amount; i++)
        {
            p.getWorld().spawnEntity(location, type);
        }
    }

    @Override
    public String getMessage() {
        return ChatColor.DARK_GREEN + "Enemies have spawned nearby!";
    }

    @Override
    public int getCooldownTime() {
        return 60;
    }
}
