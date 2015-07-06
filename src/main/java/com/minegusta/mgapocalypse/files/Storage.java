package com.minegusta.mgapocalypse.files;

import com.google.common.collect.Maps;

import java.util.concurrent.ConcurrentMap;

public class Storage {
    public static ConcurrentMap<String, MGPlayer> players = Maps.newConcurrentMap();

    public static void addPlayer(String uuid) {
        players.put(uuid, MGPlayer.build(uuid, FileManager.getFile(uuid)));
    }

    public static void removePlayer(String uuid) {
        if (containsPlayer(uuid))
        {
            players.get(uuid).saveFile();
            players.remove(uuid);
        }
    }

    public static boolean containsPlayer(String uuid) {
        return players.containsKey(uuid);
    }

    public static MGPlayer getMGPlayer(String uuid) {
        if (containsPlayer(uuid)) {
            return players.get(uuid);
        }
        addPlayer(uuid);
        return players.get(uuid);
    }
}
