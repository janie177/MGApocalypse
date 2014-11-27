package com.minegusta.mgapocalypse.pvplog;

import com.minegusta.mgapocalypse.Main;
import com.minegusta.mgapocalypse.config.LogoutManager;
import com.minegusta.mgapocalypse.util.StringLocConverter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class PvpBot
{
    private UUID uuid;
    private Location loc;
    private String name;
    private int TASK = -1;
    private Zombie v;
    private Inventory inv;
    private int seconds = 0;

    public PvpBot(Player p)
    {
        this.uuid = p.getUniqueId();
        this .loc = p.getLocation();
        this.name = p.getName();
        this.inv = p.getInventory();

        this.v = spawnBot();
        TASK = start();
    }


    private int start()
    {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.PLUGIN, new Runnable() {
            @Override
            public void run()
            {
                for(Entity ent : v.getNearbyEntities(20,10,20))
                {
                    if(v.isDead())break;
                    if(ent instanceof Zombie)
                    {
                        ((Creature)ent).setTarget(v);
                    }
                    if(v.getLocation().distance(ent.getLocation()) < 3)v.damage(2);
                }
                if (v == null || v.isDead())
                {
                    for (ItemStack i : inv)
                    {
                        if(i!=null && i.getType() != Material.AIR) loc.getWorld().dropItemNaturally(loc, i);
                    }
                    LogoutManager.set(uuid, true);
                    stop();
                    return;
                }
                v.teleport(loc);
                seconds++;

                if (seconds > 10) {
                    v.setHealth(0);
                    v.damage(100);
                    v.remove();
                    stop();
                }
            }
        }, 0, 20);
    }

    public void stop()
    {
        Bukkit.broadcastMessage("DEBUG: Stop called!");
        if(TASK != -1)
        {
            Bukkit.getScheduler().cancelTask(TASK);
        }
        v = null;
        LogData.remove(uuid);
    }

    private Zombie spawnBot()
    {
        Bukkit.broadcastMessage(ChatColor.DARK_RED + name +  ChatColor.RED +" just combat logged! Their NPC has been spawned.");
        Zombie v = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
        v.setBaby(false);
        v.setVillager(false);
        v.setCustomNameVisible(true);
        v.setCustomName(name);
        v.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 100, 10));
        v.setCanPickupItems(false);

        v.getEquipment().setHelmet(new ItemStack(Material.SKULL_ITEM, 1, (short) 3) {
            {
                SkullMeta meta = (SkullMeta) getItemMeta();
                meta.setOwner(name);
                setItemMeta(meta);
            }
        });

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
