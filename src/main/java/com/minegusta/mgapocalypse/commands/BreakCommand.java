package com.minegusta.mgapocalypse.commands;


import com.minegusta.mgapocalypse.util.Break;
import com.minegusta.mgapocalypse.util.TempData;
import com.minegusta.mgapocalypse.util.WorldCheck;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class BreakCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args)
    {
        if(!(s instanceof Player)) return true;
        Player p = (Player) s;

        if(!WorldCheck.is(p.getWorld()))return true;

        Break b = new Break(p);
        b.start();
        TempData.breakMap.put(p.getUniqueId().toString(), b);
        return true;
    }
}
