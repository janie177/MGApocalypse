package com.minegusta.mgapocalypse.perks.abilities;

import com.google.common.collect.Maps;
import com.minegusta.mgapocalypse.perks.IPerk;
import com.minegusta.mgapocalypse.perks.Perk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentMap;

public class AirDrop implements IPerk {

    public static ConcurrentMap<FallingBlock, Boolean> blocks = Maps.newConcurrentMap();

    @Override
    public String[] getDescription(int level) {
        return new String[]{"Random supplies drop", "on your location."};
    }

    @Override
    public Material getMaterial() {
        return Material.CHEST;
    }

    @Override
    public Type getType() {
        return Type.INSTANT;
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public String getName() {
        return "AirDrop";
    }

    @Override
    public int getDataValue() {
        return 0;
    }

    @Override
    public int getCost() {
        return 10;
    }

    @Override
    public Perk getPerk() {
        return Perk.AIRDROP;
    }

    @Override
    public void apply(Player p) {
        Location l = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() + 30, p.getLocation().getZ());

        byte blockdata = 0x0;

        FallingBlock block = l.getWorld().spawnFallingBlock(l, Material.TRAPPED_CHEST, blockdata);

        block.setDropItem(false);

        blocks.put(block, false);


    }
}
