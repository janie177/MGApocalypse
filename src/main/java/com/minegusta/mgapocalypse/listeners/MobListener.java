package com.minegusta.mgapocalypse.listeners;

import com.google.common.collect.Lists;
import com.minegusta.mgapocalypse.items.LootItem;
import com.minegusta.mgapocalypse.kills.ZombieKills;
import com.minegusta.mgapocalypse.util.RandomNumber;
import com.minegusta.mgapocalypse.util.TempData;
import com.minegusta.mgapocalypse.util.WorldCheck;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MobListener implements Listener
{
    private final static int nightChance = 2; //Promillage to spawn a zombie group
    private final static int dayChance = 100; //promillage to spawn a zombie group

    private final List<EntityType> blockedDrops = Lists.newArrayList(EntityType.ZOMBIE, EntityType.SHEEP, EntityType.SQUID, EntityType.HORSE, EntityType.PIG_ZOMBIE);

    private final static List<Material> highspawnchance = Lists.newArrayList(Material.SMOOTH_BRICK,Material.STONE,Material.WOOD, Material.DOUBLE_STEP);

    private final static List<CreatureSpawnEvent.SpawnReason> allowedReasons = Lists.newArrayList(CreatureSpawnEvent.SpawnReason.SPAWNER, CreatureSpawnEvent.SpawnReason.CUSTOM);

    private static long coolDown = System.currentTimeMillis();

    //Block spawning of nooby mobs
    @EventHandler(priority = EventPriority.LOWEST)
    public void onSpawn(CreatureSpawnEvent e)
    {
        if(!WorldCheck.is(e.getEntity().getWorld()))return;

        if(allowedReasons.contains(e.getSpawnReason()))
        {
            return;
        }

        Location loc = e.getLocation();
        e.setCancelled(true);

        int chance = 0;

        //Check time settings
        long time = loc.getWorld().getTime();

        boolean day = false;
        if (time < 12300 || time > 23850) chance = dayChance;
        else chance = nightChance;

        if(highspawnchance.contains(loc.getBlock().getRelative(BlockFace.DOWN).getType()))
        {
            chance = chance * 2;
        }

        int amount = RandomNumber.get(1000);

        //Return if chance is false
        if (!(amount <= chance)) return;

        if(!(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - coolDown) > 8))return;

        coolDown = System.currentTimeMillis();

        //Spawn a zombie
        Zombie zombie = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
        zombie.setCanPickupItems(false);

        for (int i = 0; i < RandomNumber.get(6); i++)
        {
            Location random = zombie.getLocation().add(RandomNumber.getDouble(10) - 5,0,RandomNumber.getDouble(10) - 5);
            Zombie zombie2 = (Zombie) loc.getWorld().spawnEntity(random, EntityType.ZOMBIE);
            zombie2.setCanPickupItems(false);
            if (RandomNumber.get(25) == 1) {
                zombie2.setBaby(true);
            }
        }

    }



    //Make mobs faster and stronger on target, also block target from far on crouching players.
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDeath(EntityTargetLivingEntityEvent e)
    {
        if(!WorldCheck.is(e.getEntity().getWorld()))return;

        if(e.getEntity().getType() == EntityType.ZOMBIE && e.getTarget() instanceof Player)
        {
            //Sneaking hides you from zombies.
            Player p = (Player) e.getTarget();
            Zombie zombie = (Zombie) e.getEntity();
            if(zombie.getLocation().distance(p.getLocation()) > 21 && !p.isSprinting())
            {
                e.setCancelled(true);
            }
            else if(p.isSneaking() && zombie.getLocation().distance(p.getLocation()) > 9)
            {
                e.setCancelled(true);
            }
            //Adults only
            else if(!zombie.isBaby())
            {
                zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 180, 1));
            }
            else
            {
                zombie.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 40, 0));
            }
        }
    }

    //Stop mobs from burning
    @EventHandler(priority = EventPriority.LOWEST)
    public void onZombieBurn(EntityCombustEvent e)
    {
        if(!WorldCheck.is(e.getEntity().getWorld()))return;

        if(e.getEntityType() == EntityType.ZOMBIE)
        {
            e.setCancelled(true);
        }
    }


    //Remove drops
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDeath(EntityDeathEvent e)
    {
        if(!WorldCheck.is(e.getEntity().getWorld()))return;

        e.setDroppedExp(0);

        if(e.getEntityType() == EntityType.ZOMBIE) {

            if (RandomNumber.get(6) == 1) e.getDrops().add(LootItem.ZOMBIEMEAT.build());
            if (e.getEntity().getLastDamageCause() != null) {
                EntityDamageEvent cause = e.getEntity().getLastDamageCause();
                if(cause.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK))
                {
                    if(((EntityDamageByEntityEvent)cause).getDamager() instanceof Player) ZombieKills.add((Player)((EntityDamageByEntityEvent)cause).getDamager());
                }
                if(cause.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE))
                {
                    if(((EntityDamageByEntityEvent)cause).getDamager() instanceof Arrow)
                    {
                        Arrow arrow = (Arrow) ((EntityDamageByEntityEvent)cause).getDamager();
                        if(arrow.getShooter() != null && arrow.getShooter() instanceof Player)
                        {
                            ZombieKills.add((Player) arrow.getShooter());
                        }
                    }
                }
            }
        }

        //Block drops
        if(blockedDrops.contains(e.getEntityType()))
        {
            e.getDrops().clear();
        }
    }
}
