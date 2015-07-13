package com.minegusta.mgapocalypse.perks.abilities;

import com.minegusta.mgapocalypse.perks.IPerk;
import com.minegusta.mgapocalypse.perks.Perk;
import org.bukkit.Material;

public class Bowman implements IPerk {

    @Override
    public String[] getDescription(int level) {
        return new String[]{"Do more damage", "with bows.", "Next level: +" + level * 2 + "%"};
    }

    @Override
    public Material getMaterial() {
        return Material.BOW;
    }

    @Override
    public Type getType() {
        return Type.UPGRADE;
    }

    @Override
    public int getMaxLevel() {
        return 30;
    }

    @Override
    public String getName() {
        return "BowMan";
    }

    @Override
    public int getDataValue() {
        return 0;
    }

    @Override
    public int getCost() {
        return 5;
    }

    @Override
    public Perk getPerk() {
        return Perk.BOWMAN;
    }
}
