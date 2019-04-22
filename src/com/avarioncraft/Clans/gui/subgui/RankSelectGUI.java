package com.avarioncraft.Clans.gui.subgui;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.data.Rank;
import com.avarioncraft.Clans.gui.GUIs;

import net.crytec.api.itemstack.ItemBuilder;
import net.crytec.api.smartInv.ClickableItem;
import net.crytec.api.smartInv.content.InventoryContents;
import net.crytec.api.smartInv.content.InventoryProvider;
import net.crytec.api.smartInv.content.SlotPos;
import net.crytec.api.util.UtilPlayer;

public class RankSelectGUI implements InventoryProvider {

	@Override
	public void init(Player player, InventoryContents contents) {

		UUID targetUUID = contents.property("targetUUID");
		Clan clan = Clan.ofMember(targetUUID).get();

		for (Rank rank : clan.getRanks().getAllRanks()) {
			if (rank.isOwner())
				continue;
			contents.add(ClickableItem.of(new ItemBuilder(rank.getIcon()).name(rank.getDisplayName()).build(), e -> {
				clan.setRank(targetUUID, rank);
				UtilPlayer.playSound(player, Sound.BLOCK_ENCHANTMENT_TABLE_USE);
			}));
		}

		contents.set(SlotPos.of(2, 4), ClickableItem.of(new ItemBuilder(Material.BEACON).name("§fZurück").build(), e -> {
			GUIs.MemberManageGUI.open(player, new String[] { "target", "targetUUID" }, new Object[] { contents.property("target"), targetUUID });
		}));

	}

	@Override
	public void update(Player player, InventoryContents contents) {

	}

}
