package com.minegusta.mgapocalypse.listeners;

import com.minegusta.mgapocalypse.util.InfoMenu;
import com.minegusta.mgapocalypse.util.WorldCheck;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListener implements Listener
{
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        if(!WorldCheck.is(e.getWhoClicked().getWorld())) return;

        if(e.getClickedInventory().getName().equals(InfoMenu.getInventoryName()))
        {
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
        }
    }

}
