package com.avarioncraft.Clans.data;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.avarioncraft.Clans.administration.schematic.AvarionSchematic;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.armory.Armory;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.bank.Bank;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.barracks.Barracks;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.flag.Flag;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.gather.GatherJobs;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.producer.ProducerJobs;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.resource.ClanResources;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.temple.Temple;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.trader.Trader;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.warehouse.Warehouse;
import com.avarioncraft.Clans.data.message.ClanMessage;
import com.avarioncraft.Clans.data.message.MessageBuilder;
import com.avarioncraft.Clans.events.ClanCreateEvent;
import com.avarioncraft.Clans.events.ClanMemberAddEvent;
import com.avarioncraft.Clans.util.enums.ArchitectureStyle;
import com.avarioncraft.Clans.util.enums.ClanMenuStyle;
import com.avarioncraft.Clans.util.enums.ClanPrivacy;
import com.google.common.collect.Maps;

import lombok.Getter;

@SerializableAs("Clan")
public class Clan implements ConfigurationSerializable{
	
	/**
	 * Constructor für das Clan Objekt
	 */
	public Clan(UUID owner, int clanID, long creationDate, String name) {
		this.owner = owner;
		this.identifier = clanID;
		this.creationDate = creationDate;
		this.upgrades = new Upgrades(this);
		this.name = name;
		MessageBuilder.get().ClanMessages.put(this, new HashSet<ClanMessage>());
		
		this.members.put(owner, this.getRanks().getOwnerRank());
		Clan.userClanMap.put(owner, this);
		this.getUpgrades().getBank().levelUP(false);
		this.getUpgrades().getClanResources().levelUP(false);
		
	}
	
	private final int identifier;
	private final UUID owner;
	private final long creationDate;
	
	//Static Variablen
	public static Map<UUID, Clan> userClanMap = Maps.newHashMap();
	public static Map<Integer, Clan> idClanMap = Maps.newHashMap();
	public static int clanCounter = 0;
	
	
	private Location homeLocation;
	
	
	private ItemStack banner = new ItemStack(Material.BLACK_BANNER);
	private String name;
	private ClanMenuStyle menuStyle = ClanMenuStyle.GRAY;
	private ArchitectureStyle architecture = ArchitectureStyle.NORD;
	private ClanPrivacy privacy = ClanPrivacy.PRIVATE;
	
	
	
	private double experience;
	private int level;
//	private int points; // Ka was die Points sind?
	private int clanPoints; // Benötigt um die Upgrades zu upgraden
	
	
	
	private Set<UUID> bannedPlayers;
	private Map<UUID, Rank> members = Maps.newHashMap();
	
//	private Map<QuestDifficulty, Integer> completedDailys = Maps.newEnumMap(QuestDifficulty.class); // Wird aktuell nicht benutzt.
	
	
//	private Set<Long> claims = Sets.newHashSet();
//	private Set<Long> pointsOfInterest = Sets.newHashSet(); // Chunk Long Koordinaten in denen etwas 'wichtiges' steht, oder so.
	
	
	@Getter
	private RankContainer ranks = new RankContainer();
	
	@Getter
	private Upgrades upgrades;
	
	
	
	
	//Static Mehtoden
	public static Optional<Clan> ofMember(Player player) {
		return ofMember(player.getUniqueId());
	}
	//TODO Beim erstellen hier rein
	public static Optional<Clan> ofMember(UUID uuid) {
		return Optional.ofNullable(userClanMap.get(uuid));
	}
	//TODO Beim erstellen hier rein
	public static Optional<Clan> ofID(int id) {
		return Optional.ofNullable(idClanMap.get(id));
	}
	
	public static Collection<Clan> getAllClans(ClanPrivacy privacy) {
		return idClanMap.values().stream().filter(c -> (c.getPrivacy() == privacy)).collect(Collectors.toSet());
	}
	
	public static Clan create(UUID owner, String name) {
		if (!name.matches("^[a-zA-Z0-9]+$")) {
			System.out.println("Regex failed");
			return null;
		}
		if (idClanMap.values().stream().filter(clan -> clan.getName().equalsIgnoreCase(name)).findFirst().isPresent()) {
			System.out.println("Name already exist");
			return null;
		}
		
		
		Clan clan = new Clan(owner, (clanCounter + 1), System.currentTimeMillis(), name);
		clan.setRank(owner, clan.getRanks().getOwnerRank());
		
		// Call create Event
		ClanCreateEvent event = new ClanCreateEvent(clan, Bukkit.getPlayer(owner));
		Bukkit.getPluginManager().callEvent(event);
		
		Clan.clanCounter++;
		return clan;
	}
	
	
	
	public void setRank(UUID member, Rank rank) {
		this.members.put(member, rank);
	}
	
	
	public String getName() {
		return this.name;
	}
	
	public ClanMenuStyle getMenuStyle() {
		return this.menuStyle;
	}
	
	public ItemStack getBanner() {
		return this.banner.clone();
	}
	
	public double getExperience() {
		return this.experience;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public ClanPrivacy getPrivacy() {
		return this.privacy;
	}
	
	public int getIdentifier() {
		return this.identifier;
	}
	
	public void setMenuStyle(ClanMenuStyle style) {
		this.menuStyle = style;
	}
	
	public void setPrivacy(ClanPrivacy privacy) {
		this.privacy = privacy;
	}
	
	public UUID getOwner() {
		return this.owner;
	}
	
	public Location getBaseLocation() {
		return this.homeLocation;
	}
	
	public ArchitectureStyle getArchitecture() {
		return this.architecture;
	}
	
	public int getClanPoints() {
		return this.clanPoints;
	}
	

	public void addMember(UUID uuid) {
		ClanMemberAddEvent event = new ClanMemberAddEvent(this, uuid, this.getRanks().getDefaultRank());
		if (event.isCancelled()) {
			return;
		}

		Clan.userClanMap.put(uuid, this);
		this.members.put(uuid, this.getRanks().getDefaultRank());
	}

	
	
	/**
	 * Set mit allen Mitgliedern des Clans.
	 * @return
	 */
	public Set<UUID> getMembers() {
		return members.keySet();
	}
	
	public List<Player> getOnlineMembers() {
		return members.keySet().stream().filter(id -> Bukkit.getPlayer(id) != null).map(id -> Bukkit.getPlayer(id)).collect(Collectors.toList());
	}
	
	public boolean hasHomeLocation() {
		return (homeLocation != null);
	}
	
	public void setBaseLocation(Location location) {
		this.homeLocation = location;
	}
			

	public boolean isBanned(UUID uuid) {
		return this.bannedPlayers.contains(uuid);
	}
	
	public Rank getRank(Player player) {
		return this.members.get(player.getUniqueId());
	}
	
	public Rank getRank(UUID uuid) {
		return this.members.get(uuid);
	}
	

	//TODO Spieler aus dem Clan volständig entfernen.
	public void banPlayer(UUID uuid) {
		this.bannedPlayers.add(uuid);
	}

	
	/**
	 * Addet eine Anzahl von ClanPunkten
	 * @param points die Anzahl
	 */
	public void addClanPoints(int points) {
		this.clanPoints += points;
	}
	/**
	 * Löscht eine Anzahl von ClanPunkten
	 * @param points die Anzahl
	 */
	public void removeClanPoints(int points) {
		this.clanPoints -= points;
	}
	
	/**
	 * Hebt einen Ban für einen Spieler auf.
	 * @param uuid die UUID des zu entbannenden Spielers.
	 */
	public void unbanPlayer(UUID uuid) {
		this.bannedPlayers.remove(uuid);
	}
	
	/**
	 * Erstellt ein Clangebäude auf dem Clangebiet.
	 * Im angegebenen ClanStil.
	 */
	public void constructHomeBuilding(Location location) {
		new AvarionSchematic(location, this.architecture.getStyleID()).build();
	}
	
	public Armory getArmory() {
		return this.getUpgrades().getArmory();
	}
	
	public Bank getBank() {
		return this.getUpgrades().getBank();
	}
	
	public Barracks getBarracks() {
		return this.getUpgrades().getBarracks();
	}
	
	public ClanResources getClanResources() {
		return this.getUpgrades().getClanResources();
	}
	
	public Flag getFlag() {
		return this.getUpgrades().getFlag();
	}
	
	public GatherJobs getGatherJobs() {
		return this.getUpgrades().getGatherJobs();
	}
	
	public ProducerJobs getProducerJobs() {
		return this.getUpgrades().getProducerJobs();
	}
	
	public Temple getTemple() {
		return this.getUpgrades().getTemple();
	}
	
	public Trader getTrader() {
		return this.getUpgrades().getTrader();
	}
	
	public Warehouse getWarehouse() {
		return this.getUpgrades().getWarehouse();
	}
	
	
	/*
	 * 
	 * Flatfile Storage
	 * 
	 */
	
	
	private void loadInternalData(Map<String, Object> data) {
		
		this.experience = (double) data.get("Experience");
		this.level = (int) data.get("Level");
		this.homeLocation = (Location) data.get("BaseLocation");
		this.ranks = (RankContainer) data.get("RankContainer");
		this.menuStyle = ClanMenuStyle.valueOf( (String) data.getOrDefault("MenuStyle", ClanMenuStyle.GRAY.toString()));
		this.privacy = ClanPrivacy.valueOf( (String) data.getOrDefault("Privacy", ClanPrivacy.PUBLIC.toString()));
		
		this.members.put( this.getOwner() , this.ranks.getOwnerRank());
		
		// Load static Clan data after successfull object creation.
		Clan.userClanMap.put(this.getOwner(), this);
		Clan.idClanMap.put(this.getIdentifier(), this);
	}
	
	public static Clan deserialize(Map<String, Object> args) {
		Clan clan = new Clan( UUID.fromString( (String) args.get("OwnerID")), 
				(int) args.get("ClanID"),
				(long) args.get("CreationDate"),
				(String) args.get("ClanName"));
		clan.loadInternalData(args);
		
		return clan;
	}
	
	@Override
	public Map<String, Object> serialize() {
		
		Map<String, String> usermap = Maps.newHashMap();
		
		for (UUID uuid : this.members.keySet()) {
			usermap.put(uuid.toString(), this.getRank(uuid).getDisplayName());
		}
		
		
		Map<String, Object> args = Maps.newHashMap();
		args.put("OwnerID", this.owner.toString());
		args.put("ClanID", this.identifier);
		args.put("CreationDate", this.creationDate);
		args.put("ClanName", this.name);
		args.put("RankContainer", this.getRanks());
		args.put("Level", this.getLevel());
		args.put("Experience", this.getExperience());
		args.put("BaseLocation", this.getBaseLocation());
		args.put("Members", usermap);
		args.put("MenuStyle", this.getMenuStyle().toString());
		args.put("Privacy", this.getPrivacy().toString());
		return args;
	}
	
	
}