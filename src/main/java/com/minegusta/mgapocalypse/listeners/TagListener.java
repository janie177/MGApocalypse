package com.minegusta.mgapocalypse.listeners;

import com.minegusta.mgapocalypse.util.TempData;
import com.minegusta.mgapocalypse.util.WorldCheck;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.tag.AsyncPlayerReceiveNameTagEvent;

public class TagListener implements Listener{
    @EventHandler
    public void onTag(AsyncPlayerReceiveNameTagEvent e)
    {
        if (!WorldCheck.is(e.getPlayer().getWorld())) return;
        Player p = e.getNamedPlayer();
        if(TempData.getKills(p) > 7)e.setTag(ChatColor.RED + p.getName());
        else if(TempData.getHeals(p) > 14)e.setTag(ChatColor.GREEN + p.getName());

    }
}
