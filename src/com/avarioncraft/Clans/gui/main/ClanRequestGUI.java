package com.avarioncraft.Clans.gui.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.data.message.ClanMessage;
import com.avarioncraft.Clans.data.message.MessageBuilder;
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

public class ClanRequestGUI implements InventoryProvider{
	
	private static ItemStack MI = new ItemBuilder(Material.PAPER).build();
	
	@Override
	public void init(Player player, InventoryContents contents) {
		
		Clan clan = Clan.ofMember(player).get();
		Material mat = clan.getMenuStyle().getGlass();
		
		ItemStack placeholder = new ItemBuilder(mat).name(" ").build();
		
		contents.fillRow(0, ClickableItem.empty(placeholder));
		contents.fillRow(4, ClickableItem.empty(placeholder));
		
		Pagination pagination = contents.pagination();
		
		Set<ClanMessage> messages = MessageBuilder.get().ClanMessages.get(clan);
		ArrayList<ClickableItem> clicks = Lists.newArrayList();
		ItemStack paper;
		ClickableItem click;
		
		for(ClanMessage message : messages) {
			paper = new ItemBuilder(MI.clone())
					.name("§e" + message.getTitle())
					.lore(Arrays.asList(message.getLores()))
					.lore("")
					.lore("§f" + message.getSender())
					.lore("§f" + message.getDate())
					.build();
			click = ClickableItem.of(paper, event ->{
				UUID playerID = UUID.fromString(message.getSender().split("@")[1]);
				if(event.getClick() != ClickType.LEFT) {
					player.sendMessage(F.main("Clan", "§fDu hast §c" + message.getSender().split("@")[0] + " §fabgelehnt."));
					messages.remove(message);
					MessageBuilder.get().addClanResponse(playerID, clan, false);
					GUIs.ClanRequestsGUI.open(player);
					return;
				}
				player.sendMessage(F.main("Clan", "§fDu hast §e" + message.getSender().split("@")[0] + " §fangenommen."));
				if(Clan.ofMember(playerID).isPresent()) {
					messages.remove(message);
					player.sendMessage(F.error("Dieser Spieler ist bereits einem Clan beigetreten."));
					GUIs.ClanRequestsGUI.open(player);
					return;
				}
				clan.addMember(playerID);
				messages.remove(message);
				MessageBuilder.get().addClanResponse(playerID, clan, true);
				GUIs.ClanRequestsGUI.open(player);
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
            contents.inventory().getParent().get().open(player);
        }));
 
        contents.set(SlotPos.of(4, 0), ClickableItem.of(new ItemBuilder(Material.PAPER).name("Seite zurück").build(), e -> {
            contents.inventory().open(player, pagination.previous().getPage());
        }));
		
	}

	@Override
	public void update(Player player, InventoryContents contents) {
		
	}

}
