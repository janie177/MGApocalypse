package com.minegusta.mgapocalypse.perks.abilities;

import com.minegusta.mgapocalypse.perks.IPerk;
import com.minegusta.mgapocalypse.perks.Perk;
import org.bukkit.Material;

public class Resistance implements IPerk {
    @Override
    public String[] getDescription(int level) {
        return new String[]{"You are more", "resistant to bleeding", "and disease infection.", "Next level: +" + level * 2 + "% resistance."};
    }

    @Override
    public Material getMaterial() {
        return Material.MILK_BUCKET;
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
        return "Resistance";
    }

    @Override
    public int getDataValue() {
        return 0;
    }

    @Override
    public int getCost() {
        return 3;
    }

    @Override
    public Perk getPerk() {
        return Perk.RESISTANCE;
    }
}
