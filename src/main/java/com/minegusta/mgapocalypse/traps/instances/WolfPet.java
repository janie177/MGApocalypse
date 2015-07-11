package com.minegusta.mgapocalypse.traps.instances;

import com.google.common.collect.Maps;
import com.minegusta.mgapocalypse.traps.ITrap;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class WolfPet implements ITrap {

    private static ConcurrentMap<String, Long> claimed = Maps.newConcurrentMap();

    @Override
    public void apply(Player p, Sign s) {

        String uuid = p.getUniqueId().toString();

        if(claimed.containsKey(uuid) && (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) - TimeUnit.MILLISECONDS.toSeconds(claimed.get(uuid)) < 21600)) return;


        Wolf w = (Wolf) p.getWorld().spawnEntity(p.getLocation(), EntityType.WOLF);
        w.setAdult();
        w.setTamed(true);
        w.setOwner(p);
        w.setCollarColor(DyeColor.CYAN);
        w.setBreed(false);

        claimed.put(uuid, System.currentTimeMillis());
    }

    @Override
    public String getMessage() {
        return ChatColor.GREEN + "An abandoned dog begins to follow you...";
    }

    @Override
    public int getCooldownTime() {
        return 0;
    }
}
