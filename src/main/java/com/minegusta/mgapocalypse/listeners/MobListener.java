package com.minegusta.mgapocalypse.listeners;

import com.google.common.collect.Lists;
import com.minegusta.mgapocalypse.MGApocalypse;
import com.minegusta.mgapocalypse.files.MGPlayer;
import com.minegusta.mgapocalypse.perks.Perk;
import com.minegusta.mgapocalypse.util.RandomNumber;
import com.minegusta.mgapocalypse.util.WorldCheck;
import com.minegusta.mgapocalypse.util.ZombieUtil;
import com.minegusta.mgloot.loottables.LootItem;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class MobListener implements Listener {
    private final static int nightChance = 2; //Promillage to spawn a zombie group
    private final static int dayChance = 100; //promillage to spawn a zombie group

    private final List<EntityType> blockedDrops = Lists.newArrayList(EntityType.ZOMBIE, EntityType.SHEEP, EntityType.SQUID, EntityType.HORSE, EntityType.PIG_ZOMBIE, EntityType.SKELETON);

    private final static List<Material> highspawnchance = Lists.newArrayList(Material.SMOOTH_BRICK, Material.STONE, Material.WOOD, Material.DOUBLE_STEP);

    private final static List<CreatureSpawnEvent.SpawnReason> allowedReasons = Lists.newArrayList(CreatureSpawnEvent.SpawnReason.SPAWNER, CreatureSpawnEvent.SpawnReason.CUSTOM);

    private static long coolDown = System.currentTimeMillis();

    //Block spawning of nooby mobs
    @EventHandler(priority = EventPriority.LOWEST)
    public void onSpawn(CreatureSpawnEvent e) {
        if (!WorldCheck.is(e.getEntity().getWorld())) return;

        if (allowedReasons.contains(e.getSpawnReason()))
        {
            if(e.getEntity() instanceof Zombie)
            {
                Zombie z = (Zombie) e.getEntity();
                ZombieUtil.setZombieToVanilla(z);
            }
            else if(e.getEntity() instanceof PigZombie)
            {
                PigZombie p = (PigZombie) e.getEntity();
                p.getEquipment().setItemInMainHand(new ItemStack(Material.AIR));
                p.getEquipment().setItemInOffHand(new ItemStack(Material.AIR));
            }
            return;
        }
        e.setCancelled(true);

        //Leave this out, custom task for now.
        /**
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

         **/
    }

    private static int SNEAKDISTANCE = 15;
    private static int WALKDDISTANCE = 28;
    private static int RUNDISTANCE = 56;

    //Make mobs faster and stronger on target, also block target from far on crouching players.
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDeath(EntityTargetLivingEntityEvent e) {
        if (!WorldCheck.is(e.getEntity().getWorld())) return;

        if (e.getEntity().getType() == EntityType.ZOMBIE && e.getTarget() instanceof Player)
        {
            Player p = (Player) e.getTarget();
            MGPlayer mgp = MGApocalypse.getMGPlayer(p);
            double reduction = (100 - (2 * mgp.getPerkLevel(Perk.STEALTH))) * 0.01 ;

            Zombie zombie = (Zombie) e.getEntity();


            if(p.isSprinting() && zombie.getLocation().distance(p.getLocation()) > RUNDISTANCE * reduction)
            {
                e.setCancelled(true);
            }
            else if (zombie.getLocation().distance(p.getLocation()) > WALKDDISTANCE * reduction && !p.isSprinting())
            {
                e.setCancelled(true);

            } else if (p.isSneaking() && zombie.getLocation().distance(p.getLocation()) > SNEAKDISTANCE * reduction) {
                e.setCancelled(true);
            }


            //Setting the zombie speed
            else if (!zombie.isBaby()) {
                zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 600, 1, false, false));
            } else {
                zombie.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 180, 0, false, false));
            }
        }
    }

    //Stop mobs from burning
    @EventHandler(priority = EventPriority.LOWEST)
    public void onZombieBurn(EntityCombustEvent e) {
        if (!WorldCheck.is(e.getEntity().getWorld())) return;

        if (e.getEntityType() == EntityType.ZOMBIE) {
            e.setCancelled(true);
        }
    }


    //Remove drops
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDeath(EntityDeathEvent e) {
        if (!WorldCheck.is(e.getEntity().getWorld())) return;

        e.setDroppedExp(0);

        if (e.getEntityType() == EntityType.ZOMBIE) {

            if (RandomNumber.get(6) == 1) e.getDrops().add(LootItem.ZOMBIEMEAT.build());
            if (e.getEntity().getLastDamageCause() != null) {
                EntityDamageEvent cause = e.getEntity().getLastDamageCause();
                if (cause.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
                    if (((EntityDamageByEntityEvent) cause).getDamager() instanceof Player) {
                        MGApocalypse.getMGPlayer((Player) ((EntityDamageByEntityEvent) cause).getDamager()).addZombieKills(1);
                    }
                }
                if (cause.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)) {
                    if (((EntityDamageByEntityEvent) cause).getDamager() instanceof Arrow) {
                        Arrow arrow = (Arrow) ((EntityDamageByEntityEvent) cause).getDamager();
                        if (arrow.getShooter() != null && arrow.getShooter() instanceof Player) {
                            MGApocalypse.getMGPlayer((Player) arrow.getShooter()).addZombieKills(1);
                        }
                    }
                }
            }
        }

        //Block drops
        if (blockedDrops.contains(e.getEntityType())) {
            e.getDrops().clear();
        }
    }
}
