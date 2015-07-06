package com.minegusta.mgapocalypse.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class StringLocConverter {

    public static String locationToString(Location l) {
        return Double.toString(l.getX()) + "," + Double.toString(l.getY()) + "," + Double.toString(l.getZ()) + "," + l.getWorld().getName();
    }

    public static Location stringToLocation(String s) {
        String[] split = s.split(",");
        double x = Double.parseDouble(split[0]);
        double y = Double.parseDouble(split[1]);
        double z = Double.parseDouble(split[2]);
        World w = null;

        try {
            w = Bukkit.getWorld(split[3]);
        } catch (Exception ignored) {
        }

        return new Location(w, x, y, z);
    }

}
