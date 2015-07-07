package com.minegusta.mgapocalypse.commands;

import com.minegusta.mgapocalypse.Tasks.PerkMenuTask;
import com.minegusta.mgapocalypse.util.PerkMenu;
import com.minegusta.mgapocalypse.util.WorldCheck;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PerkCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        if (!(s instanceof Player)) return true;

        Player p = (Player) s;

        if (!WorldCheck.is(p.getWorld())) return true;

        Inventory inv = PerkMenu.build(p);

        p.openInventory(inv);

        PerkMenuTask.invs.put(inv, true);

        return true;
    }

}
