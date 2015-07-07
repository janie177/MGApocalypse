package com.minegusta.mgapocalypse.perks.abilities;

import com.minegusta.mgapocalypse.perks.IPerk;
import com.minegusta.mgapocalypse.perks.Perk;
import org.bukkit.Material;

public class Health implements IPerk {
    @Override
    public String[] getDescription(int level) {
        return new String[]{"You get", "more health.", "Next level: +" + level * 0.5 + " hearts."};
    }

    @Override
    public Material getMaterial() {
        return Material.APPLE;
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
        return "Health";
    }

    @Override
    public int getDataValue() {
        return 0;
    }

    @Override
    public int getCost() {
        return 20;
    }

    @Override
    public Perk getPerk() {
        return Perk.HEALTH;
    }
}
