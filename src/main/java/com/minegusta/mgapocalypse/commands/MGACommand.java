package com.minegusta.mgapocalypse.commands;

import com.minegusta.mgapocalypse.config.DefaultConfig;
import com.minegusta.mgapocalypse.traps.Trap;
import com.minegusta.mgapocalypse.util.WorldCheck;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MGACommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!(s instanceof Player) || !(s.isOp())) return true;

        Player p = (Player) s;
        if (args == null || args.length == 0) {
            sendHelp(p);
            return true;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("addspawn")) {
                if (!WorldCheck.is(p.getWorld())) {
                    p.sendMessage(ChatColor.RED + "This world is not valid!!");
                    return true;
                }
                p.sendMessage(ChatColor.GREEN + "You added this position as a spawn!");
                DefaultConfig.addSpawn(p.getLocation());
                return true;
            } else if (args[0].equalsIgnoreCase("traps"))
            {
                p.sendMessage(ChatColor.GREEN + "Place a sign. Then place a block on top.");
                p.sendMessage(ChatColor.GREEN + "On that signs, place a pressureplate.");
                p.sendMessage(ChatColor.GREEN + "Line 1: [Trap].");
                p.sendMessage(ChatColor.GREEN + "Line 2: Name of the trap.");
                p.sendMessage(ChatColor.GREEN + "Line 3: Amount of entities to spawn (if appliable).");
                p.sendMessage(ChatColor.GREEN + "Line 4: Entity type / potion type");
                p.sendMessage(ChatColor.GREEN + "--- Trap Names ---");
                for(Trap trap : Trap.values())
                {
                    p.sendMessage(ChatColor.GRAY + " - " + trap.name());
                }
                return true;

            } else if (args[0].equalsIgnoreCase("listspawns")) {
                int index = 1;
                for (String spawn : DefaultConfig.getSpawns()) {
                    p.sendMessage(ChatColor.YELLOW + Integer.toString(index) + ". " + ChatColor.GREEN + spawn);
                    index++;
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("addtown")) {
                if (!WorldCheck.is(p.getWorld())) {
                    p.sendMessage(ChatColor.RED + "This world is not valid!!");
                    return true;
                }
                p.sendMessage(ChatColor.GREEN + "You added this position as a town!");
                DefaultConfig.addTown(p.getLocation());
                return true;
            } else if (args[0].equalsIgnoreCase("listtowns")) {
                int index = 1;
                for (String town : DefaultConfig.getTowns()) {
                    p.sendMessage(ChatColor.YELLOW + Integer.toString(index) + ". " + ChatColor.GREEN + town);
                    index++;
                }
                return true;
            } else if (args[0].equalsIgnoreCase("setmainspawn")) {
                DefaultConfig.setMainSpawn(p.getLocation());
                p.sendMessage(ChatColor.GREEN + "Main spawn location set!");
                return true;
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("world")) {
                try {
                    World w = Bukkit.getWorld(args[1]);
                    p.teleport(w.getSpawnLocation());
                } catch (Exception ignored) {
                    p.sendMessage(ChatColor.RED + "That is not a valid world you dork.");
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("deletespawn")) {
                int index = -1;
                try {
                    index = Integer.parseInt(args[1]) - 1;
                } catch (Exception ignored) {
                }
                if (index < 0 || index > DefaultConfig.getSpawns().size()) {
                    p.sendMessage(ChatColor.RED + "That is not a valid index!");
                    return true;
                } else {
                    p.sendMessage(ChatColor.GREEN + "You removed a spawn from the list!");
                    DefaultConfig.removeSpawn(index);
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("deletetown")) {
                int index = -1;
                try {
                    index = Integer.parseInt(args[1]) - 1;
                } catch (Exception ignored) {
                }
                if (index < 0 || index > DefaultConfig.getTowns().size()) {
                    p.sendMessage(ChatColor.RED + "That is not a valid index!");
                    return true;
                } else {
                    p.sendMessage(ChatColor.GREEN + "You removed a town from the list!");
                    DefaultConfig.removeTown(index);
                }
                return true;
            }
        }
        sendHelp(p);
        return true;
    }

    private void sendHelp(Player p) {
        p.sendMessage(ChatColor.DARK_GREEN + "- - - Use the plugin like this: - - -");
        p.sendMessage(ChatColor.GREEN + " - /mga addspawn");
        p.sendMessage(ChatColor.GREEN + " - /mga listspawns");
        p.sendMessage(ChatColor.GREEN + " - /mga deletespawn <index>");
        p.sendMessage(ChatColor.GREEN + " - /mga addtown");
        p.sendMessage(ChatColor.GREEN + " - /mga listtowns");
        p.sendMessage(ChatColor.GREEN + " - /mga deletetown <index>");
        p.sendMessage(ChatColor.GREEN + " - /mga setmainspawn");
        p.sendMessage(ChatColor.GREEN + " - /mga world <world>");
        p.sendMessage(ChatColor.GREEN + " - /mga traps");
    }
}
