package com.minegusta.mgapocalypse.util;

import org.bukkit.Location;
import org.bukkit.Material;

public class BlockUtil {

    public static void changeBlock(Location l, Material to, int data)
    {
        l.getWorld().getPlayers().stream().filter(p -> l.distance(p.getLocation()) < 45).forEach(p -> p.sendBlockChange(l, to, (byte) data));
    }
}
