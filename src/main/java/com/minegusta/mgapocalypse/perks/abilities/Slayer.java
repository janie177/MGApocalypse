package com.minegusta.mgapocalypse.perks.abilities;

import com.minegusta.mgapocalypse.perks.IPerk;
import com.minegusta.mgapocalypse.perks.Perk;
import org.bukkit.Material;

public class Slayer implements IPerk
{
    @Override
    public String[] getDescription(int level) {
        return new String[]{"Do more damage", "with swords.", "Next level: " + level * 2 + "%"};
    }

    @Override
    public Material getMaterial() {
        return Material.IRON_SWORD;
    }

    @Override
    public Type getType() {
        return Type.UPGRADE;
    }

    @Override
    public int getMaxLevel() {
        return 50;
    }

    @Override
    public String getName() {
        return "Slayer";
    }

    @Override
    public int getDataValue() {
        return 0;
    }

    @Override
    public int getCost() {
        return 2;
    }

    @Override
    public Perk getPerk() {
        return Perk.SLAYER;
    }
}
