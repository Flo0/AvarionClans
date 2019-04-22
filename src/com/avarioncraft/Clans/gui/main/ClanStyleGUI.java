package com.avarioncraft.Clans.gui.main;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.gui.GUIs;
import com.avarioncraft.Clans.util.enums.ClanMenuStyle;

import net.crytec.api.itemstack.ItemBuilder;
import net.crytec.api.smartInv.ClickableItem;
import net.crytec.api.smartInv.content.InventoryContents;
import net.crytec.api.smartInv.content.InventoryProvider;
import net.crytec.api.smartInv.content.SlotPos;
import net.crytec.api.util.F;

public class ClanStyleGUI implements InventoryProvider{

	@Override
	public void init(Player player, InventoryContents contents) {
		
		Clan clan = Clan.ofMember(player).get();
		
		ItemStack placeholder = new ItemBuilder(clan.getMenuStyle().getGlass()).name(" ").build();
		contents.fillRow(0, ClickableItem.empty(placeholder));
		contents.fillRow(5, ClickableItem.empty(placeholder));
		
		int count = 0;
		for(ClanMenuStyle style : ClanMenuStyle.values()) {
			
			contents.set(SlotPos.of(2, count), ClickableItem.of(new ItemBuilder(style.getGlass()).name("§eAnzeige: §f" + style.getDisplayName()).build(), event ->{
				clan.setMenuStyle(style);
				GUIs.ClanStyleGUI.open(player);
			}));
			count++;
		}
		
		contents.set(SlotPos.of(3, 4), ClickableItem.of(new ItemBuilder(clan.getBanner()).name("§eBanner ändern").build(), event ->{
			player.sendMessage(F.error("Feature ist noch nicht implementiert!"));
		}));
		
		ItemStack back = new ItemBuilder(Material.BEACON).name("§fZurück").build();
		contents.set(SlotPos.of(5, 4), ClickableItem.of(back, event->{
			GUIs.openMain(player);
		}));

	}

	@Override
	public void update(Player player, InventoryContents contents) {
		
	}

}
