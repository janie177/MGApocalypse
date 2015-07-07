package com.minegusta.mgapocalypse.perks.abilities;

import com.minegusta.mgapocalypse.perks.IPerk;
import com.minegusta.mgapocalypse.perks.Perk;
import org.bukkit.Material;

public class Stealth implements IPerk {
    @Override
    public String[] getDescription(int level) {
        return new String[]{"You are less", "visible to zombies.", "Next level: -" + level * 2 + " % visible"};
    }

    @Override
    public Material getMaterial() {
        return Material.FEATHER;
    }

    @Override
    public Type getType() {
        return Type.UPGRADE;
    }

    @Override
    public int getMaxLevel() {
        return 15;
    }

    @Override
    public String getName() {
        return "Stealth";
    }

    @Override
    public int getDataValue() {
        return 0;
    }

    @Override
    public int getCost() {
        return 2;
    }

    @Override
    public Perk getPerk() {
        return Perk.STEALTH;
    }
}
