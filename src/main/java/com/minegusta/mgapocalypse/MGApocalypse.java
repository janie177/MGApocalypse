package com.minegusta.mgapocalypse;


import com.minegusta.mgapocalypse.files.MGPlayer;
import com.minegusta.mgapocalypse.files.Storage;
import org.bukkit.entity.Player;

public class MGApocalypse {
    public static MGPlayer getMGPlayer(Player p) {
        return Storage.getMGPlayer(p.getUniqueId().toString());
    }

    public static void addMGPlayer(Player p) {
        Storage.addPlayer(p.getUniqueId().toString());
    }

    public static void removeMGPlayer(Player p) {
        Storage.removePlayer(p.getUniqueId().toString());
    }

    public static MGPlayer[] getMGPlayers() {
        return (MGPlayer[]) Storage.players.values().toArray();
    }
}
