package com.minegusta.mgapocalypse.perks.abilities;

import com.minegusta.mgapocalypse.perks.IPerk;
import com.minegusta.mgapocalypse.perks.Perk;
import org.bukkit.Material;

public class Hydration implements IPerk {
    @Override
    public String[] getDescription(int level) {
        return new String[]{"You can survive", "longer without water.", "Next level: " + level + " extra water."};
    }

    @Override
    public Material getMaterial() {
        return Material.POTION;
    }

    @Override
    public Type getType() {
        return Type.UPGRADE;
    }

    @Override
    public int getMaxLevel() {
        return 10;
    }

    @Override
    public String getName() {
        return "Hydration";
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
        return Perk.HYDRATION;
    }
}
