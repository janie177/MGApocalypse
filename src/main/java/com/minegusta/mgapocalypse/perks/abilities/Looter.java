package com.minegusta.mgapocalypse.perks.abilities;

import com.minegusta.mgapocalypse.perks.IPerk;
import com.minegusta.mgapocalypse.perks.Perk;
import org.bukkit.Material;

public class Looter implements IPerk {
    @Override
    public String[] getDescription(int level) {
        return new String[0];
    }

    @Override
    public Material getMaterial() {
        return null;
    }

    @Override
    public Type getType() {
        return null;
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getDataValue() {
        return 0;
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public Perk getPerk() {
        return null;
    }

    @Override
    public void apply() {

    }
}
