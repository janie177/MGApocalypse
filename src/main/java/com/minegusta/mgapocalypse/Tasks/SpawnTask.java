package com.minegusta.mgapocalypse.Tasks;

import com.minegusta.mgapocalypse.Main;
import com.minegusta.mgapocalypse.config.DefaultConfig;
import com.minegusta.mgapocalypse.util.RandomNumber;
import com.minegusta.mgapocalypse.util.StringLocConverter;
import com.minegusta.mgapocalypse.util.WorldCheck;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

import java.util.List;

public class SpawnTask {
    private static int SPAWNTASK = -1;

    //-- FINAL VARIABLES --//
    private final static int interval = 10; //Interval in seconds to spawn new zombies.
    private final static int townDistance = 130; //The distance from towns that counts as a town still.
    private final static int zombieRadius = 100; //The radius in which there's a zombie limit.
    private final static int maxZombieAmount = 16; //The maximum amount of zombies near a player.
    private final static int townMaxZombieAmount = 35; //The maximum amount of zombies near a player in towns.
    private final static int spawnChance = 200; //Promillage chance to spawn a zombie group.
    private final static int townSpawnChance = 700; //Promillage chance to spawn a zombie group in towns.
    private final static int maxGroupSize = 6; //The maximum group size for zombies.
    private final static List<String> towns = DefaultConfig.getTowns(); //All towns defined.


    public static void start() {
        SPAWNTASK = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.PLUGIN, () ->
        {
            {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    //Making sure the player is in an enabled world.
                    if (!WorldCheck.is(p.getWorld())) return;

                    int chance = RandomNumber.get(1000);
                    if (!(chance <= townSpawnChance)) return;

                    Location l = p.getLocation();
                    boolean town = false;

                    //Check if in a town.
                    for (String s : towns) {
                        if (StringLocConverter.stringToLocation(s).distance(l) < townDistance) {
                            town = true;
                            break;
                        }
                    }

                    //Check if the spawn is if it's not a town.
                    if (!town) {
                        if (!(chance <= spawnChance)) return;
                    }

                    //Check if the player does not have too many zombies around them already.
                    int zombieAmount = (int) p.getWorld().getLivingEntities().stream().filter(ent -> ent instanceof Zombie && ent.getLocation().distance(p.getLocation()) <= zombieRadius).count();

                    if (town) {
                        if (zombieAmount >= townMaxZombieAmount) return;
                    } else {
                        if (zombieAmount >= maxZombieAmount) return;
                    }

                    //Find the location and then spawn the mobs.

                    int min = 48;
                    int max = 70;
                    if (town) {
                        min = 28;
                        max = 50;
                    }

                    int x = RandomNumber.get(min, max);
                    int z = RandomNumber.get(min, max);
                    if (RandomNumber.getBoolean()) x = -x;
                    if (RandomNumber.getBoolean()) z = -z;

                    //Check if there is no players near the new spawn point.
                    Location spawnLoc = noPlayersNear(l.add(x, 0, z));

                    if (spawnLoc.getBlock().getType() != Material.AIR) {
                        Location newLoc = ascend(spawnLoc);
                        spawnZombie(newLoc);
                    } else if (spawnLoc.getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR) {
                        Location newLoc = descend(spawnLoc);
                        spawnZombie(newLoc);
                    } else {
                        spawnZombie(spawnLoc);
                    }
                }
            }
        }, 20 * interval, 20 * interval);
    }

    private static Location descend(Location l) {
        if (l.getY() < 20) return l.getWorld().getHighestBlockAt(l.getBlockX(), l.getBlockZ()).getLocation();
        Location newL = l.add(0, -2, 0);
        if (newL.getBlock().getType() != Material.AIR) {
            return newL.add(0, 2, 0);
        } else return descend(newL);
    }

    private static Location ascend(Location l) {
        if (l.getY() > 100) return l.getWorld().getHighestBlockAt(l.getBlockX(), l.getBlockZ()).getLocation();
        Location newL = l.add(0, 2, 0);
        if (newL.getBlock().getType() == Material.AIR) {
            return newL;
        } else return descend(newL);
    }

    public static boolean stop() {
        if (SPAWNTASK == -1) return false;
        Bukkit.getScheduler().cancelTask(SPAWNTASK);
        return true;
    }

    private static void spawnZombie(Location loc) {
        //Spawn a zombie
        Zombie zombie = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
        zombie.setCanPickupItems(false);

        for (int i = 0; i <= RandomNumber.get(maxGroupSize); i++) {
            Location random = zombie.getLocation().add(RandomNumber.getDouble(10) - 5, 0, RandomNumber.getDouble(10) - 5);
            Zombie zombie2 = (Zombie) loc.getWorld().spawnEntity(random, EntityType.ZOMBIE);
            zombie2.setCanPickupItems(false);
            if (RandomNumber.get(25) == 1) {
                zombie2.setBaby(true);
            }
        }
    }

    private static Location noPlayersNear(Location spawnLoc) {
        int players = (int) spawnLoc.getWorld().getPlayers().stream().filter(entity -> entity.getLocation().distance(spawnLoc) <= 20).count();
        if (players > 0) {
            return noPlayersNear(spawnLoc.add(spawnLoc.getX() - 20, 0, spawnLoc.getZ() - 20));
        }
        return spawnLoc;
    }

}
