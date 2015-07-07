package com.minegusta.mgapocalypse.perks.abilities;

import com.minegusta.mgapocalypse.MGApocalypse;
import com.minegusta.mgapocalypse.perks.IPerk;
import com.minegusta.mgapocalypse.perks.Perk;
import org.bukkit.Material;
import org.bukkit.entity.Player;

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
        return 4;
    }

    @Override
    public Perk getPerk() {
        return Perk.ATHLETE;
    }

    @Override
    public void apply(Player p)
    {
        setBoost(p);
    }

    public static void setBoost(Player p)
    {
        double speed = 1 + MGApocalypse.getMGPlayer(p).getPerkLevel(Perk.ATHLETE) * 0.5;
        p.setWalkSpeed((float) speed);
    }

    public static void reset(Player p)
    {
        p.setWalkSpeed(1);
    }

}
