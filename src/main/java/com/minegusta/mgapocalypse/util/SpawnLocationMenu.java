package com.minegusta.mgapocalypse.util;

import com.google.common.collect.Lists;
import com.minegusta.mgapocalypse.MGApocalypse;
import com.minegusta.mgapocalypse.config.DefaultConfig;
import com.minegusta.mgapocalypse.files.MGPlayer;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpawnLocationMenu {

	private static Inventory inv;

	public static void init()
	{
		inv = Bukkit.createInventory(new SpawnLocationInventoryHolder(), 9 * 6, ChatColor.RED + "Pick a spawn!");

		int slot = 1;

		ItemStack random = new ItemStack(Material.DIAMOND, 1)
		{
			{
				ItemMeta meta = getItemMeta();
				meta.setDisplayName(ChatColor.DARK_AQUA + "Random!");
				meta.setLore(Lists.newArrayList(ChatColor.AQUA + "Click me for a random spawn spot."));
				setItemMeta(meta);
			}
		};

		inv.setItem(0, random);


		for(String s : DefaultConfig.getSpawns())
		{
			if(slot > 54) break;
			final int i = slot;

			ItemStack spawn = new ItemStack(Material.BED, 1)
			{
				{
					Location spawn = StringLocConverter.stringToLocation(s);
					ItemMeta meta = getItemMeta();
					meta.setDisplayName(ChatColor.DARK_GREEN + "Spawn #" + Integer.toString(i));
					meta.setLore(Lists.newArrayList(ChatColor.GREEN + "X: " + spawn.getX(), ChatColor.GREEN + "Z: " + spawn.getZ()));
					setItemMeta(meta);
				}
			};

			inv.setItem(slot, spawn);

			slot++;
		}
	}


	public static void open(Player p)
	{
		p.openInventory(inv);
	}

	public static void process(Player p, int slot)
	{

		//Random location when the first slot is chosen.
		if(slot == 0)
		{
			p.teleport(DefaultConfig.getRandomSpawn());
		}
		//Teleport the player to the chosen location, unless it is invalid.
		else
		{
			int index = slot - 1;
			if(index >= DefaultConfig.getSpawns().size())
			{
				p.teleport(DefaultConfig.getRandomSpawn());
			}
			else
			{
				Location spawn = StringLocConverter.stringToLocation(DefaultConfig.getSpawns().get(index));
				p.teleport(spawn);
			}

		}


		//The default spawning stuff.
		MGPlayer mgp = MGApocalypse.getMGPlayer(p);
		mgp.cleanPlayerKeepPerks();
		mgp.setPlaying(true);
		p.setGameMode(GameMode.SURVIVAL);
		new SpawnKit(p);
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 1, false));
		p.sendMessage(ChatColor.GRAY + "You wake up in an apocalyptic world...");
	}
}
