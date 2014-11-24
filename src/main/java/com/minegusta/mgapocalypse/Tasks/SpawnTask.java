package com.minegusta.mgapocalypse.Tasks;

import com.minegusta.mgapocalypse.Main;
import com.minegusta.mgapocalypse.config.DefaultConfig;
import com.minegusta.mgapocalypse.util.RandomNumber;
import com.minegusta.mgapocalypse.util.StringLocConverter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

import java.util.List;

public class SpawnTask
{
    private static int SPAWNTASK = -1;

    //-- FINAL VARIABLES --//
    private final static int interval = 10; //Interval in seconds to spawn new zombies.
    private final static int townDistance = 40; //The distance from towns that counts as a town still.
    private final static int zombieRadius = 100; //The radius in which there's a zombie limit.
    private final static int maxZombieAmount = 20; //The maximum amount of zombies near a player.
    private final static int townMaxZombieAmount = 40; //The maximum amount of zombies near a player in towns.
    private final static int spawnChance = 5; //Promillage chance to spawn a zombie group.
    private final static int townSpawnChance = 10; //Promillage chance to spawn a zombie group in towns.
    private final static int maxGroupSize = 5; //The maximum group size for zombies.
    private final static List<String> towns = DefaultConfig.getTowns(); //All towns defined.




    public static void start()
    {
        SPAWNTASK = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.PLUGIN, new Runnable()
        {
            @Override
            public void run()
            {
                for(Player p : Bukkit.getOnlinePlayers()) {
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
                    int zombieAmount = 0;

                    for (Entity ent : p.getNearbyEntities(zombieRadius, zombieRadius, zombieRadius)) {
                        if (ent instanceof Zombie) zombieAmount++;
                    }
                    if (town) {
                        if (zombieAmount > maxZombieAmount) return;
                    } else
                    {
                        if (zombieAmount > townMaxZombieAmount) return;
                    }

                    //Find the location and then spawn the mobs.

                    int x = RandomNumber.get(48,70);
                    int z = RandomNumber.get(48,70);
                    if(RandomNumber.getBoolean())x = -x;
                    if(RandomNumber.getBoolean())z = -z;

                    //Check if there is no players near the new spawn point.
                    Location spawnLoc = noPlayersNear(l.add(x,0,z));

                    if(spawnLoc.getBlock().getType() != Material.AIR && spawnLoc.getBlock().getRelative(BlockFace.UP).getType() != Material.AIR)
                    {
                        Location newLoc = spawnLoc.getWorld().getHighestBlockAt(spawnLoc.getBlockX(), spawnLoc.getBlockZ()).getLocation().add(0,1,0);
                        spawnZombie(newLoc);
                    }
                    else
                    {
                        spawnZombie(spawnLoc);
                    }



                }
            }
        }, 20 * interval, 20 * interval);
    }

    public static boolean stop()
    {
        if(SPAWNTASK == -1)return false;
        Bukkit.getScheduler().cancelTask(SPAWNTASK);
        return true;
    }

    private static void spawnZombie(Location loc)
    {
        //Spawn a zombie
        Zombie zombie = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
        zombie.setCanPickupItems(false);

        for (int i = 0; i <= RandomNumber.get(maxGroupSize); i++)
        {
            Location random = zombie.getLocation().add(RandomNumber.getDouble(10) - 5,0,RandomNumber.getDouble(10) - 5);
            Zombie zombie2 = (Zombie) loc.getWorld().spawnEntity(random, EntityType.ZOMBIE);
            zombie2.setCanPickupItems(false);
            if (RandomNumber.get(25) == 1) {
                zombie2.setBaby(true);
            }
        }
    }

    private static Location noPlayersNear(Location spawnLoc)
    {
        Entity temp = spawnLoc.getWorld().spawnEntity(spawnLoc, EntityType.EXPERIENCE_ORB);
        for(Entity ent : temp.getNearbyEntities(20,10,20))
        {
            if(ent instanceof Player)
            {
                temp.remove();
                return noPlayersNear(spawnLoc.add(-(ent.getLocation().getX() - spawnLoc.getX()), 0, -(ent.getLocation().getZ() - spawnLoc.getZ())));
            }
        }
        temp.remove();
        return spawnLoc;
    }

}