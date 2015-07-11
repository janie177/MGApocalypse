package com.minegusta.mgapocalypse.util;

import com.minegusta.mgapocalypse.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public class BlockUtil {

    public static void changeBlock(Location l, Material to, int data)
    {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), ()-> l.getWorld().getPlayers().stream().filter(p -> l.distance(p.getLocation()) < 45).forEach(p -> p.sendBlockChange(l, to, (byte) data)), 2);

    }
}
