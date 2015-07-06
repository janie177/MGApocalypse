package com.minegusta.mgapocalypse.Tasks;

import com.minegusta.mgapocalypse.MGApocalypse;
import com.minegusta.mgapocalypse.Main;
import org.bukkit.Bukkit;

public class PlayTimeTask
{
    private static int id = -1;

    public static void start() {
        id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.PLUGIN, () ->
        {
            MGApocalypse.getMGPlayers().stream().forEach(p ->
            {
                if(p.getIfPlaying())
                {
                    p.addPlayTime(1);
                    p.addTimeAlive(1);
                }
            });
        },20 * 60, 20 * 60);
    }

    public static void stop()
    {
        if(id != -1)
        {
            Bukkit.getScheduler().cancelTask(id);
        }
    }

}
