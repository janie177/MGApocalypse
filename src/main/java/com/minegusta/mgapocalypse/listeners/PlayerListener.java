package com.minegusta.mgapocalypse.listeners;

import com.google.common.collect.Lists;
import com.minegusta.mgapocalypse.buttons.ButtonManager;
import com.minegusta.mgapocalypse.dotmanagers.BleedingManager;
import com.minegusta.mgapocalypse.dotmanagers.DiseaseManager;
import com.minegusta.mgapocalypse.items.LootItem;
import com.minegusta.mgapocalypse.lootblocks.Loot;
import com.minegusta.mgapocalypse.util.*;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlayerListener implements Listener
{

    //All the blocks that are disallowed to right click.
    private static final List<Material> blockedBlocks = Lists.newArrayList(Material.ENCHANTMENT_TABLE, Material.ANVIL, Material.BED, Material.MINECART, Material.STORAGE_MINECART);

    //Chances for bleeding. Also for diseases.
    private final static int bleedChance = 12; //In %
    private final static int diseaseChance = 3; //In %

    //All the allowed commands, lower case only.
    private final static List<String> allowedCMDS = Lists.newArrayList("/credits", "/pop", "/break", "/hub", "/pause", "/logout", "/log-out", "/leave", "/abort", "/exit", "/msg", "/r", "/pm", "/message", "/me");

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

        if(m == Material.POTION)
        {
            if(e.getItem().getDurability() == 0) {
                p.setLevel(20);
                p.sendMessage(ChatColor.GREEN + "You feel refreshed.");
                e.setCancelled(true);
                ItemUtil.removeOne(p, Material.POTION);
                p.getInventory().addItem(LootItem.EMPTYBOTTLE.build());
                p.updateInventory();
            }
            else
            {
                new RemoveItemAfterSecond(p, p.getInventory().getHeldItemSlot());
            }
        }


        //Cure diseases
        if(m == Material.MILK_BUCKET)
        {
            e.setCancelled(true);
            ItemUtil.removeOne(p, Material.MILK_BUCKET);
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
                ItemUtil.removeOne(healer, Material.PAPER);
                BleedingManager.bandage(p, true);
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
                ItemUtil.removeOne(p, Material.PAPER);
                BleedingManager.bandage(p, true);
            }
        }

        //Check for buttons
        if(e.hasBlock() && e.getClickedBlock().getType().equals(Material.STONE_BUTTON))
        {
            ButtonManager.despawnButon(e.getClickedBlock().getLocation());
        }

        //Block buckets
        if(hand == Material.BUCKET || hand == Material.WATER_BUCKET)
        {
            e.setCancelled(true);
        }

        //Smoke grenades
        if(e.hasBlock() && hand == Material.SLIME_BALL)
        {
            new SmokeGrenade(e.getClickedBlock().getLocation());
            ItemUtil.removeOne(p, Material.SLIME_BALL);
        }

        if(e.hasBlock() && e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getRelative(e.getBlockFace()).getType() == Material.STATIONARY_WATER && hand == Material.GLASS_BOTTLE)
        {
            e.setCancelled(true);
            ItemUtil.removeOne(p, Material.GLASS_BOTTLE);
            p.getInventory().addItem(LootItem.WATERBOTTLE.build());
            p.updateInventory();
        }

        //Block interacting with certain blocks
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK)
        {
            if(blockedBlocks.contains(e.getClickedBlock().getType()))
            {
                e.setCancelled(true);
            }
        }

        //No bonemeal
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

        if(type == Material.CROPS)e.setCancelled(true);
        if(type == Material.STONE_BUTTON)
        {
            e.setCancelled(true);
            e.getBlock().setType(Material.AIR);
        }

        if(type == Material.CROPS && hand == Material.WOOD_HOE)
        {
            l.getWorld().dropItemNaturally(l, new ItemStack(Material.WHEAT, 1));
            tool.setDurability((short) (tool.getDurability() + 5));
            if(tool.getDurability() >= hand.getMaxDurability())new RemoveItemAfterSecond(p, p.getInventory().getHeldItemSlot());
        }

        if(type == Material.MELON_BLOCK && hand == Material.WOOD_HOE)
        {
            l.getWorld().dropItemNaturally(l, new ItemStack(Material.MELON, 1));
            tool.setDurability((short) (tool.getDurability() + 15));
            if(tool.getDurability() >= hand.getMaxDurability())new RemoveItemAfterSecond(p, p.getInventory().getHeldItemSlot());
        }

        if(type == Material.SOUL_SAND && hand == Material.WOOD_SPADE)
        {
            l.getWorld().dropItemNaturally(l, Loot.getGrave().build());
            if(RandomNumber.get(3) == 1)l.getWorld().spawnEntity(l, EntityType.SKELETON);
            tool.setDurability((short) (tool.getDurability() + 15));
            if(tool.getDurability() >= hand.getMaxDurability())new RemoveItemAfterSecond(p, p.getInventory().getHeldItemSlot());
        }

        if((type == Material.IRON_ORE || type == Material.DIAMOND_ORE || type == Material.COAL_ORE)&& hand == Material.WOOD_PICKAXE)
        {
            l.getWorld().dropItemNaturally(l, Loot.getOre().build());
            tool.setDurability((short) (tool.getDurability() + 20));
            if(tool.getDurability() >= hand.getMaxDurability())new RemoveItemAfterSecond(p, p.getInventory().getHeldItemSlot());
        }

        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEvent(EntityDamageByEntityEvent e)
    {
        if(!WorldCheck.is(e.getEntity().getWorld()))return;

        if(e.getEntity() instanceof Player)
        {
            Player p = (Player) e.getEntity();

            //Healing check
            if(e.getDamager() instanceof Player)
            {
                Player damager = (Player) e.getDamager();
                ItemStack hand = damager.getItemInHand();
                if(hand.getType() == Material.PAPER)
                {
                    TempData.healMap.put(p.getUniqueId().toString(), System.currentTimeMillis());
                    BleedingManager.bandage(p, false);
                    p.sendMessage(ChatColor.GRAY + damager.getName() + " began bandaging you...");
                    damager.sendMessage(ChatColor.GRAY + "You begin bandaging " + p.getName() + ".");
                    e.setCancelled(true);
                }

                else if(hand.getType() == Material.SHEARS)
                {
                    if(TempData.healMap.containsKey(p.getUniqueId().toString()) && TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - TempData.healMap.get(p.getUniqueId().toString())) < 15)
                    {
                        long wait = 0;
                        if(TempData.healCoolDownMap.containsKey(p.getUniqueId().toString())) wait = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - TempData.healCoolDownMap.get(p.getUniqueId().toString()));
                        if(TempData.healCoolDownMap.containsKey(p.getUniqueId().toString()) && wait < 180)
                        {
                            damager.sendMessage(ChatColor.RED + "This player cannot be healed for another " + Long.toString(180 - wait) + " Seconds.");
                        }
                        else
                        {
                            TempData.healCoolDownMap.put(p.getUniqueId().toString(), System.currentTimeMillis());
                            healPlayer(p, damager);
                        }
                    }
                    else
                    {
                        damager.sendMessage(ChatColor.RED + "Hit players with bandage first before healing them!");
                    }
                    e.setCancelled(true);
                }
            }

            //Disease checking
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
        else if(e.getEntity() instanceof Zombie)
        {
            if(e.getDamager() instanceof Player)
            {
                Zombie zombie = (Zombie) e.getEntity();
                Player p = (Player) e.getDamager();
                zombie.setVelocity(zombie.getLocation().toVector().subtract(p.getLocation().toVector()).normalize().multiply(1.1));
                zombie.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 1));
            }
            else if(e.getDamager() instanceof Arrow)
            {
                if(((Arrow) e.getDamager()).getShooter() != null && ((Arrow) e.getDamager()).getShooter() instanceof Player)
                {
                    Zombie zombie = (Zombie) e.getEntity();
                    Player p = (Player)((Arrow) e.getDamager()).getShooter();
                    zombie.setVelocity(zombie.getLocation().toVector().subtract(p.getLocation().toVector()).normalize().multiply(1.1));
                    zombie.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 1));
                }
            }
        }
    }

    private void healPlayer(Player p, Player healer)
    {
        p.sendMessage(ChatColor.LIGHT_PURPLE + "You were healed by " + healer.getName() + ".");
        healer.sendMessage(ChatColor.LIGHT_PURPLE + "You healed " + p.getName() + ".");
        p.getWorld().spigot().playEffect(p.getLocation(), Effect.HEART);
        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 7, 0));
    }

    //Spawn a zombie on death
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEvent(PlayerDeathEvent e)
    {
        if(!WorldCheck.is(e.getEntity().getWorld()))return;

        //Do stuff here

        e.setDroppedExp(0);
        final Player p = e.getEntity();

        DiseaseManager.cure(p);
        BleedingManager.bandage(p, false);

        Zombie z = (Zombie) p.getWorld().spawnEntity(p.getLocation(), EntityType.ZOMBIE);
        z.setBaby(false);
        z.setVillager(false);
        z.setCustomNameVisible(true);
        z.setCustomName(p.getName());
        z.setCanPickupItems(false);

        z.getEquipment().setHelmet(new ItemStack(Material.SKULL_ITEM, 1, (short) 3) {
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
        new RespawnLater(e.getPlayer());
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

    //Sprinting lures zombies.
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEvent(PlayerToggleSprintEvent e)
    {
        if(!WorldCheck.is(e.getPlayer().getWorld()))return;

        for(Entity ent : e.getPlayer().getNearbyEntities(30, 12, 30))
        {
            if(ent instanceof Zombie)
            {
                ((Creature)ent).setTarget(e.getPlayer());
            }
        }
    }

    //Stop health regen.
    @EventHandler(priority = EventPriority.LOWEST)
    public void onHeal(EntityRegainHealthEvent e)
    {
        if(!WorldCheck.is(e.getEntity().getWorld()))return;

        if(e.getEntity() instanceof Player)
        {
            if(e.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED || e.getRegainReason() == EntityRegainHealthEvent.RegainReason.REGEN)
            {
                e.setCancelled(true);
            }
        }
    }
}
