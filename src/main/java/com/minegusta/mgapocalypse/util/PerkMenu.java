package com.minegusta.mgapocalypse.util;

import com.google.common.collect.Lists;
import com.minegusta.mgapocalypse.MGApocalypse;
import com.minegusta.mgapocalypse.files.MGPlayer;
import com.minegusta.mgapocalypse.perks.Perk;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PerkMenu {

    private static ItemStack bg = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13) {
        {
            ItemMeta meta = getItemMeta();
            meta.setDisplayName(" ");
            setItemMeta(meta);
        }
    };

    public static Inventory build(Player p) {
        Inventory inv = Bukkit.createInventory(p, 9 * 6, getInventoryName(p));

        fillInventory(p, inv);

        return inv;
    }

    public static String getInventoryName(Player p) {
        return ChatColor.DARK_RED + "~" + ChatColor.GOLD + "PerkPoints: " + ChatColor.LIGHT_PURPLE + MGApocalypse.getMGPlayer(p).getPerkPoints() + ChatColor.DARK_RED + "~";
    }

    private static void fillInventory(Player p, Inventory inventory) {
        MGPlayer mgp = MGApocalypse.getMGPlayer(p);

        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, bg);
        }

        int data = 1;
        for(int i = 45; i < 54; i++)
        {
            inventory.getItem(i).setDurability((short) data);
            data++;
        }

        data = 1;
        for(int i = 0; i < 9; i++)
        {
            inventory.getItem(i).setDurability((short) data);
            data++;
        }

        for (Perk perk : Perk.values()) {
            int level = mgp.getPerkLevel(perk);
            int amount = level;
            if(amount == 0) amount++;
            if (amount > 64) amount = 64;

            Material mat = perk.getMaterial();
            int mdata = perk.getDataValue();

            if(level == 0 && perk.getMaxLevel() != 0)
            {
                mat = Material.BARRIER;
                mdata = 0;
            }

            inventory.setItem(perk.getSlot(), new ItemStack(mat, amount, (short) mdata) {
                {
                    ItemMeta meta = getItemMeta();
                    meta.setDisplayName(ChatColor.DARK_RED + perk.getName());
                    List<String> lore = Lists.newArrayList();

                    if (level >= perk.getMaxLevel() && perk.getMaxLevel() != 0) {
                        lore.add(ChatColor.BOLD + "" + ChatColor.DARK_GRAY + "Max Level Reached!");
                    } else {
                        lore.add(ChatColor.YELLOW + "Cost: " + ChatColor.LIGHT_PURPLE + perk.getCost());
                        lore.add(ChatColor.YELLOW + "Type: " + ChatColor.AQUA + perk.getType().name());
                    }

                    for (String s : perk.description(level + 1)) {
                        lore.add(ChatColor.GREEN + s);
                    }

                    meta.setLore(lore);
                    setItemMeta(meta);
                }
            });
        }
    }
}
