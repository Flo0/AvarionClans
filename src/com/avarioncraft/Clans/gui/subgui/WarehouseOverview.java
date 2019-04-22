package com.avarioncraft.Clans.gui.subgui;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.warehouse.Warehouse;

import net.crytec.api.itemstack.ItemBuilder;
import net.crytec.api.smartInv.ClickableItem;
import net.crytec.api.smartInv.content.InventoryContents;
import net.crytec.api.smartInv.content.InventoryProvider;

public class WarehouseOverview implements InventoryProvider {

	@Override
	public void init(Player player, InventoryContents contents) {
		
		Clan clan = Clan.ofMember(player).get();
		Warehouse wh = clan.getUpgrades().getWarehouse();
				
		for (int i = 0; i < 54; i++) {
			final int chest = i;
			if (wh.getStock().getChest(i).isEnabled()) {
				contents.add(ClickableItem.of(new ItemBuilder(Material.CHEST).name("§eKiste §6#" + i).build(), e -> {
					wh.getStock().getChest(chest).open(player);
				}));
			} else {
				contents.add(ClickableItem.empty(new ItemBuilder(Material.BARRIER).name("§cNicht verfügbar").build()));
			}
		}
		
	}

	@Override
	public void update(Player player, InventoryContents contents) {
		
	}

}
