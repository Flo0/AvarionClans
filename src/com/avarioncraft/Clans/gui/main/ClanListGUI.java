package com.avarioncraft.Clans.gui.main;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.util.enums.ClanPrivacy;
import com.google.common.collect.Lists;

import net.crytec.api.itemstack.ItemBuilder;
import net.crytec.api.smartInv.ClickableItem;
import net.crytec.api.smartInv.content.InventoryContents;
import net.crytec.api.smartInv.content.InventoryProvider;
import net.crytec.api.smartInv.content.Pagination;
import net.crytec.api.smartInv.content.SlotIterator;
import net.crytec.api.smartInv.content.SlotIterator.Type;
import net.crytec.api.smartInv.content.SlotPos;

public class ClanListGUI implements InventoryProvider {

	@Override
	public void init(Player player, InventoryContents contents) {

		ArrayList<ClickableItem> items = Lists.newArrayList();
		Pagination pagination = contents.pagination();

		for (Clan clan : Clan.getAllClans(ClanPrivacy.PUBLIC)) {
			items.add(ClickableItem.of(new ItemBuilder(Material.BLACK_BANNER).name(clan.getName()).build(), e -> {
				player.sendMessage("Debug - " + clan.getName());
			}));
		}

		for (Clan clan : Clan.getAllClans(ClanPrivacy.PROTECTED)) {
			items.add(ClickableItem.of(new ItemBuilder(Material.BLACK_BANNER).name(clan.getName()).build(), e -> {
				player.sendMessage("Debug - " + clan.getName());
			}));
		}

		ClickableItem[] c = new ClickableItem[items.size()];
		c = items.toArray(c);
		pagination.setItems(c);
		pagination.setItemsPerPage(18);

		SlotIterator slotIterator = contents.newIterator(Type.HORIZONTAL, SlotPos.of(1, 0));
		slotIterator = slotIterator.allowOverride(false);
		pagination.addToIterator(slotIterator);
	}

	@Override
	public void update(Player player, InventoryContents contents) {

	}

}
