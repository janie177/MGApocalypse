package com.minegusta.mgapocalypse.commands;

import com.minegusta.mgapocalypse.util.InfoMenu;
import com.minegusta.mgapocalypse.util.WorldCheck;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InfoCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args)
    {
        if(!(s instanceof Player))return true;

        if(!WorldCheck.is(((Player)s).getWorld())) return true;

        ((Player) s).openInventory(InfoMenu.build((Player) s));

        return true;
    }
}
