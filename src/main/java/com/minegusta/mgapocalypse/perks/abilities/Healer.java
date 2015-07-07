package com.minegusta.mgapocalypse.perks.abilities;

import com.minegusta.mgapocalypse.perks.IPerk;
import com.minegusta.mgapocalypse.perks.Perk;
import org.bukkit.Material;

public class Healer implements IPerk {
    @Override
    public String[] getDescription(int level) {
        return new String[]{"You heal others", "more health.", "Next level: +" + level * 3 + "%"};
    }

    @Override
    public Material getMaterial() {
        return Material.PAPER;
    }

    @Override
    public Type getType() {
        return Type.UPGRADE;
    }

    @Override
    public int getMaxLevel() {
        return 20;
    }

    @Override
    public String getName() {
        return "Healer";
    }

    @Override
    public int getDataValue() {
        return 0;
    }

    @Override
    public int getCost() {
        return 4;
    }

    @Override
    public Perk getPerk() {
        return Perk.HEALER;
    }
}
