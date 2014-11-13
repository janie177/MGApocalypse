package com.minegusta.mgapocalypse.config;

import com.minegusta.mgapocalypse.util.StringLocConverter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.UUID;

public class SavedLocationsManager {

    static File file;
    static FileConfiguration conf;

    /**
     * Create a file for saved locations.
     * @param p The plugin.
     */
    public static void createOrLoadLocationsFile(Plugin p){
        try {

            file = new File(p.getDataFolder(), "playerlocations.yml");

            if (!file.exists()) {
                p.saveResource("playerlocations.yml", false);
                Bukkit.getLogger().info("Successfully created " + file.getName() + ".");
            }
            conf = YamlConfiguration.loadConfiguration(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Save the file to disk. Do this in a repeating task.
     */
    public static void save(){
        try{
            conf.save(file);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Get a players saved location.
     * @param p The UUID of the player to check for.
     * @return The location the player logged out from or null.
     */
    public static Location getLocation(UUID p){
        if(!conf.isSet(p.toString())) return null;
        else
        {
            return StringLocConverter.stringToLocation(conf.getString(p.toString()));
        }
    }

    /**
     * Set the location when a player logs out.
     * @param p The player in question.
     * @param l The location to save.
     */
    public static void setLocation(UUID p, Location l){
        conf.set(p.toString(), StringLocConverter.locationToString(l));
    }

    /**
     * Reset a players location after the respawned.
     * @param p The player's UUID to reset the location for.
     */
    public static void resetLocation(UUID p)
    {
        if(conf.isSet(p.toString())){
            conf.set(p.toString(), null);
        }
    }
}
