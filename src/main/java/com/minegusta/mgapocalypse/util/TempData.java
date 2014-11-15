package com.minegusta.mgapocalypse.util;

import com.google.common.collect.Maps;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class TempData
{
    public static ConcurrentMap<String, Break> breakMap = Maps.newConcurrentMap();
    public static ConcurrentMap<String, Long> healMap = Maps.newConcurrentMap();
    public static ConcurrentMap<String, Long> healCoolDownMap = Maps.newConcurrentMap();
    public static ConcurrentMap<String, Long> diseaseMap = Maps.newConcurrentMap();
    public static ConcurrentMap<String, Long> bleedingMap = Maps.newConcurrentMap();
    public static ConcurrentMap<String, List<ItemStack>> deathMap = Maps.newConcurrentMap();
}
