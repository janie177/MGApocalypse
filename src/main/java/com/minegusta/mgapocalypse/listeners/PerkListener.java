package com.minegusta.mgapocalypse.listeners;

import com.minegusta.mgapocalypse.perks.abilities.AirDrop;
import com.minegusta.mgapocalypse.util.WorldCheck;
import com.minegusta.mgloot.loottables.LootItem;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

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

}
