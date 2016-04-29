package com.minegusta.mgapocalypse.util;

import org.bukkit.Material;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
		if(z.isBaby())
		{
			z.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 12000, 1, false, false));
		}
		else z.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 12000, 0, false, false));
	}

}
