package com.avarioncraft.Clans.gui.main;

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
import net.crytec.api.util.ChatStringInput;
import net.crytec.api.util.F;
import net.crytec.api.util.UtilMath;

public class BankGUI implements InventoryProvider{

	@Override
	public void init(Player player, InventoryContents contents) {
		
		Clan clan = Clan.ofMember(player).get();
		
		ItemStack placeholder = new ItemBuilder(clan.getMenuStyle().getGlass()).name(" ").build();
		contents.fillRow(0, ClickableItem.empty(placeholder));
		contents.fillRow(4, ClickableItem.empty(placeholder));
		
		Bank bank = clan.getUpgrades().getBank();
		double currentmoney = bank.getMoney();
		double maxmoney = bank.getMaxMoney();
		
		contents.set(SlotPos.of(2, 2), ClickableItem.of(new ItemBuilder(Material.GREEN_WOOL).name("§eGeld einzahlen").build(), event->{
			player.closeInventory();
			player.sendMessage(F.main("Bank", "Bitte den Betrag eingeben."));
			
			ChatStringInput.addPlayer(player, input->{
				input.replaceAll(",", ".");
				
				if (!UtilMath.isDouble(input)) {
					player.sendMessage(F.error("Nur einen normalen Dezimalwert eingeben."));
					return;
				}
				
				double money = MathUtil.roundAvoid(Double.parseDouble(input), 2);
				
				bank.addMoney(player, money);
				
			});
		}));
		
		contents.set(SlotPos.of(2, 4), ClickableItem.empty(new ItemBuilder(Material.GOLD_BLOCK)
				.name("§6Clan Bank")
				.lore("")
				.lore("§eLevel: §f" + bank.getLevel() + " / " + bank.getMaxLevel())
				.lore("")
				.lore("§eSchatzkammer: §f" + currentmoney + " / " + maxmoney)
				.lore(MathUtil.ProgressBar(currentmoney, maxmoney, 20, "â–ˆ"))
				.build()));
		
		contents.set(SlotPos.of(2, 6), ClickableItem.of(new ItemBuilder(Material.RED_WOOL).name("§eGeld abheben").build(), event->{
			player.closeInventory();
			player.sendMessage(F.main("Bank", "Bitte den Betrag eingeben."));
			
			ChatStringInput.addPlayer(player, input->{
				ChatStringInput.addPlayer(player, output->{
					output.replaceAll(",", ".");
					
					if (!UtilMath.isDouble(output)) {
						player.sendMessage(F.error("Nur einen normalen Dezimalwert eingeben."));
						return;
					}
					
					double money = MathUtil.roundAvoid(Double.parseDouble(output), 2);
					
					bank.withdrawMoney(player, money);
					
				});
			});
		}));
		
        contents.set(SlotPos.of(4, 4), ClickableItem.of(new ItemBuilder(Material.BEACON).name("§fZurück").build(), e -> {
            GUIs.openMain(player);
        }));
		
	}

	@Override
	public void update(Player player, InventoryContents contents) {
		
	}

}
