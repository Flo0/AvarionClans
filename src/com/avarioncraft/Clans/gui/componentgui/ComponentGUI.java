package com.avarioncraft.Clans.gui.componentgui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.UpgradeComponent;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.UpgradePointSystem;

import net.crytec.api.itemstack.ItemBuilder;
import net.crytec.api.smartInv.ClickableItem;
import net.crytec.api.smartInv.content.InventoryContents;
import net.crytec.api.smartInv.content.InventoryProvider;
import net.crytec.api.smartInv.content.SlotPos;

public class ComponentGUI implements InventoryProvider {

	public ComponentGUI(UpgradePointSystem system) {
		this.system = system;
	}

	private UpgradePointSystem system;

	@Override
	public void init(Player player, InventoryContents contents) {

		ItemStack placeholder = new ItemBuilder(Clan.ofMember(player).get().getMenuStyle().getGlass()).name(" ").build();

		for (int count = 0; count <= 8; count++) {

			contents.add(ClickableItem.empty(placeholder));

		}

		contents.set(SlotPos.of(0, 4), ClickableItem.empty(system.getGuiIcon()));

		for (UpgradeComponent component : system.getUpgrades()) {
			contents.add(ClickableItem.of(component.getGuiItem(), event -> {

				if (component.isUpgradeable()) {
					component.onUpgrade();
				}

			}));
		}

	}

	@Override
	public void update(Player player, InventoryContents contents) {

	}

}