package com.minegusta.mgapocalypse.listeners;


import com.minegusta.mgapocalypse.util.RemoveEntity;
import com.minegusta.mgapocalypse.util.WorldCheck;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ProjectileListener implements Listener {
    @EventHandler
    public void onThrow(ProjectileHitEvent e) {
        if (!WorldCheck.is(e.getEntity().getWorld())) return;

        //Enderpearl grenades.
        if (e.getEntity() instanceof EnderPearl) {
            Location loc = e.getEntity().getLocation();
            loc.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 5, false, false);
        }

        //Snowballs
        if (e.getEntity() instanceof Snowball) {

            Location l = e.getEntity().getLocation();

            LivingEntity temp = (LivingEntity) l.getWorld().spawnEntity(l, EntityType.SQUID);

            temp.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 300, 0));

            temp.getWorld().getLivingEntities().stream().filter(ent -> ent instanceof Zombie && ent.getLocation().distance(temp.getLocation()) < 30).forEach(ent ->
            {
                ((Creature) ent).setTarget(temp);
                EntityTargetEvent event = new EntityTargetLivingEntityEvent(ent, temp, EntityTargetEvent.TargetReason.RANDOM_TARGET);
                Bukkit.getPluginManager().callEvent(event);
            });

            new RemoveEntity(temp, 5);
        }
    }

    //Block enderpearl teleportation.
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockTeleport(PlayerTeleportEvent e) {
        if (!WorldCheck.is(e.getPlayer().getWorld())) return;

        if (e.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            e.setCancelled(true);
        }
    }
}
