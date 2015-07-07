package com.minegusta.mgapocalypse.perks.abilities;

import com.minegusta.mgapocalypse.perks.IPerk;
import com.minegusta.mgapocalypse.perks.Perk;
import org.bukkit.Material;

public class Athlete implements IPerk {
    @Override
    public String[] getDescription(int level) {
        return new String[]{"Increase movement speed.", "Next level: +" + level * 0.5 + "%"};
    }

    @Override
    public Material getMaterial() {
        return Material.LEATHER_BOOTS;
    }

    @Override
    public Type getType() {
        return Type.UPGRADE;
    }

    @Override
    public int getMaxLevel() {
        return 25;
    }

    @Override
    public String getName() {
        return "Athlete";
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
        return Perk.ATHLETE;
    }

}
