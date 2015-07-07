package com.minegusta.mgapocalypse.traps.instances;

import com.minegusta.mgapocalypse.traps.ITrap;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public class ZombieSpawn implements ITrap
{
    @Override
    public void apply(Player p, Sign s)
    {

    }

    @Override
    public String getMessage() {
        return ChatColor.DARK_GREEN + "Zombies have spawned nearby!";
    }

    @Override
    public int getCooldownTime() {
        return 60;
    }
}
