package com.minegusta.mgapocalypse.util;

import com.minegusta.mgapocalypse.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

public class RemoveEntity {
    private int seconds;
    private Entity e;

    public RemoveEntity(Entity e, int seconds) {
        this.e = e;
        this.seconds = seconds;
        start();
    }

    private void start() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.PLUGIN, new Runnable() {
            @Override
            public void run() {
                if (e != null) {
                    e.remove();
                }
            }
        }, 20 * seconds);
    }
}
