package com.avarioncraft.Clans.gui.owner;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.data.Rank;
import com.avarioncraft.Clans.data.RankContainer;
import com.avarioncraft.Clans.gui.GUIs;
import com.avarioncraft.Clans.util.enums.Permission;

import net.crytec.api.itemstack.ItemBuilder;
import net.crytec.api.smartInv.ClickableItem;
import net.crytec.api.smartInv.content.InventoryContents;
import net.crytec.api.smartInv.content.InventoryProvider;
import net.crytec.api.smartInv.content.SlotPos;
import net.crytec.api.util.ChatStringInput;
import net.crytec.api.util.F;

public class ClanOwnerRankGUI implements InventoryProvider {

	@Override
	public void init(Player player, InventoryContents contents) {
		Clan clan = Clan.ofMember(player).get();
		RankContainer rcon = clan.getRanks();
		
		ItemStack placeholder = new ItemBuilder(clan.getMenuStyle().getGlass()).name(" ").build();
		
		contents.fillRow(0, ClickableItem.empty(placeholder));
		contents.fillRow(4, ClickableItem.empty(placeholder));
		
		
		if (clan.getRank(player).hasPermission(Permission.RANK_MANAGER)) {
		if (rcon.getTotalRanks() < 18) {
		
		contents.set(SlotPos.of(0, 8), ClickableItem.of(new ItemBuilder(Material.EMERALD).name("§2Rang hinzufügen").build(), e -> {
			player.sendMessage(F.main("Clan", "Bitte gebe den neuen Rangnamen in den Chat ein:"));
			player.closeInventory();
			ChatStringInput.addPlayer(player, rang -> {
				rcon.addRank(rang, false, false);
				GUIs.ClanRankGUI.open(player);
			});
		}));
		} else {
			contents.set(SlotPos.of(0, 8), ClickableItem.empty(new ItemBuilder(Material.REDSTONE)
						.name("§cMaximale Anzahl")
						.lore("§fDu hast die maximale Anzahl")
						.lore("§fan Rängen in diesem Clan")
						.lore("§ferreicht.")
						.build()));
		}
		} else {
			// NO Perm
		}
		
		contents.set(SlotPos.of(1, 0), ClickableItem.of(new ItemBuilder(Material.SEA_PICKLE).name("§f" + rcon.getDefaultRank().getDisplayName()).build(), e -> {
			GUIs.ClanRankEditGUI.open(player, new String[] { "rank" }, new Object[] { rcon.getDefaultRank() });
		} ));
		
		for (Rank rank : clan.getRanks().getAllRanks()) {
			if (rank == rcon.getOwnerRank() || rank == rcon.getDefaultRank()) continue;
			contents.add(ClickableItem.of(new ItemBuilder(rank.getIcon())
						.name("§f" + rank.getDisplayName())
						.lore("§aLinksklick §fum den Rang zu bearbeiten.")
						.lore("§aRechtsklick §fum den Rang zu löschen.")
						.build(), e -> { 
				if (clan.getRank(player).hasPermission(Permission.RANK_MANAGER)) {
					
					if (e.getClick() == ClickType.LEFT) {
						GUIs.ClanRankEditGUI.open(player, new String[] { "rank" }, new Object[] { rank });
					} else {
						rcon.removeRank(rank.getDisplayName());
						contents.inventory().open(player);
					}
					
					
				}
			} ));
		}
		
		contents.add(ClickableItem.empty(new ItemBuilder(Material.GOLDEN_HELMET).setItemFlag(ItemFlag.HIDE_ATTRIBUTES).name("§f" + rcon.getOwnerRank().getDisplayName()).build()));
		
		ItemStack back = new ItemBuilder(Material.BEACON).name("§fZurück").build();
		contents.set(SlotPos.of(4, 4), ClickableItem.of(back, event->{
			GUIs.openMain(player);
		}));
	}

	@Override
	public void update(Player player, InventoryContents contents) {
		
	}

}
