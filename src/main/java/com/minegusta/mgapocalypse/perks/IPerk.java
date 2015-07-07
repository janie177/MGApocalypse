package com.minegusta.mgapocalypse.perks;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public interface IPerk {
    public enum Type {
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

    default void apply(Player p) {

    }
}
