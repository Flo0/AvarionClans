package com.avarioncraft.Clans.data.message.manager;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.avarioncraft.Clans.core.AvarionClans;
import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.data.message.ClanMessage;
import com.avarioncraft.Clans.data.message.MessageBuilder;
import com.avarioncraft.Clans.util.enums.Permission;
import com.google.common.collect.Maps;

import net.crytec.api.util.ChatStringInput;
import net.crytec.api.util.F;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class ClanMessageManager{
	
	private String Title;
	private String Lores;
	private ClanMessage message;
	private Player sender;
	
	public ClanMessageManager(Player player) {
		this.sender = player;
		this.startBuilder(player, false);
	}
	
	public static Map<Player, ClanMessageManager> openManager = Maps.newHashMap();
	
	public ClanMessageManager() {}
	
	@SuppressWarnings("deprecation")
	public void sendPlayer(Player player) {
		player.sendMessage(F.main("Nachricht", "Den Namen des Spielers eingeben. (Bis zu 3 Spieler mit Komma trennen)"));
		ChatStringInput.addPlayer(player, input -> {
			String M = input;
			Bukkit.getScheduler().runTask(AvarionClans.getPlugin(), ()->{
				M.replaceAll(" ", "");
				String[] names = M.split(",");
				for(String name : names) {
					OfflinePlayer op = Bukkit.getOfflinePlayer(name);
					if (op == null) {
					}else {
						UUID id = op.getUniqueId();
						MessageBuilder.get().addMessage(sender, id, this.message);
					}
				}
			});
		});
	}
	
	public void broadcast(Player player) {
		Clan clan = Clan.ofMember(player).get();
		int count = 0;
		
		if(!Clan.ofMember(player).get().getRank(player).hasPermission(Permission.BROADCAST)) {
			player.sendMessage(F.error("Nicht genügend Rechte im Clan."));
			return;
		}
		for(UUID ID : clan.getMembers()) {
			count ++;
			MessageBuilder.get().addMessage(this.sender, ID, this.message);
		}
		player.sendMessage(F.main("Nachricht", "Die Nachricht wurde an alle (" + count + ") Mitglieder gesendet."));
		
	}
	
	private void sendMessage(Player player) {
		BaseComponent cancel = new TextComponent("§e>>> §f[Abbrechen]");
		cancel.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("Klicke zum Abbrechen")));
		cancel.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/hgaertg45q"));
		BaseComponent send = new TextComponent("§e>>> §f[An einen Spieler senden]");
		send.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("Klicke -> Namen eingeben")));
		send.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/rhj9843"));
		BaseComponent broadcast = new TextComponent("§e>>> §f[An alle Clan Mitglieder senden]");
		broadcast.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/g54wgath6"));
		broadcast.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("Klicke zum absenden.")));
		player.sendMessage("");
		for(int n = 1; n <= 10 ; n++) {
			player.sendMessage("");
		}

		player.sendMessage("");
		player.sendMessage("");
		player.sendMessage("§e----- [Klicke auf eine Sendeoption] -----");
		player.sendMessage("");
		player.sendMessage(send);
		player.sendMessage("");
		player.sendMessage(broadcast);
		player.sendMessage("");
		player.sendMessage(cancel);
		player.sendMessage("");
	}
	
	public void startBuilder(Player player, boolean isBig) {
		if(isBig) {
			player.sendMessage(F.main("Titel", "Dieser Titel war zu groß. [Max. 20 Zeichen]"));
			ChatStringInput.addPlayer(player, input -> {
				if(input.length() > 20) {
					this.startBuilder(player, true);
				}else {
					this.Title = input;
					this.messageBuilder(player);
				}
			});
		}else {
			openManager.put(player, this);
			player.sendMessage(F.main("Titel", "Gib einen Titel ein. [Max. 20 Zeichen]"));
			ChatStringInput.addPlayer(player, input -> {
				if(input.length() > 20) {
					this.startBuilder(player, true);
				}else {
					this.Title = input;
					this.messageBuilder(player);
				}
			});
		}
	}
	
	private void messageBuilder(Player player) {
		player.sendMessage(F.main("Nachricht", "Bitte eine Nachricht eingeben. % für nächste Zeile."));
		ChatStringInput.addPlayer(player, input -> {
			this.Lores = input;
			this.message = new ClanMessage(this.Title, this.Lores, System.currentTimeMillis(), "§o" + player.getName());
			this.sendMessage(player);
		});

	}
	
	
}
