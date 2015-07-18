package com.minegusta.mgapocalypse.traps.instances;

import com.minegusta.mgapocalypse.traps.ITrap;
import com.minegusta.mgapocalypse.util.RandomNumber;
import com.minegusta.mgapocalypse.util.SpawnLocationFinder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class UProjectileTrap implements ITrap {
    @Override
    public boolean apply(Player p, Sign s) {

        int amount = 1;
        EntityType type = EntityType.ARROW;

        try
        {
            if(s.getLine(2).length() != 0) amount = Integer.parseInt(s.getLine(2));
            if(s.getLine(3).length() != 0) type = EntityType.valueOf(s.getLine(3));

        } catch (Exception igored){}

        Location old = SpawnLocationFinder.get(s, p);
        Location location = new Location(old.getWorld(), old.getX(), old.getY(), old.getZ());
        location.add(0,2,0);

        if(!type.isSpawnable())
        {
            return false;
        }

        for (int i = 0; i < amount; i++)
        {
            Entity ent = p.getWorld().spawnEntity(location, type);

            double x = p.getLocation().getX() - ent.getLocation().getX();
            double y = p.getLocation().getY() - ent.getLocation().getY();
            double z = p.getLocation().getZ() - ent.getLocation().getZ();

            Vector v = new Vector(x, y, z);
            v.normalize();

            ent.setVelocity(v.multiply(2.4));

            if(ent instanceof Fireball)
            {
                Fireball ball = (Fireball) ent;
                ball.setIsIncendiary(false);
            }
        }
        return true;
    }

    @Override
    public String getMessage() {
        return ChatColor.RED + "Projectiles have been launched at you!";
    }

    @Override
    public int getCooldownTime() {
        return 0;
    }
}
