package com.minegusta.mgapocalypse.commands;

import com.minegusta.mgapocalypse.util.PerkMenu;
import com.minegusta.mgapocalypse.util.WorldCheck;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PerkCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args)
    {
        if(!(s instanceof Player))return true;

        Player p = (Player) s;

        if(!WorldCheck.is(p.getWorld()))return true;

        p.openInventory(PerkMenu.build(p));

        return true;
    }

}
