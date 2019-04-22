package com.avarioncraft.Clans.gui.owner;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.data.Rank;
import com.avarioncraft.Clans.gui.GUIs;
import com.avarioncraft.Clans.util.enums.Permission;

import net.crytec.api.itemstack.ItemBuilder;
import net.crytec.api.smartInv.ClickableItem;
import net.crytec.api.smartInv.content.InventoryContents;
import net.crytec.api.smartInv.content.InventoryProvider;
import net.crytec.api.smartInv.content.SlotPos;

public class RankEditGUI implements InventoryProvider {

	@Override
	public void init(Player player, InventoryContents contents) {
		Rank rank = contents.property("rank");
		Clan clan = Clan.ofMember(player).get();
		
		ItemStack placeholder = new ItemBuilder(clan.getMenuStyle().getGlass()).name(" ").build();
		contents.fillRow(0, ClickableItem.empty(placeholder));
		contents.fillRow(5, ClickableItem.empty(placeholder));
		
		for (Permission perm : Permission.values()) {
			
			ItemBuilder builder = new ItemBuilder(Material.GRAY_DYE).name("§7" + perm.getDisplayName());
			
			if (rank.hasPermission(perm)) {
				builder = new ItemBuilder(Material.LIME_DYE);
				builder.name("§2" + perm.getDisplayName());
			}
			
			contents.add(ClickableItem.of(builder.build(), e -> {
					if (rank.hasPermission(perm)) {
						rank.removePerm(perm);
						GUIs.ClanRankEditGUI.open(player, new String[] { "rank" }, new Object[] { rank });
					} else {
						rank.addPerm(perm);
						GUIs.ClanRankEditGUI.open(player, new String[] { "rank" }, new Object[] { rank });
					}
			}));
		}
		
		
		ItemStack back = new ItemBuilder(Material.BEACON).name("§fZurück").build();
		contents.set(SlotPos.of(5, 4), ClickableItem.of(back, event->{
			contents.inventory().getParent().get().open(player);
		}));
	}

	@Override
	public void update(Player player, InventoryContents contents) {
		
	}
}