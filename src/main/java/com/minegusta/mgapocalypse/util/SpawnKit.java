package com.minegusta.mgapocalypse.util;

import com.minegusta.mgapocalypse.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SpawnKit {
    private Player p;

    public SpawnKit(Player p) {
        this.p = p;
        start();
    }

    private void start() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.PLUGIN, () ->
        {
            for (RespawnKit kit : RespawnKit.values()) {
                if (p.hasPermission(kit.getPermissionsNode())) {
                    addKit(kit);
                }
            }

        }, 35);
    }

    private void addKit(RespawnKit kit) {
        p.getInventory().addItem(kit.getItems());
        p.updateInventory();
    }
}
