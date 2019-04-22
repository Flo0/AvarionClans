package com.avarioncraft.Clans.gui.main;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.gui.GUIs;
import com.avarioncraft.Clans.util.enums.Permission;
import com.google.common.collect.Lists;

import net.crytec.api.itemstack.ItemBuilder;
import net.crytec.api.smartInv.ClickableItem;
import net.crytec.api.smartInv.content.InventoryContents;
import net.crytec.api.smartInv.content.InventoryProvider;
import net.crytec.api.smartInv.content.Pagination;
import net.crytec.api.smartInv.content.SlotIterator;
import net.crytec.api.smartInv.content.SlotIterator.Type;
import net.crytec.api.util.UtilPlayer;
import net.crytec.api.smartInv.content.SlotPos;

public class ClanMemberListGUI implements InventoryProvider {

	@Override
	public void init(Player player, InventoryContents contents) {
		
		Clan clan = Clan.ofMember(player).get();
		
		ItemStack placeholder = new ItemBuilder(clan.getMenuStyle().getGlass()).name(" ").build();
		contents.fillRow(0, ClickableItem.empty(placeholder));
		contents.fillRow(4, ClickableItem.empty(placeholder));
		
		ArrayList<ClickableItem> items = Lists.newArrayList();
		Pagination pagination = contents.pagination();
		
		
		for (UUID uuid : clan.getMembers()) {
			OfflinePlayer op = Bukkit.getOfflinePlayer(uuid);
			
			ItemBuilder builder = new ItemBuilder(Material.PLAYER_HEAD);
			builder.setSkullOwner(op);
			builder.name("§f" + op.getName());
			builder.lore("§fRang: " + clan.getRank(uuid).getDisplayName());
			items.add(ClickableItem.of(builder.build(), e -> {
				if (uuid.equals(clan.getOwner())) return;
				
				if (clan.getRank(uuid).hasPermission(Permission.PROMOTE)) {
					GUIs.MemberManageGUI.open(player, new String[] { "target", "targetUUID" }, new Object[] { op.getName(), uuid });
				} else {
					UtilPlayer.playSound(player, Sound.ITEM_SHIELD_BREAK);
				}
				
			}));
		}
		
		ClickableItem[] c = new ClickableItem[items.size()];
		c = items.toArray(c);
		pagination.setItems(c);
		pagination.setItemsPerPage(36);

		SlotIterator slotIterator = contents.newIterator(Type.HORIZONTAL, SlotPos.of(1, 0));
		slotIterator = slotIterator.allowOverride(false);
		pagination.addToIterator(slotIterator);
		
        contents.set(SlotPos.of(4, 8), ClickableItem.of(new ItemBuilder(Material.PAPER).name("§fSeite vor").build(), e -> {
            contents.inventory().open(player, pagination.next().getPage());
        }));
        
        contents.set(SlotPos.of(4, 4), ClickableItem.of(new ItemBuilder(Material.BEACON).name("§fZurück").build(), e -> {
            GUIs.openMain(player);
        }));
 
        contents.set(SlotPos.of(4, 0), ClickableItem.of(new ItemBuilder(Material.PAPER).name("§fSeite zurück").build(), e -> {
            contents.inventory().open(player, pagination.previous().getPage());
        }));
		
		
	}

	@Override
	public void update(Player player, InventoryContents contents) {
		
	}

}
