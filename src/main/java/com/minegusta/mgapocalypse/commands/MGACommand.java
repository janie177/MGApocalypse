package com.minegusta.mgapocalypse.commands;

import com.minegusta.mgapocalypse.config.DefaultConfig;
import com.minegusta.mgapocalypse.util.WorldCheck;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MGACommand implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args)
    {
        if(!(s instanceof Player) || !(s.isOp()))return true;

        Player p = (Player) s;
        if(args == null || args.length == 0)
        {
            sendHelp(p);
            return true;
        }
        else if(args.length == 1)
        {
            if(args[0].equalsIgnoreCase("addspawn"))
            {
                if(!WorldCheck.is(p.getWorld()))
                {
                    p.sendMessage(ChatColor.RED + "This world is not valid!!");
                    return true;
                }
                p.sendMessage(ChatColor.GREEN + "You added this position as a spawn!");
                DefaultConfig.addSpawn(p.getLocation());
                return true;
            }
            else if(args[0].equalsIgnoreCase("listspawns"))
            {
                int index = 1;
                for(String spawn : DefaultConfig.getSpawns())
                {
                    p.sendMessage(ChatColor.YELLOW + Integer.toString(index) + ". " + ChatColor.GREEN + spawn);
                    index++;
                }
                return true;
            }
            if(args[0].equalsIgnoreCase("addtown"))
            {
                if(!WorldCheck.is(p.getWorld()))
                {
                    p.sendMessage(ChatColor.RED + "This world is not valid!!");
                    return true;
                }
                p.sendMessage(ChatColor.GREEN + "You added this position as a town!");
                DefaultConfig.addTown(p.getLocation());
                return true;
            }
            else if(args[0].equalsIgnoreCase("listtowns"))
            {
                int index = 1;
                for(String town : DefaultConfig.getTowns())
                {
                    p.sendMessage(ChatColor.YELLOW + Integer.toString(index) + ". " + ChatColor.GREEN + town);
                    index++;
                }
                return true;
            }
            else if(args[0].equalsIgnoreCase("setmainspawn"))
            {
                DefaultConfig.setMainSpawn(p.getLocation());
                p.sendMessage(ChatColor.GREEN + "Main spawn location set!");
                return true;
            }
        }
        else if(args.length == 2)
        {
            if(args[0].equalsIgnoreCase("deletespawn"))
            {
                int index = -1;
                try
                {
                    index = Integer.parseInt(args[1]) - 1;
                } catch (Exception ignored){}
                if(index < 0 || index > DefaultConfig.getSpawns().size())
                {
                    p.sendMessage(ChatColor.RED + "That is not a valid index!");
                    return true;
                }
                else
                {
                    p.sendMessage(ChatColor.GREEN + "You removed a spawn from the list!");
                    DefaultConfig.removeSpawn(index);
                }
                return true;
            }
            if(args[0].equalsIgnoreCase("deletetown"))
            {
                int index = -1;
                try
                {
                    index = Integer.parseInt(args[1]) - 1;
                } catch (Exception ignored){}
                if(index < 0 || index > DefaultConfig.getTowns().size())
                {
                    p.sendMessage(ChatColor.RED + "That is not a valid index!");
                    return true;
                }
                else
                {
                    p.sendMessage(ChatColor.GREEN + "You removed a town from the list!");
                    DefaultConfig.removeTown(index);
                }
                return true;
            }
        }
        sendHelp(p);
        return true;
    }

    private void sendHelp(Player p)
    {
        p.sendMessage(ChatColor.DARK_GREEN + "- - - Use the plugin like this: - - -");
        p.sendMessage(ChatColor.GREEN + " - /mga addspawn");
        p.sendMessage(ChatColor.GREEN + " - /mga listspawns");
        p.sendMessage(ChatColor.GREEN + " - /mga deletespawn <index>");
        p.sendMessage(ChatColor.GREEN + " - /mga addtown");
        p.sendMessage(ChatColor.GREEN + " - /mga listtowns");
        p.sendMessage(ChatColor.GREEN + " - /mga deletetown <index>");
        p.sendMessage(ChatColor.GREEN + " - /mga setmainspawn");
    }
}
