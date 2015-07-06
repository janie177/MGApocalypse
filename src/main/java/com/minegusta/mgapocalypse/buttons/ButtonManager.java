package com.minegusta.mgapocalypse.buttons;

import com.google.common.collect.Maps;
import org.bukkit.Location;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class ButtonManager {
    public static ConcurrentMap<Location, Long> buttonMap = Maps.newConcurrentMap();

    public static void despawnButon(Location l) {
        if (!buttonMap.containsKey(l) || TimeUnit.NANOSECONDS.toSeconds(System.currentTimeMillis() - buttonMap.get(l)) > 50) {
            buttonMap.put(l, System.currentTimeMillis());
            new DespawnButton(l).start();
        }
    }

}
