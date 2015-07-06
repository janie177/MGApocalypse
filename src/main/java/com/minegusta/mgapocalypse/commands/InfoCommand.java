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

public class InfoCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        if (!(s instanceof Player)) return true;

        Player player = (Player) s;

        if (!WorldCheck.is(player.getWorld())) return true;

        if (args.length > 0) {
            try {
                OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);

                if (!FileManager.fileExists(p.getUniqueId().toString())) {
                    s.sendMessage(ChatColor.RED + "That player could not be found.");
                    return true;
                }

                MGPlayer mgp = MGPlayer.build(p.getUniqueId().toString(), FileManager.getFile(p.getUniqueId().toString()));
                player.openInventory(InfoMenu.build(mgp, player));
                s.sendMessage(ChatColor.YELLOW + "You open " + p.getName() + "'s info menu!");
                return true;
            } catch (Exception ignored) {
                s.sendMessage(ChatColor.RED + "That player could not be found.");
                return true;
            }
        }

        player.openInventory(InfoMenu.build(MGApocalypse.getMGPlayer(player), player));
        s.sendMessage(ChatColor.YELLOW + "You open your info menu!");

        return true;
    }
}
