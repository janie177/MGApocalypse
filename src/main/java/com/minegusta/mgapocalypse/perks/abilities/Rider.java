package com.minegusta.mgapocalypse.perks.abilities;

import com.minegusta.mgapocalypse.perks.IPerk;
import com.minegusta.mgapocalypse.perks.Perk;
import org.bukkit.Material;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

public class Rider implements IPerk {
    @Override
    public String[] getDescription(int level) {
        return new String[]{"Summon a horse", "on your location.", "One-time buy."};
    }

    @Override
    public Material getMaterial() {
        return Material.SADDLE;
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
        return "Rider";
    }

    @Override
    public int getDataValue() {
        return 0;
    }

    @Override
    public int getCost() {
        return 25;
    }

    @Override
    public Perk getPerk() {
        return Perk.RIDER;
    }

    @Override
    public void apply(Player p)
    {


    }
}
