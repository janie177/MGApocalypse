package com.minegusta.mgapocalypse.listeners;

import com.minegusta.mgapocalypse.MGApocalypse;
import com.minegusta.mgapocalypse.files.MGPlayer;
import com.minegusta.mgapocalypse.perks.IPerk;
import com.minegusta.mgapocalypse.perks.Perk;
import com.minegusta.mgapocalypse.util.InfoMenu;
import com.minegusta.mgapocalypse.util.PerkMenu;
import com.minegusta.mgapocalypse.util.WorldCheck;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener
{
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        if(!WorldCheck.is(e.getWhoClicked().getWorld())) return;

        if(!(e.getWhoClicked() instanceof Player)) return;

        if(e.getClickedInventory() == null || e.getClick() == null || e.getAction() == null) return;

        if(e.getClickedInventory().getName().equals(InfoMenu.getInventoryName())) {
            e.setCancelled(true);
        }

        //Perk shop
        else if(e.getClickedInventory().getName().startsWith(ChatColor.DARK_RED + "~" + ChatColor.GOLD + "Perk Store " + ChatColor.YELLOW + "Points: "))
        {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            MGPlayer mgp = MGApocalypse.getMGPlayer(p);

            //Buy the clicked perk
            if (!e.getCursor().getType().equals(Material.AIR)) {
                p.updateInventory();
                return;
            }
            if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
                return;
            }
            if (!e.getCurrentItem().hasItemMeta() || !e.getCurrentItem().getItemMeta().hasDisplayName()) {
                return;
            }

            ItemStack clicked = e.getCurrentItem();
            Perk clickedPerk = null;

            for(Perk perk : Perk.values())
            {
                if(perk.getName().equals(clicked.getItemMeta().getDisplayName()))
                {
                    clickedPerk = perk;
                    break;
                }
            }

            if(clickedPerk == null)
            {
                return;
            }

            int maxLevel = clickedPerk.getMaxLevel();
            int level = mgp.getPerkLevel(clickedPerk);

            if(level >= maxLevel && maxLevel != 0)
            {
                p.sendMessage(ChatColor.RED + "You already have the maximum level for this perk!");
                return;
            }

            if(!mgp.removePerkPoints(clickedPerk.getCost()))
            {
                p.sendMessage(ChatColor.RED + "You do not have enough perkpoints!");
                return;
            }

            if(clickedPerk.getType() == IPerk.Type.INSTANT)
            {
                clickedPerk.apply();
                p.sendMessage(ChatColor.YELLOW + "You bought the instant boost " + clickedPerk.getName() + "!");
            }
            else
            {
                int newLevel = level + 1;
                p.sendMessage(ChatColor.YELLOW + "You bought " + clickedPerk.getName() + " level " + newLevel + "!");
                mgp.setPerkLevel(clickedPerk, newLevel);
            }

            //Re-Open the thing
            p.openInventory(PerkMenu.build(p));
        }

    }
}
