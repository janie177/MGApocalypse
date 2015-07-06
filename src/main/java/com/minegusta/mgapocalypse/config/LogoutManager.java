package com.minegusta.mgapocalypse.config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.UUID;

public class LogoutManager {
    static File file;
    static FileConfiguration conf;

    /**
     * Create a file for log out and pvp.
     *
     * @param p The plugin.
     */
    public static void createFile(Plugin p) {
        try {

            file = new File(p.getDataFolder(), "pvplogs.yml");

            if (!file.exists()) {
                p.saveResource("pvplogs.yml", false);
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
    public static void save() {
        try {
            conf.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get if a player was killed
     *
     * @param p The UUID of the player to check for.
     * @return if the player is dead.
     */
    public static boolean getIfDead(UUID p) {
        return conf.isSet(p.toString()) && conf.getBoolean(p.toString());
    }

    /**
     * Set if a player was killed.
     *
     * @param p    The player in question.
     * @param dead if the player should be killed on log-in.
     */
    public static void set(UUID p, boolean dead) {
        conf.set(p.toString(), dead);
    }

    /**
     * Set a player to false so they wont be killed again.
     *
     * @param p The player's UUID to reset the location for.
     */
    public static void reset(UUID p) {
        conf.set(p.toString(), false);
    }
}
