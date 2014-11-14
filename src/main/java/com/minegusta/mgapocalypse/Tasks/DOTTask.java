package com.minegusta.mgapocalypse.Tasks;

import com.minegusta.mgapocalypse.Main;
import com.minegusta.mgapocalypse.util.TempData;
import com.minegusta.mgapocalypse.util.WorldCheck;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class DOTTask
{
    public static int diseaseTask()
    {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.PLUGIN, new Runnable() {
            @Override
            public void run()
            {
                for(String uuid : TempData.diseaseMap.keySet())
                {
                    Player p = Bukkit.getPlayer(UUID.fromString(uuid));
                    if(p.isOnline() && WorldCheck.is(p.getWorld()))
                    {
                        p.getWorld().spigot().playEffect(p.getLocation(), Effect.PARTICLE_SMOKE);
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 0));
                        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 2, 0));
                        p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 6, 0));
                        p.sendMessage(ChatColor.RED + "You feel sick. Some milk would help.");
                    }
                }
            }
        }, 20 * 10, 20 * 20);
    }

    public static int bleedingTask()
    {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.PLUGIN, new Runnable() {
            @Override
            public void run()
            {
                for(String uuid : TempData.bleedingMap.keySet())
                {
                    Player p = Bukkit.getPlayer(UUID.fromString(uuid));
                    if(p.isOnline() && WorldCheck.is(p.getWorld()))
                    {
                        p.damage(1.0);
                        p.getWorld().spigot().playEffect(p.getLocation(), Effect.CRIT);
                        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 2, 2));
                        p.sendMessage(ChatColor.RED + "You should find some bandages. You are bleeding.");
                    }
                }
            }
        }, 20 * 10, 20 * 14);
    }
}
