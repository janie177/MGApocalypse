package com.minegusta.mgapocalypse.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemUtil
{
    public static void removeOne(Player p, Material m)
    {
        if(p.getInventory().containsAtLeast(new ItemStack(m), 2))
        {
            for(ItemStack i : p.getInventory())
            {
                if(i.getType() == m)
                {
                    if(i.getAmount() > 1)
                    {
                        i.setAmount(i.getAmount() - 1);
                    }
                    else
                    {
                        i.setType(Material.AIR);
                    }
                }
            }
        }
        else
        {
            p.getInventory().remove(m);
        }
        p.updateInventory();
    }
}
