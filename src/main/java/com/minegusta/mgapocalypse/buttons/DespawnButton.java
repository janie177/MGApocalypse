package com.minegusta.mgapocalypse.buttons;

import com.minegusta.mgapocalypse.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public class DespawnButton {
    private Location l;
    private int TASK;

    public DespawnButton(Location l) {
        this.l = l;
    }

    public void start() {
        TASK = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.PLUGIN, new Runnable() {
            @Override
            public void run() {
                l.getWorld().getBlockAt(l).setType(Material.AIR);
                if (ButtonManager.buttonMap.containsKey(l)) ButtonManager.buttonMap.remove(l);
            }
        }, 20 * 40);
    }
}
