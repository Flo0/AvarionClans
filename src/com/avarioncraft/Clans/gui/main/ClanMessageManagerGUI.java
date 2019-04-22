package com.avarioncraft.Clans.gui.main;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.data.message.manager.ClanMessageManager;
import com.avarioncraft.Clans.gui.GUIs;

import net.crytec.api.itemstack.ItemBuilder;
import net.crytec.api.smartInv.ClickableItem;
import net.crytec.api.smartInv.content.InventoryContents;
import net.crytec.api.smartInv.content.InventoryProvider;
import net.crytec.api.smartInv.content.SlotPos;

public class ClanMessageManagerGUI implements InventoryProvider{
	
	@Override
	public void init(Player player, InventoryContents contents) {
		
		Clan clan = Clan.ofMember(player).get();
		
		ItemStack placeholder = new ItemBuilder(clan.getMenuStyle().getGlass()).name(" ").build();
		contents.fillRow(0, ClickableItem.empty(placeholder));
		contents.fillRow(4, ClickableItem.empty(placeholder));
		
		ItemStack back = new ItemBuilder(Material.BEACON).name("§fZurück").build();
		ItemStack ListStack = new ItemBuilder(Material.BOOK).name("§eClan-Nachrichten").build();
		ItemStack ArchiveStack = new ItemBuilder(Material.BOOKSHELF).name("§eClan-Archiv").build();
		ItemStack WriteStack = new ItemBuilder(Material.WRITABLE_BOOK).name("§eNachricht verfassen").build();
		
		contents.set(SlotPos.of(2, 2), ClickableItem.of(ListStack, event->{
			GUIs.ClanMessages.open(player);
		}));
		
		contents.set(SlotPos.of(2, 4), ClickableItem.of(ArchiveStack, event->{
			
		}));
		
		contents.set(SlotPos.of(2, 6), ClickableItem.of(WriteStack, event->{
			new ClanMessageManager(player);
			player.closeInventory();
		}));
		
		contents.set(SlotPos.of(4, 4), ClickableItem.of(back, event->{
			GUIs.openMain(player);
		}));
		
	}
	
	@Override
	public void update(Player player, InventoryContents contents) {}
	
}
