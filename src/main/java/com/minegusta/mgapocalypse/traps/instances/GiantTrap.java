package com.minegusta.mgapocalypse.traps.instances;

import com.minegusta.mgapocalypse.traps.ITrap;
import com.minegusta.mgapocalypse.util.RandomNumber;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Player;

public class GiantTrap implements ITrap {
    @Override
    public void apply(Player p, Sign s) {
        Location location = new Location(p.getWorld(), p.getLocation().getX() + RandomNumber.get(30) - 15, p.getLocation().getY() + 1, p.getLocation().getZ() + RandomNumber.get(30) - 15);

        Giant g = (Giant) location.getWorld().spawnEntity(location, EntityType.GIANT);
        g.setCustomName(ChatColor.DARK_GREEN + "Giant");
        g.setCustomNameVisible(true);
    }

    @Override
    public String getMessage() {
        return ChatColor.DARK_GREEN + "A giant has been spawned nearby!";
    }

    @Override
    public int getCooldownTime() {
        return 300;
    }
}
