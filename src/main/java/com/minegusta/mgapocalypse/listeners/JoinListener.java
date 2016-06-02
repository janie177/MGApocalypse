package com.minegusta.mgapocalypse.listeners;

import com.minegusta.mgapocalypse.MGApocalypse;
import com.minegusta.mgapocalypse.config.DefaultConfig;
import com.minegusta.mgapocalypse.config.SavedLocationsManager;
import com.minegusta.mgapocalypse.files.MGPlayer;
import com.minegusta.mgapocalypse.util.SpawnKit;
import com.minegusta.mgapocalypse.util.SpawnLocationMenu;
import com.minegusta.mgapocalypse.util.WorldCheck;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class JoinListener implements Listener {
    //Listen for sign creation.
    @EventHandler
    public void onSignCreate(SignChangeEvent e) {
        Player p = e.getPlayer();

        if (e.getLine(1).equalsIgnoreCase("wasteland")) {
            if (!p.isOp()) {
                e.setLine(1, "Nope.");
                return;
            }

            String text = e.getLine(1);
            if (text.equalsIgnoreCase("wasteland")) {
                p.sendMessage(ChatColor.GREEN + "You made a wasteland entry point.");

                e.setLine(0, ChatColor.DARK_RED + "- - - -");
                e.setLine(1, ChatColor.AQUA + "Wasteland");
                e.setLine(2, ChatColor.AQUA + "" + ChatColor.BOLD + "[Join]");
                e.setLine(3, ChatColor.DARK_RED + "- - - -");
            }
        }
    }

    //Listen on interacting with the sign.
    @EventHandler(priority = EventPriority.LOWEST)
    public void onSignClick(PlayerInteractEvent e) {

        if(e.getHand() != EquipmentSlot.HAND) return;

        if (e.hasBlock() && e.getAction() == Action.RIGHT_CLICK_BLOCK && (e.getClickedBlock().getType() == Material.WALL_SIGN || e.getClickedBlock().getType() == Material.SIGN_POST)) {
            if (!(e.getClickedBlock().getState() instanceof Sign)) return;
            Sign sign = (Sign) e.getClickedBlock().getState();

            if (sign.getLine(1).equals(ChatColor.AQUA + "Wasteland")) {
                //Spawn the player
                if (SavedLocationsManager.getLocation(e.getPlayer().getUniqueId()) == null || !WorldCheck.is(SavedLocationsManager.getLocation(e.getPlayer().getUniqueId()).getWorld())) {


                    /**
                    MGPlayer mgp = MGApocalypse.getMGPlayer(e.getPlayer());

                    mgp.cleanPlayerKeepPerks();


                    //No longer pick a random spawn by default. Now open a fancy menu.

                    e.getPlayer().teleport(DefaultConfig.getRandomSpawn());

                    mgp.setPlaying(true);
                    e.getPlayer().setGameMode(GameMode.SURVIVAL);

                    new SpawnKit(e.getPlayer());

                    */

                    SpawnLocationMenu.open(e.getPlayer());

                } else {

                    MGPlayer mgp = MGApocalypse.getMGPlayer(e.getPlayer());

                    mgp.applyConfigStats();

                    e.getPlayer().teleport(SavedLocationsManager.getLocation(e.getPlayer().getUniqueId()));
                    SavedLocationsManager.resetLocation(e.getPlayer().getUniqueId());
                    mgp.setPlaying(true);
                    e.getPlayer().setGameMode(GameMode.SURVIVAL);

                    e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 1, false));
                    e.getPlayer().sendMessage(ChatColor.GRAY + "You wake up in an apocalyptic world...");
                }
            }
        }
    }
}
