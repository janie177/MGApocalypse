package com.minegusta.mgapocalypse.perks.abilities;

import com.minegusta.mgapocalypse.MGApocalypse;
import com.minegusta.mgapocalypse.perks.IPerk;
import com.minegusta.mgapocalypse.perks.Perk;
import org.bukkit.Material;
import org.bukkit.entity.Player;

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
        return 12;
    }

    @Override
    public Perk getPerk() {
        return Perk.HEALTH;
    }

    @Override
    public void apply(Player p)
    {
        setBoost(p);
    }

    public static void setBoost(Player p)
    {
        double health = 20 + MGApocalypse.getMGPlayer(p).getPerkLevel(Perk.HEALTH);
        p.setMaxHealth(health);
        p.setHealthScale(health);
    }

    public static void reset(Player p)
    {
        p.setMaxHealth(20);
        p.setHealthScale(20);
    }
}
