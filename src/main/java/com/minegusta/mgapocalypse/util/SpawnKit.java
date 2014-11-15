package com.minegusta.mgapocalypse.util;

import com.minegusta.mgapocalypse.Main;
import com.minegusta.mgapocalypse.items.LootItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SpawnKit
{
    private Player p;

    public SpawnKit(Player p)
    {
        this.p = p;
        start();
    }

    private void start()
    {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.PLUGIN, new Runnable() {
            @Override
            public void run() {
                p.getInventory().addItem(LootItem.WATERBOTTLE.build(), LootItem.WOODSWORD.build(), LootItem.BANDAGE.build(), LootItem.LEATHERCHEST.build());
                p.updateInventory();
            }
        }, 35);
    }
}
