package com.minegusta.mgapocalypse.util;

import com.minegusta.mgapocalypse.config.DefaultConfig;
import org.bukkit.World;

import java.util.List;

public class WorldCheck
{
    private static final List<String> worlds = DefaultConfig.getWorlds();

    public static boolean is(World w)
    {
        return worlds.contains(w.getName());
    }
}
