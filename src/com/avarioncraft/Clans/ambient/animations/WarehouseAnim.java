package com.avarioncraft.Clans.ambient.animations;

import java.util.Map;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.util.enums.ClanMenuStyle;
import com.google.common.collect.Maps;

public class WarehouseAnim {
	
	public WarehouseAnim() {
		colors.put(ClanMenuStyle.BLACK, Color.BLACK);
		colors.put(ClanMenuStyle.BLUE, Color.BLUE);
		colors.put(ClanMenuStyle.GRAY, Color.GRAY);
		colors.put(ClanMenuStyle.LIGHT_BLUE, Color.NAVY);
		colors.put(ClanMenuStyle.LIME, Color.LIME);
		colors.put(ClanMenuStyle.MAGENTA, Color.PURPLE);
		colors.put(ClanMenuStyle.ORANGE, Color.ORANGE);
		colors.put(ClanMenuStyle.RED, Color.MAROON);
		colors.put(ClanMenuStyle.YELLOW, Color.YELLOW);
	}
	
	private Map<ClanMenuStyle, Color> colors = Maps.newHashMap();
	
	public void playBreakAnim(Location loc) {
		
		loc.getWorld().playSound(loc, Sound.BLOCK_ANVIL_PLACE, 0.4F, 0.4F);
		
		for(int n = 0; n < 3; n++) {
			final int y = n;
			Animation.drawCircle(loc, 2.0, 12).forEach((vec)->{
				Location play = loc.clone().add(vec).add(0.5, y, 0.5);
				loc.getWorld().spawnParticle(Particle.CRIT, play.getX(), play.getY(), play.getZ(), 1, 0.0, 0.0, 0.0, 0);
			});
		}

		
	}
	
	public void playIdleAnim(Location loc, Clan clan, boolean suppress) {
		
		if(suppress) return;
		
		loc.add(0, 1.0F, 0);
		
		for(float h = 0.0F; h < 1.5F; h+=0.1F) {
			
			Particle particle = Particle.REDSTONE;
			
			particle.builder().particle(Particle.REDSTONE)
			.color(this.colors.get(clan.getMenuStyle())).location(loc.add(0, h, 0))
			.particle(Particle.REDSTONE)
			.count(3)
			.offset(0.1, 0.0, 0.1)
			.receivers(32);
			
		}
		
	}
	

	
}
