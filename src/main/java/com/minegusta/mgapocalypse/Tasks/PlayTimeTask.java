package com.minegusta.mgapocalypse.Tasks;

import com.minegusta.mgapocalypse.MGApocalypse;
import com.minegusta.mgapocalypse.Main;
import com.minegusta.mgapocalypse.files.MGPlayer;
import org.bukkit.Bukkit;

public class PlayTimeTask
{
    private static int id = -1;

    public static void start() {
        id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.PLUGIN, () -> MGApocalypse.getMGPlayers().stream().filter(MGPlayer::getIfPlaying).forEach(PlayTimeTask::updateTime),20 * 60, 20 * 60);
    }

    private static void updateTime(MGPlayer p)
    {
        p.addPlayTime(1);
        p.addTimeAlive(1);
    }

    public static void stop()
    {
        if(id != -1)
        {
            Bukkit.getScheduler().cancelTask(id);
        }
    }

}
