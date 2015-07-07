package com.minegusta.mgapocalypse.perks;

import org.bukkit.Material;

public interface IPerk
{
    public enum Type
    {
        INSTANT, UPGRADE;
    }

    String[] getDescription(int level);

    Material getMaterial();

    Type getType();

    int getMaxLevel();

    String getName();

    int getDataValue();

    int getCost();

    Perk getPerk();

    void apply();
}
