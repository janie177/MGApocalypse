package com.minegusta.mgapocalypse.Tasks;

import com.minegusta.mgapocalypse.Main;
import com.minegusta.mgapocalypse.util.WorldCheck;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class DrinkTask
{
    public static int start()
    {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.PLUGIN, new Runnable() {
            @Override
            public void run()
            {
                for(Player p : Bukkit.getOnlinePlayers())
                {
                    if(WorldCheck.is(p.getWorld()))
                    {
                        if(p.getLevel() == 0)
                        {
                            p.damage(1.0);
                            p.sendMessage(ChatColor.RED + "You are thirsty!");
                        }
                        else
                        {
                            p.setLevel(p.getLevel() - 1);
                            switch (p.getLevel())
                            {
                                case 10:
                                {
                                    p.sendMessage(ChatColor.RED + "You are beginning to feel thirsty.");
                                }
                                break;
                                case 5:
                                {
                                    p.sendMessage(ChatColor.RED + "Your own pee is starting to look tempting..");
                                }
                                break;
                                case 1:
                                {
                                    p.sendMessage(ChatColor.RED + "You need water soon!");
                                }
                                break;
                                case 0:
                                {
                                    p.sendMessage(ChatColor.RED + "You are dehydrated.");
                                }
                                break;
                            }
                        }
                    }
                }

            }
        }, 20 * 10, 20 * 26);
    }
}
