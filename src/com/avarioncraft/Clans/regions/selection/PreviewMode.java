package com.avarioncraft.Clans.regions.selection;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.avarioncraft.Clans.core.AvarionClans;
import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.events.ClanBaseCreatedEvent;
import com.avarioncraft.Clans.regions.packet.PreviewThread;
import com.google.common.collect.Maps;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;

import net.crytec.api.events.RegionLeftEvent;

public class PreviewMode implements Listener {
	
	private static PreviewMode instance = null;
	
	protected PreviewMode() {
		container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		Bukkit.getPluginManager().registerEvents(this, AvarionClans.getPlugin());
	}
	
	public static PreviewMode get() {
		if (instance == null) {
			instance = new PreviewMode();
		}
		return instance;
	}
	
	private HashMap<Player, ProtectedRegion> inPreview = Maps.newHashMap();
	private final RegionContainer container;
	
	public Set<Player> getPlayersInPreview() {
		return inPreview.keySet();
	}
	
	public void addPlayer(Player player, ProtectedRegion region, Clan clan) {
		double X = player.getChunk().getX() * 16;
		double Y = player.getLocation().getY();
		double Z = player.getChunk().getZ() * 16;
		clan.setBaseLocation(player.getLocation());
		player.teleport(new Location(player.getWorld(), X, Y, Z));
		Bukkit.getScheduler().runTaskAsynchronously(AvarionClans.getPlugin(), new PreviewThread(player, clan.getArchitecture()));
		this.inPreview.put(player, region);
	}
	
	public void removePlayer(Player player, boolean place) {
		this.inPreview.remove(player);
		if (!place) {
			Clan clan = Clan.ofMember(player).get();
			clan.setBaseLocation(null);
		}
	}
	
	public boolean isInPreview(Player player) {
		return this.inPreview.containsKey(player);
	}
	
	
	@EventHandler
	public void denyCommandSend(PlayerCommandPreprocessEvent event) {
		if (!isInPreview(event.getPlayer())) return;
		if (event.getMessage().startsWith("/clanpreview")) {
			event.setCancelled(true);
			String[] args = event.getMessage().split(" ");
			if (args[1].equals("accept")) {
				this.removePlayer(event.getPlayer(), true);
				
				Clan clan = Clan.ofMember(event.getPlayer()).get();
				clan.constructHomeBuilding(clan.getBaseLocation());
				
				ClanBaseCreatedEvent eventCall = new ClanBaseCreatedEvent(clan, event.getPlayer());
				Bukkit.getPluginManager().callEvent(eventCall);
				
				event.getPlayer().sendMessage("Deine Clan Basis wurde erstellt!");
				this.removePlayer(event.getPlayer(), true);
			} else {
				this.removePlayer(event.getPlayer(), false);
			}
		}
	}
	
	
	@EventHandler
	public void onRegionLeave(RegionLeftEvent event) {
		Player player = event.getPlayer();
		if (inPreview.containsKey(player) && event.getRegion().getId().equals(inPreview.get(player).getId())) {
			RegionManager manager = container.get(BukkitAdapter.adapt(player.getWorld()));
			manager.removeRegion(inPreview.get(player).getId());
			this.removePlayer(player, false);
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (inPreview.containsKey(player)) {
			RegionManager manager = container.get(BukkitAdapter.adapt(player.getWorld()));
			manager.removeRegion(inPreview.get(player).getId());
			this.removePlayer(player, false);
		}
		
	}
}
