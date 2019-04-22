package com.avarioncraft.Clans.data.clanIntern.clanUpgrades.producer.upgradeables;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.IJobUpgrade;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.UpgradeComponent;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.UpgradePointSystem;
import com.avarioncraft.Clans.util.enums.upgradeSubTypes.ProducerJobUpgradeType;
import com.avarioncraft.Jobs.buffs.BaseExpBuff;
import com.avarioncraft.Jobs.data.JobPlayer;
import com.avarioncraft.Jobs.util.enums.Job;

import lombok.Getter;

public class CookUpgrade extends UpgradeComponent implements IJobUpgrade{

	public CookUpgrade(UpgradePointSystem system, Clan clan, String displayName) {
		super(10, system, clan, displayName);
		this.type = ProducerJobUpgradeType.COOK;
	}
	
	private BaseExpBuff buff = new BaseExpBuff(Job.COOK, 10);
	
	//Variablen
	@Getter
	private final ProducerJobUpgradeType type;
	
	//UtilMethoden
	@Override
	public boolean isUpgradeable() {
		if(super.getMaxLevel() > super.getLevel() && this.getUpgradeCost() <= super.getSystem().getUpgradePoints()) {
			return true;
		}
		return false;
	}

	@Override
	public int getUpgradeCost() {
		return super.getLevel() + 5 + (super.getLevel() * 5);
	}

	@Override
	public void onUpgrade() {
		
		super.getSystem().removeUpgradePoints(this.getUpgradeCost());
		super.setLevel(super.getLevel() + 1);
		this.buff.setPercentBuff(super.getLevel() * 10);
		
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void applyAll() {
		
		super.getClan().getOnlineMembers().forEach(player->this.applyFor(player));
		
	}

	@Override
	public void applyFor(Player player) {
		
		JobPlayer.of(player).getExpBuffs().add(this.buff);
		
	}

}
