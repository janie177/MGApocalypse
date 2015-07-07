package com.minegusta.mgapocalypse.perks.abilities;

import com.minegusta.mgapocalypse.perks.IPerk;
import com.minegusta.mgapocalypse.perks.Perk;
import org.bukkit.Material;

public class Looter implements IPerk {
    @Override
    public String[] getDescription(int level) {
        return new String[]{"You find more", "loot in chests.", "Next level: +" + level * 3 + "%"};
    }

    @Override
    public Material getMaterial() {
        return Material.GOLD_INGOT;
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
        return "Looter";
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
        return Perk.LOOTER;
    }
}
