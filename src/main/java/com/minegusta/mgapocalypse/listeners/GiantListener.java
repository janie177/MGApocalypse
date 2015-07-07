package com.minegusta.mgapocalypse.listeners;

import com.google.common.collect.Lists;
import com.minegusta.mgapocalypse.MGApocalypse;
import com.minegusta.mgapocalypse.Main;
import com.minegusta.mgapocalypse.util.RandomNumber;
import com.minegusta.mgapocalypse.util.WorldCheck;
import com.minegusta.mgloot.loottables.Loot;
import com.minegusta.mgloot.loottables.LootItem;
import com.minegusta.mgloot.managers.LootManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.List;

public class GiantListener implements Listener
{
    @EventHandler
    public void onGiantDeath(EntityDeathEvent e)
    {
        if(!WorldCheck.is(e.getEntity().getWorld()))return;
        if(e.getEntity() instanceof Giant && e.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent)
        {
            EntityDamageByEntityEvent ee = (EntityDamageByEntityEvent) e.getEntity().getLastDamageCause();

            if(ee.getDamager() instanceof Player)
            {
                Player p = (Player) ee.getDamager();
                MGApocalypse.getMGPlayer(p).addGiantKills(1);
            }
            else if (ee.getDamager() instanceof Arrow && ((Arrow)ee.getDamager()).getShooter() instanceof Player)
            {
                Player p = (Player) ((Arrow) ee.getDamager()).getShooter();
                MGApocalypse.getMGPlayer(p).addGiantKills(1);
            }

            ItemStack loot = new LootManager(Loot.ultraLoot, 1).getLoot()[0];
            e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), loot);
        }
    }

    @EventHandler
    public void onGiantDamage(EntityDamageByEntityEvent e)
    {
        if(!WorldCheck.is(e.getEntity().getWorld()))return;
        if(!(e.getEntity() instanceof Giant))return;

        Player p = null;

        if(e.getDamager() instanceof Player)
        {
            p = (Player) e.getDamager();
        }

        if(e.getDamager() instanceof Arrow && ((Arrow) e.getDamager()).getShooter() instanceof Player) {
            p = (Player) ((Arrow) e.getDamager()).getShooter();
        }

        if(p == null)return;

        Giant g = (Giant) e.getEntity();

        g.setTarget(p);
        g.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 600, 0, false, false));
        attack(RandomNumber.get(7), p, g);
    }

    private void attack(int style, Player p, Giant g)
    {
        if(style < 4) spawnZombie(p, g.getLocation());
        else if (style < 7) arrow(g, p);
        else jump(g);
    }

    private void spawnZombie(Player p, Location l)
    {
        for(int i = 0; i < 3; i++)
        {
            Zombie z = (Zombie) l.getWorld().spawnEntity(l, EntityType.ZOMBIE);
            z.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 600, 0, false, false));
            z.setVillager(false);
            ((Creature)z).setTarget(p);
        }

    }

    private void jump(Giant g) {
        final Location center = g.getLocation();
        g.setVelocity(new Vector(0, 3.6, 0));

        List<Location> close = Lists.newArrayList();
        List<Location> medium = Lists.newArrayList();
        List<Location> far = Lists.newArrayList();

        for (int degrees = 0; degrees < 361; degrees = degrees + 45)
        {
            close.add(calculateCircle(center, degrees, 6));
        }

        for (int degrees = 0; degrees < 361; degrees = degrees + 45)
        {
            medium.add(calculateCircle(center, degrees, 12));
        }

        for (int degrees = 0; degrees < 361; degrees = degrees + 45)
        {
            far.add(calculateCircle(center, degrees, 18));
        }

        explode(close, 2, 4);
        explode(medium, 4, 3);
        explode(far, 4, 6);
    }
    
    public void explode(final List<Location> locations, int delay, float strength)
    {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.PLUGIN, ()->
        {
            for(Location l : locations)
            {
                l.getWorld().createExplosion(l.getX(), l.getY(), l.getZ(), strength, false, false);
            }
        }, 10* delay);
        
    }

    private void arrow(Giant g, Player p)
    {
        Location l = new Location(g.getWorld(), g.getLocation().getX(), g.getLocation().getY() + 10, g.getLocation().getZ());

        double x = p.getLocation().getX() - l.getX();
        double y = p.getLocation().getY() + 2 - l.getY();
        double z = p.getLocation().getZ() - l.getZ();

        Vector v = new Vector(x, y, z);
        v.normalize();
        v.multiply(3.0);

        Arrow arrow = g.getWorld().spawnArrow(l, v, 2, 0);
        arrow.setBounce(false);
        arrow.setCritical(true);
    }

    private static Location calculateCircle(Location l, int angle, double radius) {
        return new Location(l.getWorld(), l.getX() + radius * Math.sin(angle), l.getY(), l.getZ() + radius * Math.cos(angle));
    }
}
