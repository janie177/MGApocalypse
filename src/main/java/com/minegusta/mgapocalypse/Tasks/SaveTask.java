package com.minegusta.mgapocalypse.Tasks;

import com.minegusta.mgapocalypse.MGApocalypse;
import com.minegusta.mgapocalypse.Main;
import com.minegusta.mgapocalypse.config.DefaultConfig;
import com.minegusta.mgapocalypse.config.LogoutManager;
import com.minegusta.mgapocalypse.config.SavedLocationsManager;
import com.minegusta.mgapocalypse.files.MGPlayer;
import org.bukkit.Bukkit;

public class SaveTask {
    public static int start() {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.PLUGIN, () ->
        {
            SavedLocationsManager.save();
            DefaultConfig.saveConfig();
            LogoutManager.save();

            for (MGPlayer mgp : MGApocalypse.getMGPlayers()) {
                mgp.saveFile();
            }

        }, 20 * 60, 20 * 60);
    }
}
