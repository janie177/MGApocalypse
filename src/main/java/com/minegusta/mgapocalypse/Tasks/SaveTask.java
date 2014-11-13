package com.minegusta.mgapocalypse.Tasks;

import com.minegusta.mgapocalypse.Main;
import com.minegusta.mgapocalypse.config.DefaultConfig;
import com.minegusta.mgapocalypse.config.SavedLocationsManager;
import org.bukkit.Bukkit;

public class SaveTask
{
    public static int start()
    {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.PLUGIN, new Runnable()
        {
            @Override
            public void run()
            {
                SavedLocationsManager.save();
                DefaultConfig.saveConfig();
            }
        }, 20 * 60, 20 * 300);
    }
}
