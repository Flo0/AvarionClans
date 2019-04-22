package com.avarioncraft.Clans.gui.main;

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
import net.crytec.api.util.ChatStringInput;
import net.crytec.api.util.F;

public class LackClanGUI implements InventoryProvider{

	@Override
	public void init(Player player, InventoryContents contents) {
		
		ItemStack CreateItem = new ItemBuilder(Material.IRON_SWORD)
				.name("§eClan erstellen")
				.lore("")
				.lore("§fHier kannst du einen")
				.lore("§feigenen clan erstellen.")
				.build();
		
		ItemStack JoinItem = new ItemBuilder(Material.OAK_DOOR)
				.name("§eClan Beitreten")
				.lore("")
				.lore("§fHier kannst du einem")
				.lore("§fbestehenden clan beitreten.")
				.build();
		
		ItemStack MessageItem = new ItemBuilder(Material.BOOK)
				.name("§eClan einladungen")
				.lore("")
				.lore("§fHier siehst du, ob deine")
				.lore("§fAnfragen angenommen wurden.")
				.build();
		
		ClickableItem CreateClick = ClickableItem.of(CreateItem, event ->{
			
			player.closeInventory();
			player.sendMessage(F.main("Clan", "Bitte gebe einen Clannamen in den Chat ein:"));
			ChatStringInput.addPlayer(player, name -> {
				
				Clan clan = Clan.create(player.getUniqueId(), name);
				
				if (clan != null) {
					player.sendMessage(F.main("Clan", "Dein Clan " + F.name(clan.getName()) + " wurde erstellt."));
				} else {
					player.sendMessage(F.error("Der Name " + F.name(name) + " ist bereits vergeben oder enthält nicht erlaubte Zeichen."));
				}
			});
			
		});
		contents.set(SlotPos.of(1, 4), CreateClick);

		ClickableItem JoinClick = ClickableItem.of(JoinItem, event -> GUIs.ClanListGUI.open(player));
		contents.set(SlotPos.of(1, 6), JoinClick);
		
		ClickableItem MessageClick = ClickableItem.of(MessageItem, event -> {
			GUIs.ClanMessages.open(player);
		});
		contents.set(SlotPos.of(1, 2), MessageClick);
		
	}

	@Override
	public void update(Player player, InventoryContents contents) {
		
	}

}
