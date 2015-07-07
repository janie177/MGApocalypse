package com.minegusta.mgapocalypse.listeners;

import com.minegusta.mgapocalypse.MGApocalypse;
import com.minegusta.mgapocalypse.files.MGPlayer;
import com.minegusta.mgapocalypse.perks.Perk;
import com.minegusta.mgapocalypse.perks.abilities.AirDrop;
import com.minegusta.mgapocalypse.perks.abilities.Athlete;
import com.minegusta.mgapocalypse.perks.abilities.Health;
import com.minegusta.mgapocalypse.util.ItemUtil;
import com.minegusta.mgapocalypse.util.WorldCheck;
import com.minegusta.mgloot.loottables.LootItem;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PerkListener implements Listener {

    @EventHandler
    public void onAirDrop(EntityChangeBlockEvent e) {
        if (!WorldCheck.is(e.getEntity().getWorld())) return;
        if (e.getEntity() instanceof FallingBlock && AirDrop.blocks.containsKey(e.getEntity())) {
            Location l = e.getBlock().getLocation();
            e.setCancelled(true);

            l.getWorld().spigot().playEffect(l, Effect.CLOUD, 0, 0, 1, 1, 1, 1 / 30, 50, 50);
            l.getWorld().spigot().playEffect(l, Effect.SMOKE, 0, 0, 1, 1, 1, 1 / 30, 50, 50);
            l.getWorld().playSound(l, Sound.CHEST_OPEN, 2, 2);

            for (int i = 0; i < 3; i++) {
                l.getWorld().dropItemNaturally(l, LootItem.getRandom());
                l.getWorld().playSound(l, Sound.CHICKEN_EGG_POP, 1, 1);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        boolean isWorld = WorldCheck.is(e.getPlayer().getWorld());

        MGPlayer mgp = MGApocalypse.getMGPlayer(e.getPlayer());
        Player p = e.getPlayer();

        if(mgp.getPerkLevel(Perk.ATHLETE) != 0)
        {
            if(isWorld) Athlete.setBoost(p);
            else Athlete.reset(p);
        }

        if(mgp.getPerkLevel(Perk.HEALTH) != 0)
        {
            if(isWorld) Health.setBoost(p);
            else Health.reset(p);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e)
    {
        boolean isWorld = WorldCheck.is(e.getPlayer().getWorld());

        Player p = e.getPlayer();

        if(isWorld)
        {
            Health.reset(p);
            Athlete.reset(p);
        }
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e)
    {
        boolean isWorld = WorldCheck.is(e.getPlayer().getWorld());

        MGPlayer mgp = MGApocalypse.getMGPlayer(e.getPlayer());
        Player p = e.getPlayer();

        if(mgp.getPerkLevel(Perk.ATHLETE) != 0)
        {
            if(isWorld) Athlete.setBoost(p);
            else Athlete.reset(p);
        }

        if(mgp.getPerkLevel(Perk.HEALTH) != 0)
        {
            if(isWorld) Health.setBoost(p);
            else Health.reset(p);
        }
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageByEntityEvent e)
    {
        if(e.isCancelled())return;

        //Axes and swords
        if(e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity)
        {
            Player damager = (Player) e.getDamager();
            if(ItemUtil.isSword(damager.getItemInHand().getType()))
            {
                double percentage = MGApocalypse.getMGPlayer(damager).getPerkLevel(Perk.SLAYER);
                if(percentage != 0)
                {
                    e.setDamage(addDamage(e.getDamage(), percentage));
                }
            }
            else if(ItemUtil.isAxe(damager.getItemInHand().getType()))
            {
                double percentage = MGApocalypse.getMGPlayer(damager).getPerkLevel(Perk.AXEMAN);
                if(percentage != 0)
                {
                    e.setDamage(addDamage(e.getDamage(), percentage));
                }
            }

        }
        //Bows
        else if(e.getDamager() instanceof Arrow && ((Arrow)e.getDamager()).getShooter() instanceof Player && e.getEntity() instanceof LivingEntity)
        {
            Player damager = (Player) ((Arrow) e.getDamager()).getShooter();
            if(ItemUtil.isBow(damager.getItemInHand().getType()))
            {
                double percentage = MGApocalypse.getMGPlayer(damager).getPerkLevel(Perk.BOWMAN);
                if(percentage != 0)
                {
                    e.setDamage(addDamage(e.getDamage(), percentage));
                }
            }

        }
    }

    private double addDamage(double start, double percentage)
    {
        return (1 + 0.01 * percentage) * start;
    }

}
