package com.minegusta.mgapocalypse.util;

import org.bukkit.Material;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class ZombieUtil {

	public static void setZombieToVanilla(Zombie z)
	{
		EntityEquipment e = z.getEquipment();
		if(z.isVillager())
		{
			z.setVillager(false);
			z.setVillagerProfession(null);
		}
		z.setCanPickupItems(false);
		e.setItemInMainHand(new ItemStack(Material.AIR));
		e.setItemInOffHand(new ItemStack(Material.AIR));
		e.setArmorContents(new ItemStack[]{null, null, null, null, null, null});
	}

}
