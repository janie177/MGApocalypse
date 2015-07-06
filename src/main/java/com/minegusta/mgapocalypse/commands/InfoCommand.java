package com.minegusta.mgapocalypse.commands;

import com.minegusta.mgapocalypse.MGApocalypse;
import com.minegusta.mgapocalypse.files.FileManager;
import com.minegusta.mgapocalypse.files.MGPlayer;
import com.minegusta.mgapocalypse.util.InfoMenu;
import com.minegusta.mgapocalypse.util.WorldCheck;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
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

        if(args.length > 0)
        {
            try {
                OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
                MGPlayer mgp = MGPlayer.build(p.getUniqueId().toString(), FileManager.getFile(p.getUniqueId().toString()));
                ((Player) s).openInventory(InfoMenu.build(mgp));
            } catch (Exception ignored){
                s.sendMessage(ChatColor.RED + "That player could not be found.");
                return true;
            }
        }

        ((Player) s).openInventory(InfoMenu.build(MGApocalypse.getMGPlayer((Player) s)));

        return true;
    }
}
