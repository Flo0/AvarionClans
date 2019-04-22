package com.avarioncraft.Clans.gui.owner;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.bank.Bank;
import com.avarioncraft.Clans.gui.GUIs;
import com.avarioncraft.Clans.util.MathUtil;

import net.crytec.api.itemstack.ItemBuilder;
import net.crytec.api.smartInv.ClickableItem;
import net.crytec.api.smartInv.content.InventoryContents;
import net.crytec.api.smartInv.content.InventoryProvider;
import net.crytec.api.smartInv.content.SlotPos;
import net.crytec.api.util.F;

public class ClanOwnerMainMenu implements InventoryProvider {
	
	@Override
	public void init(Player player, InventoryContents contents) {
		Clan clan = Clan.ofMember(player).get();
		Bank bank = clan.getUpgrades().getBank();
		
		ItemStack placeholder = new ItemBuilder(clan.getMenuStyle().getGlass()).name(" ").build();
		
		contents.fillRow(0, ClickableItem.empty(placeholder));
		contents.fillRow(5, ClickableItem.empty(placeholder));
		
		contents.set(SlotPos.of(0, 4), ClickableItem.empty(new ItemBuilder(clan.getBanner()).name("§6" + clan.getName())
					.lore(" ")
					.lore("§eErfahrung: §f" + clan.getExperience())
					.lore("§eLevel: §f" + clan.getLevel())
					.lore(" ")
					.lore("§eOnline : §f*/*")
					.lore(" ")
					.lore("§ePrivatsphäre: §f" + clan.getPrivacy().getDisplayname())
					.build()));
		
		contents.set(SlotPos.of(1, 2), ClickableItem.empty(clan.getClanResources().getIcon()));
		
		// Member Button
		contents.set(SlotPos.of(1, 4), ClickableItem.of(new ItemBuilder(Material.PLAYER_HEAD).name("§eMitglieder").build(), e -> GUIs.MemberList.open(player)));
		
		contents.set(SlotPos.of(1, 6), ClickableItem.of(new ItemBuilder(Material.WRITTEN_BOOK).name("§eClan Log").build(), e -> {
			player.sendMessage(F.error("Feature ist noch nicht implementiert!"));

		}));
		
		contents.set(SlotPos.of(1, 8), ClickableItem.of(new ItemBuilder(Material.SIGN).name("§eRänge").build(), e -> {
			GUIs.ClanRankGUI.open(player);

		}));
		
		contents.set(SlotPos.of(1, 0), ClickableItem.of(new ItemBuilder(Material.STRUCTURE_BLOCK).name("§eClan Upgrades").build(), e -> {
			GUIs.ClanUpgradeGUI.open(player);
		}));		
		
		contents.set(SlotPos.of(3, 0), ClickableItem.of(new ItemBuilder(Material.BOOKSHELF).name("§eClan Nachrichten").build(), e -> {
			GUIs.ClanMessageManager.open(player);
		}));
		
		contents.set(SlotPos.of(3, 2), ClickableItem.of(new ItemBuilder(Material.END_PORTAL_FRAME).name("§eClan Einladungen / Privatsphäre").build(), e -> {
			GUIs.Invitation.open(player);
		}));
		
		contents.set(SlotPos.of(3, 4), ClickableItem.of(new ItemBuilder(Material.ITEM_FRAME).name("§eClan Style").build(), e -> {
			GUIs.ClanStyleGUI.open(player);
		}));
		
		contents.set(SlotPos.of(3, 6), ClickableItem.of(new ItemBuilder(Material.GOLD_BLOCK)
				.name("§6Clan Bank")
				.lore("")
				.lore("§eLevel: §f" + bank.getLevel() + " / " + bank.getMaxLevel())
				.lore("")
				.lore("§eSchatzkammer: §f" + bank.getMoney() + " / " + bank.getMaxMoney())
				.lore(MathUtil.ProgressBar(bank.getMoney(), bank.getMaxMoney(), 20, "▇"))
				.build(), e -> {
			GUIs.ClanBankGUI.open(player);
		}));

		
		contents.set(SlotPos.of(5, 8), ClickableItem.of(new ItemBuilder(Material.TNT).name("§4§lClan löschen")
					.lore("§fLöscht deinen clan permanent.")
					.lore("§fDies kann nicht rückgängig gemacht werden.")
					.build(), e -> {
						player.sendMessage(F.error("Feature ist noch nicht implementiert!"));
					}));
		
	}

	@Override
	public void update(Player player, InventoryContents contents) {
		
	}

}
