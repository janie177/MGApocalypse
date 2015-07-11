package com.minegusta.mgapocalypse.traps.instances;

import com.minegusta.mgapocalypse.Main;
import com.minegusta.mgapocalypse.traps.ITrap;
import org.bukkit.*;
import org.bukkit.block.Sign;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;

public class WhirlWindTrap implements ITrap {
    @Override
    public boolean apply(Player p, Sign s) {

        int duration = 6;

        try {
            if (s.getLine(2).length() != 0) duration = Integer.parseInt(s.getLine(2));
        } catch (Exception ignored) {
        }

        createWind(p.getLocation(), duration);

        return true;
    }

    private void createWind(Location l, int duration) {
        final Location center = l;
        for (int i = 0; i <= 20 * duration; i++) {
            if (i % 2 == 0) {
                final int k = i;

                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> {
                    if (k % 20 == 0) {
                        center.getWorld().playSound(center, Sound.ENDERDRAGON_WINGS, 10, 1);
                    }
                    center.getWorld().spigot().playEffect(center, Effect.PARTICLE_SMOKE, 0, 0, 1, 5, 1, 1 / 30, 50, 30);


                    //The sucking people in effect
                    center.getWorld().getEntitiesByClasses(LivingEntity.class, Item.class, Projectile.class).stream().
                            filter(ent -> ent.getLocation().distance(center) <= 5).forEach(ent ->
                    {
                        double angle = Math.toRadians(14);
                        double radius = Math.abs(ent.getLocation().distance(center));

                        if(radius < 1)
                        {
                            ent.setVelocity(new Vector(1,1,1));
                        }

                        double x = ent.getLocation().getX() - center.getX();
                        double z = ent.getLocation().getZ() - center.getZ();

                        double dx = x * Math.cos(angle) - z * Math.sin(angle);
                        double dz = x * Math.sin(angle) + z * Math.cos(angle);

                        Location target1 = new Location(ent.getWorld(), dx + center.getX(), ent.getLocation().getY(), dz + center.getZ());

                        double ix = ent.getLocation().getX() - target1.getX();
                        double iz = ent.getLocation().getZ() - target1.getZ();

                        Vector v = new Vector(ix, -0.2, iz);
                        v.normalize();

                        double amplifier = 1.8;
                        ent.setVelocity(ent.getVelocity().add(v.multiply(amplifier)));
                    });
                }, i);
            }
        }
    }

    @Override
    public String getMessage() {
        return ChatColor.DARK_RED + "A whirlwind appears out of the floor!";
    }

    @Override
    public int getCooldownTime() {
        return 30;
    }
}
