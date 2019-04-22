package com.avarioncraft.Clans.data.clanIntern.clanUpgrades.armory.upgradeables.buffs;

import org.bukkit.entity.Player;

import com.avarioncraft.AvarionCombat.buffs.StatBuff;
import com.avarioncraft.AvarionCombat.events.StatModifyEvent;
import com.avarioncraft.AvarionCombat.util.enums.Stat;

public class UpgradeBuff extends StatBuff{

	public UpgradeBuff(Player player, Stat stat, int percent) {
		super(player);
		this.stat = stat;
		this.percent = percent;
	}
	
	private final Stat stat;
	private int percent;
	
	@Override
	public void onModify(StatModifyEvent event) {
		
		if(event.getStat().equals(stat)) {
			event.setValue(event.getValue() + (event.getValue() * (this.percent / 100)));
		}
		
	}

}
