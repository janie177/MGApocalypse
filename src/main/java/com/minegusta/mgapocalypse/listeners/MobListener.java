package com.minegusta.mgapocalypse.listeners;

import com.google.common.collect.Lists;
import com.minegusta.mgapocalypse.items.LootItem;
import com.minegusta.mgapocalypse.util.RandomNumber;
import com.minegusta.mgapocalypse.util.WorldCheck;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class MobListener implements Listener
{
    final static int chance = 1; //Percentage to spawn a zombie group
    final static List<CreatureSpawnEvent.SpawnReason> allowedReasons = Lists.newArrayList(CreatureSpawnEvent.SpawnReason.SPAWNER, CreatureSpawnEvent.SpawnReason.CUSTOM);
    //Block spawning of nooby mobs
    @EventHandler(priority = EventPriority.LOWEST)
    public void onSpawn(CreatureSpawnEvent e)
    {
        if(!WorldCheck.is(e.getEntity().getWorld()))return;

        if(allowedReasons.contains(e.getSpawnReason()))
        {
            return;
        }

        if(e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL || e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.DEFAULT) {

            Location loc = e.getLocation();
            e.setCancelled(true);

            //Check if spawn

            if (RandomNumber.get(100) <= chance) {

                //Spawn a zombie
                Zombie zombie = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);

                for (int i = 0; i < RandomNumber.get(6); i++)
                {
                    Location random = zombie.getLocation().add(RandomNumber.get(1),0,0);
                    Zombie zombie2 = (Zombie) loc.getWorld().spawnEntity(random, EntityType.ZOMBIE);
                    if (RandomNumber.get(25) == 1) {
                        zombie2.setBaby(true);
                    }
                }
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
            if(p.isSneaking() && zombie.getLocation().distance(p.getLocation()) > 9)
            {
                e.setCancelled(true);
            }
            //Adults only
            else if(!((Zombie)e.getEntity()).isBaby())
            {
                zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 600, 1));
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
        e.getDrops().clear();
        if(RandomNumber.get(6) == 1)
        {
            e.getDrops().add(LootItem.ZOMBIEMEAT.build());
        }
    }
}
