package com.avarioncraft.Clans.gui.main;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.data.clanIntern.ClanUpgrade;
import com.avarioncraft.Clans.data.clanIntern.UpgradeCosts;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.UpgradePointSystem;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.bank.Bank;
import com.avarioncraft.Clans.gui.GUIs;
import com.avarioncraft.Clans.gui.componentgui.ComponentGUI;

import net.crytec.api.itemstack.ItemBuilder;
import net.crytec.api.smartInv.ClickableItem;
import net.crytec.api.smartInv.SmartInventory;
import net.crytec.api.smartInv.content.InventoryContents;
import net.crytec.api.smartInv.content.InventoryProvider;
import net.crytec.api.smartInv.content.SlotIterator;
import net.crytec.api.smartInv.content.SlotIterator.Type;
import net.crytec.api.smartInv.content.SlotPos;

public class ClanUpgradesGUI implements InventoryProvider{
	
	private Material getWool(boolean afford) {
		return afford ? Material.GREEN_WOOL : Material.RED_WOOL;
	}
	
	@Override
	public void init(Player player, InventoryContents contents) {
		
		Clan clan = Clan.ofMember(player).get();
		
		ItemStack placeholder = new ItemBuilder(clan.getMenuStyle().getGlass()).name(" ").build();
		contents.fillRow(0, ClickableItem.empty(placeholder));
		contents.fillRow(5, ClickableItem.empty(placeholder));
		
		SlotIterator slotIterator = contents.newIterator(Type.HORIZONTAL, 3, 0);
		
		for (ClanUpgrade u : clan.getUpgrades().getUpgrades().values()) {
			
			if(u instanceof Bank) continue;
			
			UpgradeCosts costs = u.getUpgradeCosts();
			boolean affordable = costs.isAffordable();
			
			ItemStack icon  = new ItemBuilder(this.getWool(affordable))
					.lore("")
					.lore(u.getUpgradeCosts().getCostLore())
					.name("§6" + u.getType().getDisplayName() + " Upgraden")
					.build();
			
			ClickableItem ci = ClickableItem.of(icon, e -> {
				if (affordable) {
					u.levelUP(false);
					GUIs.ClanUpgradeGUI.open(player);
				}
			});
			
			SlotIterator iter = slotIterator.next();
			contents.set(SlotPos.of(3, iter.column()), ci);
			contents.set(SlotPos.of(2, iter.column()), ClickableItem.of(u.getIcon(), e -> {
				if(u instanceof UpgradePointSystem) {
					SmartInventory.builder().provider(new ComponentGUI((UpgradePointSystem) u)).size(4, 9).build().open(player);
				}
			}));
			
		}
		
		
        contents.set(SlotPos.of(5, 4), ClickableItem.of(new ItemBuilder(Material.BEACON).name("§fZurück").build(), e -> {
            GUIs.openMain(player);
        }));
	}

	@Override
	public void update(Player player, InventoryContents contents) {
		
	}
}