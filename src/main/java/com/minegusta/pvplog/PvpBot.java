package com.minegusta.pvplog;

import com.minegusta.mgapocalypse.Main;
import com.minegusta.mgapocalypse.config.LogoutManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class PvpBot
{
    private UUID uuid;
    private Location loc;
    private String name;
    private int TASK = -1;
    private Villager v;
    private ItemStack[] inv;
    private ItemStack[] armour;
    private int seconds = 0;

    public PvpBot(Player p)
    {
        this.uuid = p.getUniqueId();
        this .loc = p.getLocation();
        this.armour = p.getInventory().getArmorContents();
        this.name = p.getName();
        this.inv = p.getInventory().getContents();

        this.v = spawnBot();
        TASK = start();
    }


    private int start()
    {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.PLUGIN, new Runnable() {
            @Override
            public void run() {
                if (v == null || v.isDead())
                {
                    for (ItemStack i : inv) {
                        if(i != null && i.getType() != Material.AIR)loc.getWorld().dropItemNaturally(loc, i);
                    }
                    for (ItemStack i : armour) {
                        if(i != null && i.getType() != Material.AIR)loc.getWorld().dropItemNaturally(loc, i);
                    }
                    LogoutManager.set(uuid, true);
                    stop();
                    return;
                }
                v.teleport(loc);
                seconds++;

                if (seconds > 10) {
                    stop();
                }
            }
        }, 0, 20);
    }

    public void stop()
    {
        if(TASK != -1)
        {
            Bukkit.getScheduler().cancelTask(TASK);
        }
        if(!v.isDead())
        {
            v.setHealth(0);
            v.damage(100);
        }
        LogData.remove(uuid);
    }

    private Villager spawnBot()
    {
        Bukkit.broadcastMessage(ChatColor.DARK_RED + name +  ChatColor.RED +" just combat logged! Their NPC has been spawned.");
        Villager v = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
        v.setAdult();
        v.setCustomNameVisible(true);
        v.setCustomName(name);
        v.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 15, 10));

        for(Entity ent : v.getNearbyEntities(30,30,30))
        {
            if(ent instanceof Zombie)
            {
                ((Creature)ent).setTarget(v);
            }
        }

        return v;
    }

    public int getRemainingTime()
    {
        return 10 - seconds;
    }
}
