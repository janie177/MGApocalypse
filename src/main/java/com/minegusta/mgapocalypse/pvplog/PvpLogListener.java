package com.minegusta.mgapocalypse.pvplog;

import com.minegusta.mgapocalypse.config.LogoutManager;
import com.minegusta.mgapocalypse.util.WGManager;
import com.minegusta.mgapocalypse.util.WorldCheck;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class PvpLogListener implements Listener{

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEvent(PlayerQuitEvent e)
    {
        if(!WorldCheck.is(e.getPlayer().getWorld()))return;

        if(WGManager.canGetDamage(e.getPlayer()))
        {
            LogData.add(e.getPlayer(), new PvpBot(e.getPlayer()));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEvent(PlayerLoginEvent e)
    {
        if(!WorldCheck.is(e.getPlayer().getWorld()))return;
        if(LogData.contains(e.getPlayer()))
        {
            int remaining = LogData.remainingTime(e.getPlayer());
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "You combat logged! Please wait " + LogData.remainingTime(e.getPlayer()) + " seconds.");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEvent(PlayerJoinEvent e)
    {
        if(!WorldCheck.is(e.getPlayer().getWorld()))return;
        if(!LogData.contains(e.getPlayer()) && LogoutManager.getIfDead(e.getPlayer().getUniqueId()))
        {
            e.getPlayer().sendMessage(ChatColor.RED + "You died after you PVP logged.");

            int slot = 0;
            for(ItemStack i : e.getPlayer().getInventory())
            {
                slot++;
                e.getPlayer().getInventory().setItem(slot, new ItemStack(Material.AIR));
            }
            e.getPlayer().setHealth(0);
            LogoutManager.reset(e.getPlayer().getUniqueId());
        }
    }
}
