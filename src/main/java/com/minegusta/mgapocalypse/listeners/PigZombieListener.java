package com.minegusta.mgapocalypse.listeners;

import com.google.common.collect.Lists;
import com.minegusta.mgapocalypse.MGApocalypse;
import com.minegusta.mgapocalypse.files.MGPlayer;
import com.minegusta.mgapocalypse.util.RandomNumber;
import com.minegusta.mgapocalypse.util.WorldCheck;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
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
            e.setDroppedExp(0);
            e.getDrops().clear();
            Location l = e.getEntity().getLocation().add(0,1,0);

            List<Block> blocks = getBlocks(l);

            effect(l);

            e.getEntity().getWorld().getPlayers().stream().filter(p -> p.getLocation().distance(l) < 30).forEach(p ->
            {
                if(p.getLocation().distance(l) < 5)
                {
                    potion(p);
                }
                blood(blocks, p);
            });
        }
    }

    @EventHandler
    public void onPigZombieHit(EntityDamageByEntityEvent e)
    {
        if(!WorldCheck.is(e.getEntity().getWorld()))return;

        if(e.getEntity() instanceof PigZombie)
        {
            ((PigZombie) e.getEntity()).damage(100);
        }

        if(e.getDamager() instanceof Player)
        {
            MGPlayer mgp = MGApocalypse.getMGPlayer((Player) e.getDamager());
            mgp.addZombieKills(1);
        }
    }

    private List<Block> getBlocks(Location l)
    {
        List<Block> blocks = Lists.newArrayList();
        int radius = 4;

        for(int x = -radius; x < radius; x++)
        {
            for(int y = -1; y < 2; y++)
            {
                for(int z = -radius; z < radius; z++)
                {
                    Block b = l.getBlock().getRelative(x,y,z);

                    if(b.getType() != Material.AIR && b.getRelative(0,1,0).getType() == Material.AIR)
                    {
                        blocks.add(b);
                    }
                }
            }
        }

        return blocks;

    }

    private void blood(List<Block> blocks, Player p)
    {
        blocks.stream().forEach(b ->
        {
            int[] bloodType = getBloodMaterial();
            p.sendBlockChange(b.getLocation(), bloodType[0], (byte) bloodType[1]);
        });
    }

    private static final int[][] bloodTypes = new int[][]{{159,5}, {159,14}, {159,5}, {159,6}, {35,14}, {35,13}};

    private int[] getBloodMaterial()
    {
        int index = RandomNumber.get(bloodTypes.length) - 1;
        return bloodTypes[index];
    }

    private void potion(Player p)
    {
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 3, 0, false, false));
        p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 15, 0, false, false));
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 25, 0, false, false));
    }

    private void effect (Location l)
    {
        l.getWorld().playSound(l, Sound.SLIME_WALK2, 10, 1);
        l.getWorld().playSound(l, Sound.CHICKEN_EGG_POP, 10F, 0.1F);
        l.getWorld().spigot().playEffect(l, Effect.CRIT, 0, 0, 1, 1, 1, 1/10, 40, 35);
        l.getWorld().spigot().playEffect(l, Effect.SLIME, 0, 0, 1, 1, 1, 1/10, 40, 35);
        l.getWorld().spigot().playEffect(l, Effect.SNOWBALL_BREAK, 0, 0, 1, 1, 1, 1/10, 40, 35);
        l.getWorld().spigot().playEffect(l, Effect.ITEM_BREAK, 55, 0, 1, 1, 1, 1/10, 40, 35);
    }
}
