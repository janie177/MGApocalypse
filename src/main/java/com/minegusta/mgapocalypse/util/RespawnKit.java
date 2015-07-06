package com.minegusta.mgapocalypse.util;

import com.minegusta.mgloot.loottables.LootItem;
import org.bukkit.inventory.ItemStack;

public enum RespawnKit {
    DEFAULT("wasteland.default", LootItem.WATERBOTTLE.build(), LootItem.WOODSWORD.build(), LootItem.BANDAGE.build(), LootItem.LEATHERCHEST.build()),
    DONOR("wasteland.donor", LootItem.LEATHERLEGS.build(), LootItem.STONEAXE.build(), LootItem.APPLE.build());

    private String permission;
    private ItemStack[] items;

    private RespawnKit(String permissionsNode, ItemStack... items) {
        this.items = items;
        this.permission = permissionsNode;
    }

    public String getPermissionsNode() {
        return permission;
    }

    public ItemStack[] getItems() {
        return items;
    }
}
