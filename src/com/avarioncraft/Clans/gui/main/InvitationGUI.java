package com.avarioncraft.Clans.gui.main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.data.message.MessageBuilder;
import com.avarioncraft.Clans.gui.GUIs;
import com.avarioncraft.Clans.util.enums.ClanPrivacy;
import com.avarioncraft.Clans.util.enums.Permission;

import net.crytec.api.itemstack.ItemBuilder;
import net.crytec.api.smartInv.ClickableItem;
import net.crytec.api.smartInv.content.InventoryContents;
import net.crytec.api.smartInv.content.InventoryProvider;
import net.crytec.api.smartInv.content.SlotPos;
import net.crytec.api.util.ChatStringInput;
import net.crytec.api.util.F;

public class InvitationGUI implements InventoryProvider{

	@SuppressWarnings("deprecation")
	@Override
	public void init(Player player, InventoryContents contents) {
		
		Clan clan = Clan.ofMember(player).get();
		
		ItemStack placeholder = new ItemBuilder(clan.getMenuStyle().getGlass()).name(" ").build();
		contents.fillRow(0, ClickableItem.empty(placeholder));
		contents.fillRow(4, ClickableItem.empty(placeholder));
		
		if (clan.getRank(player).hasPermission(Permission.INVITE)) {
			contents.set(SlotPos.of(2, 2), ClickableItem.of(new ItemBuilder(Material.BOOK).name("§eSpieler einladen").build(), e -> {
				player.sendMessage(F.main("Einladung", "Bitte den Spielernamen eingeben."));
				player.closeInventory();

				ChatStringInput.addPlayer(player, input ->{
					OfflinePlayer op = Bukkit.getOfflinePlayer(input);
					if (op == null) {
					}else {
						MessageBuilder.get().addClanInvitation(op.getUniqueId(), clan);
					}
				});
				
			}));
		}else {
			contents.set(SlotPos.of(2, 2), ClickableItem.empty(new ItemBuilder(Material.BARRIER).name("§cKeine Rechte Einladungen zu vergeben").build()));
		}
		
		if (clan.getRank(player).hasPermission(Permission.PRIVACY)) {
			contents.set(SlotPos.of(2, 4), ClickableItem.of(new ItemBuilder(clan.getPrivacy().getMaterial())
					.name("§eClan Privatsphäre: " + clan.getPrivacy().getDisplayname())
					.lore(clan.getPrivacy().getDescription())
					.lore("")
					.lore("§eLinksklick -> §fAuf §cPrivat §fstellen.")
					.lore("§eMittelklick -> §fAuf §6Gesichert §fstellen.")
					.lore("§eRechtsklick -> §fAuf §aÖffentlich §fstellen.")
					.build(), e -> {
						ClickType type = e.getClick();
						
						if(type == ClickType.LEFT) {
							clan.setPrivacy(ClanPrivacy.PRIVATE);
						}else if(type == ClickType.RIGHT) {
							clan.setPrivacy(ClanPrivacy.PUBLIC);
						}else if(type == ClickType.MIDDLE) {
							clan.setPrivacy(ClanPrivacy.PROTECTED);
						}
				GUIs.Invitation.open(player);
			}));
		}else {
			contents.set(SlotPos.of(2, 4), ClickableItem.empty(new ItemBuilder(Material.BARRIER).name("§cKeine Rechte für Privatsphäre Einstellungen").build()));
		}

		
		if (clan.getRank(player).hasPermission(Permission.INVITE)) {
			contents.set(SlotPos.of(2, 6), ClickableItem.of(new ItemBuilder(Material.PAPER).name("§eAnfragen annehmen").build(), e -> {
				GUIs.ClanRequestsGUI.open(player);
			}));
		}else {
			contents.set(SlotPos.of(2, 6), ClickableItem.empty(new ItemBuilder(Material.BARRIER).name("§cKeine Rechte Anfragen anzunehmen").build()));
		}
        
        contents.set(SlotPos.of(4, 4), ClickableItem.of(new ItemBuilder(Material.BEACON).name("§fZurück").build(), e -> {
            GUIs.openMain(player);
        }));

		
	}

	@Override
	public void update(Player player, InventoryContents contents) {
		
	}

}
