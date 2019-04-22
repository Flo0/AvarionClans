package com.avarioncraft.Clans.data.message;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.events.ClanMessageSendEvent;
import com.google.common.collect.Maps;

public class MessageBuilder {
	
	private static MessageBuilder instance = null;
	
	protected MessageBuilder() {
		
	}
	
	public static MessageBuilder get() {
		if (instance == null) {
			instance = new MessageBuilder();
		}
		return instance;
	}
	
	public Map<UUID, Set<ClanMessage>> PlayerMessages = Maps.newHashMap();
	
	public Map<Clan, Set<ClanMessage>> ClanMessages = Maps.newHashMap();
	
	public void sendClanRequest(Player player, Clan clan) {
		
	}
	
	public void newPlayer(Player player) {
		this.PlayerMessages.put(player.getUniqueId(), new HashSet<ClanMessage>());
	}
	
	public void addMessage(Player sendPlayer ,Player player, String Title, String Lores, String sender) {
		UUID ID = player.getUniqueId();
		addMessage(sendPlayer ,ID, Title, Lores, sender);
	}
	
	public void addMessage(Player sendPlayer ,UUID ID, String Title, String Lores, String sender) {
		if(PlayerMessages.containsKey(ID)) {
			addMessage(sendPlayer, ID, new ClanMessage(Title, Lores, System.currentTimeMillis(), sender));
		}
	}
	
	public void addMessage(Player sendPlayer, UUID ID, ClanMessage message) {
		if(PlayerMessages.containsKey(ID)) {
			ClanMessageSendEvent event = new ClanMessageSendEvent(message, sendPlayer, Clan.ofMember(sendPlayer).orElse(null));
			Bukkit.getPluginManager().callEvent(event);
			PlayerMessages.get(ID).add(message);
		}
	}
	
	public void addClanResponse(Player reciever, Clan clan, boolean accepted) {
		this.addClanResponse(reciever.getUniqueId(), clan, accepted);
	}
	
	public void addClanResponse(UUID ID, Clan clan, boolean accepted) {
		this.PlayerMessages.get(ID).add(new ClanResponse(clan, accepted));
	}
	
	public void addClanInvitation(Player reciever, Clan clan) {
		this.addClanInvitation(reciever.getUniqueId(), clan);
	}
	
	public void addClanInvitation(UUID ID, Clan clan) {
		this.PlayerMessages.get(ID).add(new ClanInvitation(clan));
	}
	
}
