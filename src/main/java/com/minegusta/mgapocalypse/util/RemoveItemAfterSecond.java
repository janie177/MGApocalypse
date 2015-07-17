package com.minegusta.mgapocalypse.util;

import com.minegusta.mgapocalypse.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RemoveItemAfterSecond {
    public RemoveItemAfterSecond(Player p, Material m) {
        start(p, m);
    }

    public RemoveItemAfterSecond(Player p, int slot) {
        start(p, slot);
    }


    private void start(final Player p, final Material m) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.PLUGIN, new Runnable() {
            @Override
            public void run() {
                ItemUtil.removeOne(p, m);
                p.updateInventory();
            }
        }, 2);
    }

    private void start(final Player p, final int slot) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.PLUGIN, new Runnable() {
            @Override
            public void run() {
                p.getInventory().setItem(slot, new ItemStack(Material.AIR));
                p.updateInventory();
            }
        }, 2);
    }
}
