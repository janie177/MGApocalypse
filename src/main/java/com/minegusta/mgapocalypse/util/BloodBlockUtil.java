package com.minegusta.mgapocalypse.util;

import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;

public class BloodBlockUtil {

    private static List<Block> getBlocks(Location l)
    {
        List<Block> blocks = Lists.newArrayList();
        int radius = 5;

        for(int x = -radius; x < radius; x++)
        {
            for(int y = -2; y < 2; y++)
            {
                for(int z = -radius; z < radius; z++)
                {
                    Block b = l.getBlock().getRelative(x,y,z);

                    if(b.getLocation().distance(l) > 4 ) continue;

                    if(b.getType() != Material.AIR && b.getRelative(0,1,0).getType() == Material.AIR && com.minegusta.mgloot.util.RandomNumber.get(10) > 3)
                    {
                        blocks.add(b);
                    }
                }
            }
        }
        return blocks;

    }

    public static void applyBlood(Location l, List<Player> players)
    {
        List<Block> blocks = getBlocks(l);

        players.stream().forEach(p ->
        {
            blocks.stream().forEach(b ->
            {
                int[] bloodType = getBloodMaterial();
                p.sendBlockChange(b.getLocation(), bloodType[0], (byte) bloodType[1]);
            });
        });
    }

    private static final int[][] bloodTypes = new int[][]{{159,14}, {159,13}, {159,6}, {35,14}};

    private static int[] getBloodMaterial()
    {
        int index = RandomNumber.get(bloodTypes.length) - 1;
        return bloodTypes[index];
    }
}
