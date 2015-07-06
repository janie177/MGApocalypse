package com.minegusta.mgapocalypse.commands;


import com.minegusta.mgapocalypse.MGApocalypse;
import com.minegusta.mgapocalypse.files.MGPlayer;
import com.minegusta.mgapocalypse.util.Break;
import com.minegusta.mgapocalypse.util.WorldCheck;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BreakCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!(s instanceof Player)) return true;
        Player p = (Player) s;

        if (!WorldCheck.is(p.getWorld())) return true;

        MGPlayer mgp = MGApocalypse.getMGPlayer(p);
        String uuid = p.getUniqueId().toString();

        if (!MGPlayer.breaks.containsKey(uuid)) {
            Break b = new Break(p);
            b.start();
            MGPlayer.breaks.put(uuid, b);
        } else {
            MGPlayer.breaks.get(uuid).cancel();
            MGPlayer.breaks.remove(uuid);
        }
        return true;
    }
}
