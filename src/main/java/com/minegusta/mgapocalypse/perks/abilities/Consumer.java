package com.minegusta.mgapocalypse.perks.abilities;

import com.minegusta.mgapocalypse.perks.IPerk;
import com.minegusta.mgapocalypse.perks.Perk;
import org.bukkit.Material;

public class Consumer implements IPerk {
    @Override
    public String[] getDescription(int level) {
        return new String[]{"Food drains", "a little slower.", "Next level: -" + level * 3 + "%"};
    }

    @Override
    public Material getMaterial() {
        return Material.COOKIE;
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
        return "Consumer";
    }

    @Override
    public int getDataValue() {
        return 0;
    }

    @Override
    public int getCost() {
        return 6;
    }

    @Override
    public Perk getPerk() {
        return Perk.CONSUMER;
    }

}
