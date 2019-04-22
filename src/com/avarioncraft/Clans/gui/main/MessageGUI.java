package com.avarioncraft.Clans.gui.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.data.message.ClanInvitation;
import com.avarioncraft.Clans.data.message.ClanMessage;
import com.avarioncraft.Clans.data.message.MessageBuilder;
import com.avarioncraft.Clans.events.ClanMessageRemoveEvent;
import com.avarioncraft.Clans.gui.GUIs;
import com.google.common.collect.Lists;

import net.crytec.api.itemstack.ItemBuilder;
import net.crytec.api.smartInv.ClickableItem;
import net.crytec.api.smartInv.content.InventoryContents;
import net.crytec.api.smartInv.content.InventoryProvider;
import net.crytec.api.smartInv.content.Pagination;
import net.crytec.api.smartInv.content.SlotIterator;
import net.crytec.api.smartInv.content.SlotIterator.Type;
import net.crytec.api.smartInv.content.SlotPos;
import net.crytec.api.util.F;

public class MessageGUI implements InventoryProvider{
	
	private static ItemStack MI = new ItemBuilder(Material.PAPER).build();
	
	@Override
	public void init(Player player, InventoryContents contents) {
		
		Material mat;
		
		if(Clan.ofMember(player).isPresent()) {
			mat = Clan.ofMember(player).get().getMenuStyle().getGlass();
		}else {
			mat = Material.GRAY_STAINED_GLASS_PANE;
		}
		ItemStack placeholder = new ItemBuilder(mat).name(" ").build();
		
		contents.fillRow(0, ClickableItem.empty(placeholder));
		contents.fillRow(4, ClickableItem.empty(placeholder));
		
		Pagination pagination = contents.pagination();
		
		Set<ClanMessage> messages = MessageBuilder.get().PlayerMessages.get(player.getUniqueId());
		ArrayList<ClickableItem> clicks = Lists.newArrayList();
		ItemStack paper;
		ClickableItem click;
		
		for(ClanMessage message : messages) {
			String end = "§cKlicke zum löschen.";
			if(message instanceof ClanInvitation) {
				end = "";
			}
			paper = new ItemBuilder(MI.clone())
					.name("§e" + message.getTitle())
					.lore(Arrays.asList(message.getLores()))
					.lore("")
					.lore("§f" + message.getSender())
					.lore("§f" + message.getDate())
					.lore("")
					.lore(end)
					.build();
			click = ClickableItem.of(paper, event ->{
				
				String title = event.getCurrentItem().getItemMeta().getDisplayName();
				if(title.contains("§e§a§eEinladung")) {
					if(event.getClick() == ClickType.LEFT) {
						Optional<Clan> clan = Clan.ofID(Integer.parseInt(message.getSender().split("@")[1]));
						if(clan.isPresent()) {
							if(Clan.ofMember(player).isPresent()) {
								player.sendMessage(F.error("Du bist schon in einem Clan."));
								return;
							}else {
								player.sendMessage(F.main("Clan", "Du bist einem Clan beigetreten."));
								clan.get().addMember(player.getUniqueId());
							}
						}else {
							player.sendMessage(F.error("Dieser Clan existiert nicht mehr."));
						}
					}
				}
				
				ClanMessageRemoveEvent remove_event = new ClanMessageRemoveEvent(message, Clan.ofMember(player).orElse(null), player);
				Bukkit.getPluginManager().callEvent(remove_event);
				
				messages.remove(remove_event.getMessage());
				GUIs.ClanMessages.open(player);
			});
			clicks.add(click);
		
		}
		
		ClickableItem[] clickarray = new ClickableItem[clicks.size()];
        
        clickarray = clicks.toArray(clickarray);
		
		pagination.setItems(clickarray);
        
        pagination.setItemsPerPage(36);
        
        SlotIterator iterator = contents.newIterator(Type.HORIZONTAL, SlotPos.of(1, 0));
        
        iterator.allowOverride(false);
        
        pagination.addToIterator(iterator);
		
        contents.set(SlotPos.of(4, 8), ClickableItem.of(new ItemBuilder(Material.PAPER).name("Seite vor").build(), e -> {
            contents.inventory().open(player, pagination.next().getPage());
        }));
        
        contents.set(SlotPos.of(4, 4), ClickableItem.of(new ItemBuilder(Material.BEACON).name("§fZurück").build(), e -> {
            GUIs.openMain(player);
        }));
 
        contents.set(SlotPos.of(4, 0), ClickableItem.of(new ItemBuilder(Material.PAPER).name("Seite zurück").build(), e -> {
            contents.inventory().open(player, pagination.previous().getPage());
        }));
		
	}
	
	@Override
	public void update(Player player, InventoryContents contents) {
	
	}
	
}
