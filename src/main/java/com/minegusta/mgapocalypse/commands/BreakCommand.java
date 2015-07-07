package com.minegusta.mgapocalypse.commands;


import com.minegusta.mgapocalypse.MGApocalypse;
import com.minegusta.mgapocalypse.files.MGPlayer;
import com.minegusta.mgapocalypse.util.Break;
import com.minegusta.mgapocalypse.util.WorldCheck;
import org.bukkit.ChatColor;
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

        if(!mgp.getIfPlaying())
        {
            p.sendMessage(ChatColor.YELLOW + "You are already on a break!");
            p.sendMessage(ChatColor.YELLOW + "Please use a Wasteland join sign to play.");
            return true;
        }

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
