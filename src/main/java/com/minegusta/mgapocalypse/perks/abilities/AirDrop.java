package com.minegusta.mgapocalypse.perks.abilities;

import com.minegusta.mgapocalypse.perks.IPerk;
import com.minegusta.mgapocalypse.perks.Perk;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class AirDrop implements IPerk {
    @Override
    public String[] getDescription(int level) {
        return new String[]{"Random supplies drop", "on your location."};
    }

    @Override
    public Material getMaterial() {
        return Material.CHEST;
    }

    @Override
    public Type getType() {
        return Type.INSTANT;
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public String getName() {
        return "AirDrop";
    }

    @Override
    public int getDataValue() {
        return 0;
    }

    @Override
    public int getCost() {
        return 10;
    }

    @Override
    public Perk getPerk() {
        return Perk.AIRDROP;
    }

    @Override
    public void apply(Player p) {

    }
}
