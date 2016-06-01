package com.minegusta.mgapocalypse.listeners;

import com.minegusta.mgapocalypse.util.SpawnLocationInventoryHolder;
import com.minegusta.mgapocalypse.util.SpawnLocationMenu;
import com.minegusta.mgapocalypse.util.WorldCheck;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SpawnMenuListener implements Listener {

	@EventHandler
	public void onSpawnMenuClick(InventoryClickEvent e)
	{
		if(!WorldCheck.is(e.getWhoClicked().getWorld()) || e.getClickedInventory() == null || e.getCurrentItem() == null || !(e.getWhoClicked() instanceof Player) || e.getCurrentItem().getType() == Material.AIR)
		{
			return;
		}

		//Its the spawn menu
		if(e.getClickedInventory().getHolder() instanceof SpawnLocationInventoryHolder)
		{
			e.setCancelled(true);
			SpawnLocationMenu.process((Player) e.getWhoClicked(), e.getSlot());
		}
	}
}
