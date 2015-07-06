package com.minegusta.mgapocalypse.util;

import com.google.common.collect.Lists;
import com.minegusta.mgapocalypse.MGApocalypse;
import com.minegusta.mgapocalypse.files.MGPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class InfoMenu
{
    //--// Static Stacks //--//
    private static ItemStack redStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 6)
    {
        {
            ItemMeta meta = getItemMeta();
            meta.setDisplayName("");
            setItemMeta(meta);
        }
    };
    private static ItemStack greenStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5) {
        {
            {
                ItemMeta meta = getItemMeta();
                meta.setDisplayName("");
                setItemMeta(meta);
            }
        }
    };
    private static List<Integer> green = Lists.newArrayList(3,5,21,23);
    private static List<Integer> red = Lists.newArrayList(0,1,2,6,7,8,9,11,15,17,18,19,20,24,25,26,31,32,33,39,41,48,49,50);

    //--// Methods //--//

    public static Inventory build(MGPlayer mgp)
    {
        Inventory inv = getInv();
        fillInventory(inv, mgp);

        return inv;
    }

    private static Inventory getInv()
    {
        return Bukkit.createInventory(null, 9 * 6, getInventoryName());
    }

    public static String getInventoryName()
    {
        return ChatColor.GOLD + "~~=~ " + ChatColor.DARK_RED + "Info Menu" + ChatColor.GOLD + " ~=~~";
    }

    private static void fillInventory(Inventory inv, MGPlayer mgp)
    {
        for(int i = 0; i < inv.getSize(); i++)
        {
            if(green.contains(i)) inv.setItem(i, greenStack);
            else if(red.contains(i)) inv.setItem(i, redStack);
        }

        //Set all the items by hand... :(

        inv.setItem(4, getItem("Total Heals", Material.PAPER, mgp.getTotalHeals()));
        inv.setItem(10, getItem("This Life", Material.BOOK, "The left side", "shows info on", "your current life."));
        inv.setItem(12, getItem("Total Players Killed", Material.SKULL_ITEM, 3, mgp.getTotalPlayerKills()));
        inv.setItem(13, getItem("Total Zombie Kills", Material.SKULL_ITEM, 2, mgp.getTotalZombieKills()));
        inv.setItem(14, getItem("Total Time Played", Material.WATCH, 0, Integer.toString(mgp.getPlayTime()), "Minutes."));
        inv.setItem(16, getItem("Overal Stats", Material.BOOK, "The right side", "shows info on", "your all-time stats."));
        inv.setItem(22, getItem("Total Giant Kills", Material.MONSTER_EGG, 54, mgp.getTotalGiantKills()));
        inv.setItem(27, getItem("Player Kills", Material.SKULL_ITEM, 3, Integer.toString(mgp.getPlayerKills()), "This life."));
        inv.setItem(28, getItem("Zombie Kills", Material.SKULL_ITEM, 2, Integer.toString(mgp.getZombieKills()), "This life."));
        inv.setItem(29, getItem("Giant Kills", Material.MONSTER_EGG, 54, Integer.toString(mgp.getGiantKills()), "This life."));
        inv.setItem(33, getItem("Emerald Chests", Material.EMERALD_BLOCK, mgp.getEmeraldChestsLooted()));
        inv.setItem(34, getItem("Most Zombie Kills", Material.SKULL_ITEM, 2, mgp.getLongestKillStreak()));
        inv.setItem(35, getItem("Most Giant Kills", Material.MONSTER_EGG, 54, mgp.getMostGiantKills()));
        inv.setItem(36, getItem("Heals", Material.PAPER, 0, Integer.toString(mgp.getHeals()), "This life."));
        inv.setItem(37, getItem("Time Alive", Material.WATCH, 0, Integer.toString(mgp.getTimeAlive()), "Minutes."));
        inv.setItem(38, getItem("Perks Bought", Material.DIAMOND, mgp.getPerksBought()));
        inv.setItem(40, getItem("Credits Earned", Material.GOLD_NUGGET, 0, mgp.getEarnedcredits()));
        inv.setItem(42, getItem("Diamond Chests", Material.DIAMOND_BLOCK, mgp.getDiamondChestsLooted()));
        inv.setItem(43, getItem("Longest Survived", Material.WATCH, 0, Integer.toString(mgp.getLongestAlive()), "Minutes."));
        inv.setItem(44, getItem("Most Heals", Material.PAPER, mgp.getMostHeals()));
        inv.setItem(45, getItem("Infected", Material.MILK_BUCKET, Boolean.toString(mgp.isInfected())));
        inv.setItem(46, getItem("Bleeding", Material.REDSTONE, Boolean.toString(mgp.isBleeding())));

        if(mgp.getOfflinePlayer().isOnline())
        {
            inv.setItem(47, getItem("Thirst Level", Material.POTION, mgp.getPlayer().getLevel()));
        }
        else inv.setItem(47, getItem("Thirst Level", Material.POTION, mgp.getThirst()));

        inv.setItem(51, getItem("Iron Chests", Material.IRON_BLOCK, mgp.getIronChestsLooted()));
        inv.setItem(52, getItem("Low Level Chests", Material.CHEST, mgp.getChestsLooted()));
        inv.setItem(53, getItem("Deaths", Material.BONE, mgp.getDeaths()));
    }

    private static ItemStack getItem(String name, Material m, int data, long lore)
    {
        return getItem(name, m, data, Long.toString(lore));
    }

    private static ItemStack getItem(String name, Material m, int data, int lore)
    {
        return getItem(name , m, data, Integer.toString(lore));
    }

    private static ItemStack getItem(String name, Material m, long lore)
    {
        return getItem(name, m, 0, Long.toString(lore));
    }

    private static ItemStack getItem(String name, Material m, int lore)
    {
        return getItem(name , m, 0, Integer.toString(lore));
    }

    private static ItemStack getItem(String name, Material m, String... lore)
    {
        return getItem(name , m, 0, lore);
    }

    private static ItemStack getItem(String name, Material m, int data, String... lore)
    {
        return new ItemStack(m, 1, (short) data)
        {
            {
                ItemMeta meta = getItemMeta();
                meta.setDisplayName(ChatColor.GREEN + name);
                List<String> loreList = Lists.newArrayList();
                for (String s : lore)
                {
                    loreList.add(ChatColor.YELLOW + s);
                }
                meta.setLore(loreList);
                setItemMeta(meta);
            }
        };
    }
}
