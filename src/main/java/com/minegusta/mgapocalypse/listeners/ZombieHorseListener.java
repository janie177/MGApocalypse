package com.minegusta.mgapocalypse.listeners;

import com.google.common.collect.Lists;
import com.minegusta.mgapocalypse.MGApocalypse;
import com.minegusta.mgapocalypse.files.MGPlayer;
import com.minegusta.mgapocalypse.util.BloodBlockUtil;
import com.minegusta.mgapocalypse.util.WorldCheck;
import com.minegusta.mgapocalypse.util.ZombieUtil;
import com.minegusta.mgloot.loottables.Loot;
import com.minegusta.mgloot.managers.LootManager;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class ZombieHorseListener implements Listener {
    @EventHandler
    public void onHorseHit(EntityDamageByEntityEvent e) {
        if (!WorldCheck.is(e.getEntity().getWorld())) return;

        if (e.getEntity() instanceof Horse) {
            Horse h = (Horse) e.getEntity();
            if (h.getVariant() == Horse.Variant.UNDEAD_HORSE) {
                e.setDamage(1000);
                if (e.getDamager() instanceof Player) {
                    MGPlayer mgp = MGApocalypse.getMGPlayer((Player) e.getDamager());
                    mgp.addZombieKills(1);
                } else if (e.getDamager() instanceof Arrow && ((Arrow) e.getDamager()).getShooter() instanceof Player) {
                    MGPlayer mgp = MGApocalypse.getMGPlayer((Player) ((Arrow) e.getDamager()).getShooter());
                    mgp.addZombieKills(1);
                }
            }
        }
    }

    @EventHandler
    public void onHorseDeath(EntityDeathEvent e) {
        if (!WorldCheck.is(e.getEntity().getWorld())) return;

        if (e.getEntity() instanceof Horse && ((Horse) e.getEntity()).getVariant() == Horse.Variant.UNDEAD_HORSE) {
            Horse h = (Horse) e.getEntity();
            Location l = h.getLocation();

            List<Player> players = Lists.newArrayList();
            Bukkit.getOnlinePlayers().stream().filter(p -> p.getLocation().distance(l) < 30).forEach(players::add);

            effect(l);

            BloodBlockUtil.applyBlood(l, players);
            reward(l, players);
        }
    }

    private void reward(Location l, List<Player> players)
    {
        Random rand = new Random();
        Boolean reward = rand.nextBoolean();

        if (reward) {
            players.stream().forEach(p -> p.sendMessage(ChatColor.GREEN + "The zombie horse recently ate some fresh food!"));
            LootManager m = new LootManager(Loot.foodLoot, 6);
            for (ItemStack i : m.getLoot()) {
                l.getWorld().dropItemNaturally(l, i);
            }
        } else {
            players.stream().forEach(p -> p.sendMessage(ChatColor.RED + "The zombie horse was filled with baby zombies!"));
            int amount = rand.nextInt(5) + 2;

            for (int x = 0; x < amount; x++) {
                Zombie z = (Zombie) l.getWorld().spawnEntity(l, EntityType.ZOMBIE);
                ZombieUtil.setZombieToVanilla(z);
                z.setBaby(true);
            }
        }
    }

    private void effect(Location l) {
        l.getWorld().playSound(l, Sound.ENTITY_SLIME_JUMP, 10, 1);
        l.getWorld().playSound(l, Sound.ENTITY_CHICKEN_EGG, 10F, 0.1F);
        l.getWorld().spigot().playEffect(l, Effect.CRIT, 0, 0, 2, 2, 2, 1 / 10, 40, 35);
        l.getWorld().spigot().playEffect(l, Effect.SLIME, 0, 0, 2, 2, 2, 1 / 10, 40, 35);
        l.getWorld().spigot().playEffect(l, Effect.SNOWBALL_BREAK, 0, 0, 2, 2, 2, 1 / 10, 40, 35);
    }
}

