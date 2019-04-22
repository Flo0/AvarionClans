package com.avarioncraft.Clans.data;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import com.avarioncraft.Clans.util.enums.Permission;
import com.google.common.collect.Maps;

import lombok.Getter;

@SerializableAs("RankContainer")
public class RankContainer implements ConfigurationSerializable {
	
	public RankContainer() {
		this.addRank("Standard", true, false);
		this.addRank("Owner", false, true);
		this.defaultRank = this.rankMap.get("Standard");
		this.ownerRank = this.rankMap.get("Owner");
	}
	
	public Map<String, Rank> rankMap = Maps.newLinkedHashMap();
	
	@Getter
	public Rank defaultRank;
	
	@Getter
	public Rank ownerRank;
	
	public void setDefault(Rank rank) {
		this.defaultRank.setDefault(false);
		this.defaultRank = rank;
		this.defaultRank.setDefault(true);
	}
	
	public void addPerm(String name, Permission perm) {
		this.rankMap.get(name).addPerm(perm);
	}
	
	public boolean removePerm(String name, Permission perm) {
		return this.rankMap.get(name).removePerm(perm);
	}
	
	public Rank getRank(String rank) {
		return this.rankMap.getOrDefault(rank, defaultRank);
	}
	
	public boolean addRank(String name, boolean isDefautlt, boolean isOwner) {
		if(this.rankMap.containsKey(name) || this.rankMap.size() >= 18) {
			return false;
		}else {
			this.rankMap.put(name, new Rank(name, isDefautlt, isOwner));
			return true;
		}
	}
	
	public int getTotalRanks() {
		return this.rankMap.size();
	}
	
	public Collection<Rank> getAllRanks() {
		return this.rankMap.values();
	}
	
	public boolean removeRank(String name) {
		if(!this.rankMap.containsKey(name) || this.rankMap.size() <= 0) {
			return false;
		}else {
			this.rankMap.remove(name);
			return true;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static RankContainer deserialize(Map<String, Object> args) {
		
		RankContainer ranks = new RankContainer();
		ranks.rankMap = (Map<String, Rank>) args.get("RankMap");
		ranks.setDefault((Rank) args.get("Standard") );
		ranks.ownerRank = (Rank) args.get("Owner");
		return ranks;
	}

	@Override
	public Map<String, Object> serialize() {
		LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
		
		result.put("Standard", this.getDefaultRank());
		result.put("Owner", this.getOwnerRank());
		this.rankMap.remove("Standard");
		this.rankMap.remove("Owner");
		result.put("RankMap", this.rankMap);
		return result;
	}
	
	
}
