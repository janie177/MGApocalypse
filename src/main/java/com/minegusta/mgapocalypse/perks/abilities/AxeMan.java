package com.minegusta.mgapocalypse.perks.abilities;

import com.minegusta.mgapocalypse.perks.IPerk;
import com.minegusta.mgapocalypse.perks.Perk;
import org.bukkit.Material;

public class AxeMan implements IPerk {
    @Override
    public String[] getDescription(int level) {
        return new String[]{"Do more damage", "with Axes.", "Next level: +" + level * 2 + "%"};
    }

    @Override
    public Material getMaterial() {
        return Material.IRON_AXE;
    }

    @Override
    public Type getType() {
        return Type.UPGRADE;
    }

    @Override
    public int getMaxLevel() {
        return 35;
    }

    @Override
    public String getName() {
        return "AxeMan";
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
        return Perk.AXEMAN;
    }
}
