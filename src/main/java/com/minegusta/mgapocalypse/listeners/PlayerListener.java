package com.minegusta.mgapocalypse.listeners;

import com.google.common.collect.Lists;
import com.minegusta.mgapocalypse.buttons.ButtonManager;
import com.minegusta.mgapocalypse.config.DefaultConfig;
import com.minegusta.mgapocalypse.dotmanagers.BleedingManager;
import com.minegusta.mgapocalypse.dotmanagers.DiseaseManager;
import com.minegusta.mgapocalypse.lootblocks.Loot;
import com.minegusta.mgapocalypse.util.RandomNumber;
import com.minegusta.mgapocalypse.util.WorldCheck;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class PlayerListener implements Listener
{

    //All the blocks that are disallowed to right click.
    private static final List<Material> blockedBlocks = Lists.newArrayList(Material.ENCHANTMENT_TABLE, Material.ANVIL, Material.BED, Material.MINECART, Material.STORAGE_MINECART);

    //Chances for bleeding. Also for diseases.
    private final static int bleedChance = 5; //In %
    private final static int diseaseChance = 3; //In %

    //All the allowed commands, lower case only.
    private final static List<String> allowedCMDS = Lists.newArrayList("pop", "break");

    //All food types that heal you
    private final static List<Material> food = Lists.newArrayList(Material.MELON, Material.RAW_FISH, Material.RAW_CHICKEN, Material.RAW_BEEF, Material.BREAD, Material.COOKIE, Material.POTATO_ITEM, Material.CARROT_ITEM, Material.APPLE, Material.MUSHROOM_SOUP, Material.PORK, Material.GRILLED_PORK, Material.COOKED_FISH, Material.BAKED_POTATO, Material.COOKED_CHICKEN);

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEvent(PlayerItemConsumeEvent e)
    {
        if(!WorldCheck.is(e.getPlayer().getWorld()))return;

        Player p = e.getPlayer();
        Material m = e.getItem().getType();

        if(food.contains(m))
        {
            if(p.getMaxHealth() != p.getHealth())p.setHealth(p.getHealth() + 1.0);
        }

        //Cure diseases
        if(m == Material.MILK_BUCKET)
        {
            p.setItemInHand(new ItemStack(Material.AIR));
            p.updateInventory();
            DiseaseManager.cure(p);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEvent(PlayerInteractEntityEvent e)
    {
        if(!WorldCheck.is(e.getPlayer().getWorld()))return;

        Material hand = e.getPlayer().getItemInHand().getType();

        //Check for bandaging
        if(e.getRightClicked() instanceof Player)
        {
            Player p = (Player) e.getRightClicked();
            Player healer = e.getPlayer();

            if(hand.equals(Material.PAPER))
            {
                healer.sendMessage(ChatColor.GREEN + "You bandaged " + p.getName() + ".");
                p.sendMessage(ChatColor.GREEN + healer.getName() + " bandaged your wounds.");
                healer.getInventory().remove(new ItemStack(Material.PAPER, 1));
                BleedingManager.bandage(p);
                healer.updateInventory();
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEvent(PlayerInteractEvent e)
    {
        if(!WorldCheck.is(e.getPlayer().getWorld()))return;

        Player p = e.getPlayer();
        Material hand = e.getPlayer().getItemInHand().getType();

        //Check for bandaging
        if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction() == Action.RIGHT_CLICK_BLOCK)
        {
            if(hand.equals(Material.PAPER))
            {
                p.sendMessage(ChatColor.GREEN + "You bandaged your wounds.");
                p.getInventory().remove(new ItemStack(Material.PAPER, 1));
                BleedingManager.bandage(p);
                p.updateInventory();
            }
        }

        //Check for buttons
        if(e.hasBlock() && e.getClickedBlock().getType().equals(Material.STONE_BUTTON))
        {
            ButtonManager.despawnButon(e.getClickedBlock().getLocation());
        }



        //Block interacting with certain blocks
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK)
        {
            if(blockedBlocks.contains(e.getClickedBlock().getType()))
            {
                e.setCancelled(true);
            }
        }

        if(e.hasBlock() && e.getClickedBlock().getType() == Material.GRASS && hand == Material.INK_SACK)
        {
            e.setCancelled(true);
        }

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEvent(BlockPlaceEvent e)
    {
        if(!WorldCheck.is(e.getPlayer().getWorld()))return;
        Player p = e.getPlayer();
        if(p.isOp() || p.hasPermission("minegusta.builder"))return;

        Material material = e.getBlock().getType();
        Material hand = p.getItemInHand().getType();
        Location l = e.getBlock().getLocation();

        if(material.equals(Material.STONE_BUTTON))
        {
            ButtonManager.despawnButon(l);
        }
        else
        {
            e.setCancelled(true);
        }
    }



    @EventHandler(priority = EventPriority.LOWEST)
    public void onEvent(BlockBreakEvent e)
    {
        if(!WorldCheck.is(e.getPlayer().getWorld()))return;
        Player p = e.getPlayer();
        if(p.isOp() || p.hasPermission("minegusta.builder"))return;

        //Check if you can break these blocks:
        Material type = e.getBlock().getType();
        Location l = e.getBlock().getLocation();
        ItemStack tool = p.getItemInHand();
        Material hand = tool.getType();

        if(type == Material.STONE_BUTTON)
        {
            e.getBlock().setType(Material.AIR);
        }

        if(type == Material.CROPS && hand == Material.WOOD_HOE)
        {
            l.getWorld().dropItemNaturally(l, new ItemStack(Material.WHEAT, 1));
            tool.setDurability((short) (tool.getDurability() + 5));
        }

        if(type == Material.MELON_BLOCK && hand == Material.WOOD_HOE)
        {
            l.getWorld().dropItemNaturally(l, new ItemStack(Material.MELON, 2));
            tool.setDurability((short) (tool.getDurability() + 15));
        }

        if(type == Material.SOUL_SAND && hand == Material.WOOD_SPADE)
        {
            l.getWorld().dropItemNaturally(l, Loot.getGrave().build());
            tool.setDurability((short) (tool.getDurability() + 15));
        }

        if((type == Material.IRON_ORE || type == Material.DIAMOND_ORE || type == Material.COAL_ORE)&& hand == Material.WOOD_PICKAXE)
        {
            l.getWorld().dropItemNaturally(l, Loot.getOre().build());
            tool.setDurability((short) (tool.getDurability() + 20));
        }

        e.setCancelled(true);


    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEvent(EntityDamageByEntityEvent e)
    {
        if(!WorldCheck.is(e.getEntity().getWorld()))return;

        //Bleeding and diseases.
        if(e.getEntity() instanceof Player)
        {
            Player p = (Player) e.getEntity();
            if(!e.isCancelled())
            {
                if(e.getDamager() != null && e.getDamager() instanceof Zombie)
                {
                    if(RandomNumber.get(100) <= diseaseChance)
                    {
                        DiseaseManager.infect(p);
                    }
                }
                if(RandomNumber.get(100) <= bleedChance)
                {
                    BleedingManager.bleed(p);
                }
            }
        }
    }

    //Spawn a zombie on death
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEvent(PlayerDeathEvent e)
    {
        if(!WorldCheck.is(e.getEntity().getWorld()))return;

        //Do stuff here
        e.setDroppedExp(0);
        final Player p = e.getEntity();

        Zombie z = (Zombie) p.getWorld().spawnEntity(p.getLocation(), EntityType.ZOMBIE);
        z.getEquipment().setHelmet(new ItemStack(Material.SKULL, 1) {
            {
                SkullMeta meta = (SkullMeta) getItemMeta();
                meta.setOwner(p.getName());
                setItemMeta(meta);
            }
        });
    }

    //Respawn in the right spot
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEvent(PlayerRespawnEvent e)
    {
        if(!WorldCheck.is(e.getPlayer().getWorld()))return;

        //Set the respawn position to be the main spawn.
        e.setRespawnLocation(DefaultConfig.getMainSpawn());
    }

    //Block all commands
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEvent(PlayerCommandPreprocessEvent e)
    {
        if(!WorldCheck.is(e.getPlayer().getWorld()))return;
        if(e.getPlayer().isOp() || e.getPlayer().hasPermission("minegusta.builder"))return;

        //For non ops, block all commands except the allowed ones:
        String[] command = e.getMessage().toLowerCase().split(" ");
        if(!allowedCMDS.contains(command[0].toLowerCase()))
        {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "Commands are blocked here!");
            e.getPlayer().sendMessage(ChatColor.RED + "To get back to the hub, use:");
            e.getPlayer().sendMessage(ChatColor.GRAY + "/break");
        }
    }
}
