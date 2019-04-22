package com.avarioncraft.Clans.gui.owner;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.regions.protection.ProtectionManager;
import com.avarioncraft.Clans.regions.selection.PreviewMode;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import net.crytec.api.itemstack.ItemBuilder;
import net.crytec.api.smartInv.ClickableItem;
import net.crytec.api.smartInv.content.InventoryContents;
import net.crytec.api.smartInv.content.InventoryProvider;
import net.crytec.api.smartInv.content.SlotPos;
import net.crytec.api.util.F;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class ClanOwnerNoHome implements InventoryProvider {
	
	@Override
	public void init(Player player, InventoryContents contents) {
		Clan clan = Clan.ofMember(player).get();
		
		ItemStack placeholder = new ItemBuilder(clan.getMenuStyle().getGlass()).name(" ").build();
		
		contents.fillRow(0, ClickableItem.empty(placeholder));
		contents.fillRow(2, ClickableItem.empty(placeholder));
		
		contents.set(SlotPos.of(0, 4), ClickableItem.empty(new ItemBuilder(clan.getBanner()).name("§6" + clan.getName())
				.lore(" ")
				.lore("§eErfahrung: §f" + clan.getExperience())
				.lore("§eLevel: §f" + clan.getLevel())
				.lore(" ")
				.lore("§eOnline : §f*/*")
				.lore(" ")
				.lore("§ePrivatsphäre: §f" + clan.getPrivacy().getDisplayname())
				.build()));
		
		contents.set(SlotPos.of(1, 4), ClickableItem.of(new ItemBuilder(Material.BEACON).name("§2Clan Gebiet erstellen")
					.lore("§fBevor du deinen Clan verwalten kannst")
					.lore("§fmusst du erst deine Clan Basis gründen.")
					.build(), e -> {
					
					player.closeInventory();
					
					player.sendMessage(F.main("Clan", "Du befindest dich nun im Vorschaumodus. Klicke im Chat nun auf " + F.name("§a'Basis erstellen'") + " um die Basis "
								+ "an der angezeigten Position zu erstellen. Klicke auf " + F.name("§c'Abbrechen'") + " falls du deine Basis an einem anderen Ort "
								+ "erstellen möchtest. Verlässt du den Bereich wird die Erstellung ebenfalls abgebrochen."));
					
					player.sendMessage("");
					player.sendMessage(this.getBaseAcceptText(clan, player));
					
					ProtectedRegion region = ProtectionManager.get().createClanRegion(clan, player);	
					PreviewMode.get().addPlayer(player, region, clan);
						
		}));
	}

	@Override
	public void update(Player player, InventoryContents contents) { }
	
	
	private BaseComponent[] getBaseAcceptText(Clan clan, Player player) {
		ComponentBuilder builder = new ComponentBuilder("");

		TextComponent accept = new TextComponent("Basis erstellen");
		accept.setBold(true);
		accept.setColor(ChatColor.GREEN);
		accept.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/clanpreview accept " + clan.getIdentifier()));
		accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§fKlicke hier um deine Basis zu erstellen.")));

		TextComponent spacer = new TextComponent("      ");

		TextComponent cancel = new TextComponent("Abbrechen");
		cancel.setBold(true);
		cancel.setColor(ChatColor.RED);
		cancel.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/clanpreview cancel " + clan.getIdentifier()));
		cancel.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§fKlicke hier um die Vorschau zu beenden.")));

		builder.append(accept);
		builder.append(spacer);
		builder.append(cancel);

		return builder.create();
	}
	

}
