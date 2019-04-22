package com.avarioncraft.Clans.regions.protection;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.util.enums.ClanDirection;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.RegionOperationException;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.managers.storage.StorageException;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;

import net.crytec.api.util.F;

public class ProtectionManager {
	
	private static ProtectionManager instance = null;
	
	
	public static ProtectionManager get() {
		if (instance == null) {
			instance = new ProtectionManager();
		}
		return instance;
	}
	
	
	
	
	public ProtectedRegion createClanRegion(Clan clan, Player player) {
		
		Chunk middle = player.getChunk();
		int CX = middle.getX() * 16;
		int CZ = middle.getZ() * 16;
		Location high = new Location(player.getWorld(), CX + 15, 256, CZ + 15);
		Location low = new Location(player.getWorld(), CX, 0, CZ);
		org.bukkit.util.Vector adder = new org.bukkit.util.Vector(32, 0, 32);
		high.add(adder);
		low.subtract(adder);
		
		return this.createClanRegion(clan, low, high);
	}
	
	public ProtectedRegion createClanRegion(Clan clan, Location minPoint, Location maxPoint) {
		
		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		RegionManager manager = container.get(BukkitAdapter.adapt(minPoint.getWorld()));
		
		Player owner = Bukkit.getPlayer(clan.getOwner());
		
		if (owner == null) {
			return null;
		}
		
		LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(owner);
		
		Vector3 min = BukkitAdapter.adapt(minPoint).toVector();
		Vector3 max = BukkitAdapter.adapt(maxPoint).toVector();
		
		DefaultDomain defaultDomain = new DefaultDomain();
		ProtectedRegion region = new ProtectedCuboidRegion("clan-" + clan.getIdentifier(), min.toBlockPoint(), max.toBlockPoint());
		
		if (manager.overlapsUnownedRegion(region, localPlayer)) {
			owner.sendMessage(F.error("Diese Region würde mit einer anderen überlappen. Bitte halte Abstand zu anderen Clans."));
			return null;
		}
		
		defaultDomain.addPlayer(localPlayer);
		region.setOwners(defaultDomain);
		region.setPriority(10);
		
		manager.addRegion(region);
		
		try {
			manager.saveChanges();
			owner.sendMessage(F.main("Clan", "Du hast deine Clan Region erstellt."));
			return region;
		} catch (StorageException e) {
			owner.sendMessage(F.error("Es ist etwas schief gelaufen, bitte wende dich an einen Server Administrator."));
			e.printStackTrace();
			return null;
		}
	}
	
	
	public boolean extendClanRegion(Clan clan, int chunkAmount, ClanDirection direction) {
		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		
		//Todo: Bessere World Getter Methode
		RegionManager manager = container.get(BukkitAdapter.adapt(Bukkit.getWorld("hauptwelt")));
		
		Player owner = Bukkit.getPlayer(clan.getOwner());
		
		if (owner == null) {
			return false;
		}
		
		ProtectedRegion region = manager.getRegion("clan-" + clan.getIdentifier());
		DefaultDomain members = region.getMembers();
		DefaultDomain owners = region.getOwners();
		
		Region newCuboid = new CuboidRegion(region.getMinimumPoint(), region.getMaximumPoint());
		
		try {
		newCuboid.expand(direction.getVector().multiply(chunkAmount * 16).toBlockPoint());
		} catch (RegionOperationException ex) {
			ex.printStackTrace();
			return false;
		}
		ProtectedRegion newRegion = new ProtectedCuboidRegion(region.getId(), newCuboid.getMinimumPoint(), newCuboid.getMaximumPoint());
		
		
		int newChunks = newCuboid.getChunks().size();
		
		// Chunk Anzahl prüfen
		if (newChunks > clan.getFlag().getMaxChunks()) {
			return false;
		}
		
		newRegion.setMembers(members);
		newRegion.setOwners(owners);
		newRegion.setPriority(region.getPriority());
		newRegion.setDirty(true);
		
		// Falls das nicht funktioniert, region erst entfernen.
		manager.addRegion(newRegion);
		
		try {
			manager.saveChanges();
			return true;
		} catch (StorageException e) {
			e.printStackTrace();
			return false;
		}
	}
}