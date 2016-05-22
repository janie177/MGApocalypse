package com.minegusta.mgapocalypse.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

public class WorldListener implements Listener {

	@EventHandler
	public void onChunkLoad(ChunkLoadEvent e)
	{
		for(Entity ent : e.getChunk().getEntities())
		{
			if(ent instanceof Horse && ((Horse)ent).getVariant() == Horse.Variant.UNDEAD_HORSE && ent.getTicksLived() >  20 * 20)
			{
				ent.remove();
			}
		}
	}
}
