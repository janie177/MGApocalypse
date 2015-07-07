package com.minegusta.mgapocalypse.perks.abilities;

import com.minegusta.mgapocalypse.perks.IPerk;
import com.minegusta.mgapocalypse.perks.Perk;
import org.bukkit.Material;

public class Farmer implements IPerk {
    @Override
    public String[] getDescription(int level) {
        return new String[]{"Crops sometimes", "give more loot", "Next level: +" + level * 2 + "%"};
    }

    @Override
    public Material getMaterial() {
        return Material.STONE_HOE;
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
        return "Farmer";
    }

    @Override
    public int getDataValue() {
        return 0;
    }

    @Override
    public int getCost() {
        return 1;
    }

    @Override
    public Perk getPerk() {
        return Perk.FARMER;
    }

}
