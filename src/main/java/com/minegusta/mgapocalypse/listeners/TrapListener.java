package com.minegusta.mgapocalypse.listeners;

import com.minegusta.mgapocalypse.traps.TrapManager;
import com.minegusta.mgapocalypse.util.WorldCheck;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class TrapListener implements Listener{

    @EventHandler
    public void onPressurePlate(PlayerInteractEvent e)
    {
        if(!WorldCheck.is(e.getPlayer().getWorld()))return;

        Player p = e.getPlayer();

        if(e.getAction() == Action.PHYSICAL && e.hasBlock() && (e.getClickedBlock().getType() == Material.STONE_PLATE || e.getClickedBlock().getType() == Material.WOOD_PLATE))
        {
            Block b = e.getClickedBlock().getRelative(BlockFace.DOWN, 2);

            if(b.getType() == Material.WALL_SIGN || b.getType() == Material.SIGN_POST)
            {
                Sign sign = (Sign) b.getState();
                if(sign.getLine(0).equalsIgnoreCase("[Trap]"))
                {
                    TrapManager.activate(sign, p);
                }
            }
        }
    }
}
