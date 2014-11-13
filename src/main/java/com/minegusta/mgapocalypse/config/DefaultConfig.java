package com.minegusta.mgapocalypse.config;

import com.minegusta.mgapocalypse.Main;
import com.minegusta.mgapocalypse.util.RandomNumber;
import com.minegusta.mgapocalypse.util.StringLocConverter;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class DefaultConfig
{
    /**
     * Load the default config.
     */
    public static void loadConfig()
    {
        Main.PLUGIN.saveDefaultConfig();
    }

    public static FileConfiguration getConfig()
    {
        return Main.PLUGIN.getConfig();
    }

    public static void saveConfig()
    {
        Main.PLUGIN.saveConfig();
    }


    /**
     * Get all the spawn locations as a list of strings.
     * @return A list of spawn locations (strings).
     */
    public static List<String> getSpawns()
    {
        return getConfig().getStringList("spawns");
    }

    /**
     * See which worlds are enabled.
     * @return A list of world names that are enabled.
     */
    public static List<String> getWorlds()
    {
        return getConfig().getStringList("enabled_worlds");
    }

    /**
     * Add a spawn to the file.
     * @param l The location to add.
     */
    public static void addSpawn(Location l)
    {
        getConfig().set("spawns", getSpawns().add(StringLocConverter.locationToString(l)));
    }

    /**
     * Remove a spawn from the file.
     * @param index The # to remove.
     */
    public static void removeSpawn(int index)
    {
        getConfig().set("spawns", getSpawns().remove(index));
    }

    /**
     * Get a random spawnpoint from the config.
     * @return A random spawn location.
     */
    public static Location getRandomSpawn()
    {
        int length = getSpawns().size();
        return StringLocConverter.stringToLocation(getSpawns().get(RandomNumber.get(length) - 1));
    }

    public static Location getMainSpawn()
    {
        return StringLocConverter.stringToLocation(getConfig().getString("main_spawn"));
    }

    public static void setMainSpawn(Location l)
    {
        getConfig().set("main_spawn", StringLocConverter.locationToString(l));
    }
}
