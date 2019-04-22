package com.avarioncraft.Clans.gui.main;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.gui.GUIs;

import net.crytec.api.itemstack.ItemBuilder;
import net.crytec.api.smartInv.ClickableItem;
import net.crytec.api.smartInv.content.InventoryContents;
import net.crytec.api.smartInv.content.InventoryProvider;
import net.crytec.api.smartInv.content.SlotPos;

public class MemberManagerGUI implements InventoryProvider {

	@Override
	public void init(Player player, InventoryContents contents) {
		Clan clan = Clan.ofMember(player).get();
		String target = contents.property("target");
		UUID targetUUID = contents.property("targetUUID");
		
		ItemStack placeholder = new ItemBuilder(clan.getMenuStyle().getGlass()).name(" ").build();
		contents.fillRow(0, ClickableItem.empty(placeholder));
		contents.fillRow(2, ClickableItem.empty(placeholder));
		
		contents.set(SlotPos.of(0, 4), ClickableItem.empty(new ItemBuilder(clan.getRank(targetUUID).getIcon())
					.name("§f" + target)
					.lore("§fRang: " + clan.getRank(targetUUID).getDisplayName())
					.build()));
		
		
		contents.set(SlotPos.of(1, 0), ClickableItem.of(new ItemBuilder(Material.SIGN).name("§fRang setzen").build(), e -> {
			GUIs.Sub_RankSelect.open(player, new String[] { "target", "targetUUID" }, new Object[] { target, targetUUID });
		}));

		
		contents.set(SlotPos.of(1, 7), ClickableItem.of(new ItemBuilder(Material.TNT).name("§fSpieler kicken").build(), e -> {
			
			
		}));
		
		
		contents.set(SlotPos.of(1, 8), ClickableItem.of(new ItemBuilder(Material.BARRIER).name("§fSpieler bannen").build(), e -> {
			
			
		}));
		
        contents.set(SlotPos.of(2, 4), ClickableItem.of(new ItemBuilder(Material.BEACON).name("§fZurück").build(), e -> {
            GUIs.openMain(player);
        }));
		
	}

	@Override
	public void update(Player player, InventoryContents contents) { }

}