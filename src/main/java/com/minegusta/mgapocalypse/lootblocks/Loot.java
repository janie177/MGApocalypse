package com.minegusta.mgapocalypse.lootblocks;

import com.minegusta.mgapocalypse.items.LootItem;
import com.minegusta.mgapocalypse.util.RandomNumber;

public class Loot
{
    private static final LootItem[] grave = {LootItem.SKULL, LootItem.BONE, LootItem.GRENADE, LootItem.SNOWBALL, LootItem.ARROW_4, };
    private static final LootItem[] ore = {LootItem.COAL, LootItem.COAL, LootItem.COAL, LootItem.COAL, LootItem.COAL, LootItem.COAL, LootItem.COAL, LootItem.COAL, LootItem.IRONBAR, LootItem.IRONBAR, LootItem.IRONBAR, LootItem.IRONBAR, LootItem.DIAMOND};

    public static LootItem getOre()
    {
        return ore[RandomNumber.get(ore.length) - 1];
    }

    public static LootItem getGrave()
    {
        return grave[RandomNumber.get(grave.length) - 1];
    }
}
