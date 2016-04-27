package com.minegusta.mgapocalypse.listeners;

import com.google.common.collect.Lists;
import com.minegusta.mgapocalypse.MGApocalypse;
import com.minegusta.mgapocalypse.files.MGPlayer;
import com.minegusta.mgapocalypse.util.BloodBlockUtil;
import com.minegusta.mgapocalypse.util.WorldCheck;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class PigZombieListener implements Listener{

    @EventHandler
    public void onPigZombieDeath(EntityDeathEvent e)
    {
        if(!WorldCheck.is(e.getEntity().getWorld()))return;

        if(e.getEntity() instanceof PigZombie)
        {
            Location l = e.getEntity().getLocation().add(0,1,0);
            effect(l);

            List<Player> players = Lists.newArrayList();

            e.getEntity().getWorld().getPlayers().stream().filter(p -> p.getLocation().distance(l) < 30).forEach(p ->
            {
                if(p.getLocation().distance(l) < 5)
                {
                    potion(p);
                }
                players.add(p);
            });

            BloodBlockUtil.applyBlood(l.clone().add(0,-1,0), players);
        }
    }

    @EventHandler
    public void onPigZombieHit(EntityDamageByEntityEvent e)
    {
        if(!WorldCheck.is(e.getEntity().getWorld()))return;

        if(e.getEntity() instanceof PigZombie)
        {
            e.setDamage(1000);
            if(e.getDamager() instanceof Player)
            {
                MGPlayer mgp = MGApocalypse.getMGPlayer((Player) e.getDamager());
                mgp.addZombieKills(1);
            }
            else if(e.getDamager() instanceof Arrow && ((Arrow) e.getDamager()).getShooter() instanceof Player)
            {
                MGPlayer mgp = MGApocalypse.getMGPlayer((Player) ((Arrow) e.getDamager()).getShooter());
                mgp.addZombieKills(1);
            }
        }
    }

    private void potion(Player p)
    {
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 3, 0, false, false));
        p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 15, 0, false, false));
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 15, 0, false, false));
    }

    private void effect (Location l)
    {
        l.getWorld().playSound(l, Sound.ENTITY_SLIME_JUMP, 10, 1);
        l.getWorld().playSound(l, Sound.ENTITY_CHICKEN_EGG, 10F, 0.1F);
        l.getWorld().spigot().playEffect(l, Effect.CRIT, 0, 0, 2, 2, 2, 1/10, 40, 35);
        l.getWorld().spigot().playEffect(l, Effect.SLIME, 0, 0, 2, 2, 2, 1/10, 40, 35);
        l.getWorld().spigot().playEffect(l, Effect.POTION_BREAK, 16389, 1, 1, 1, 1, 1, 1, 20);
        l.getWorld().spigot().playEffect(l, Effect.SNOWBALL_BREAK, 0, 0, 2, 2, 2, 1/10, 40, 35);
    }
}
