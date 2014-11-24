package com.minegusta.mgapocalypse.util;

import com.minegusta.mgapocalypse.items.LootItem;
import org.bukkit.inventory.ItemStack;

public enum RespawnKit
{
    DEFAULT("wasteland.default", LootItem.WATERBOTTLE.build(), LootItem.WOODSWORD.build(), LootItem.BANDAGE.build(), LootItem.LEATHERCHEST.build());

    private String permission;
    private ItemStack[] items;

    private RespawnKit(String permissionsNode, ItemStack... items)
    {
        this.items = items;
        this.permission = permissionsNode;
    }

    public String getPermissionsNode()
    {
        return permission;
    }

    public ItemStack[] getItems()
    {
        return items;
    }
}
