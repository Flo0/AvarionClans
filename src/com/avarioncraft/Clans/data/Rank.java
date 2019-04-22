package com.avarioncraft.Clans.data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import com.avarioncraft.Clans.util.enums.Permission;
import com.google.common.collect.Sets;

import lombok.Getter;
import lombok.Setter;

@SerializableAs("Rank")
public class Rank implements ConfigurationSerializable {
	
	public Rank(String displayName, boolean isDefault, boolean isOwner) {
		this.isDefault = isDefault;
		this.isOwner = isOwner;
		this.displayName = displayName;
		
		if(isOwner) {
			this.isDefault = false;
		}
	}
	
	private Rank(String displayName, boolean isDefault, boolean isOwner, Set<Permission> permissions) {
		this.isDefault = isDefault;
		this.isOwner = isOwner;
		this.displayName = displayName;
		
		if(isOwner) {
			this.isDefault = false;
		}
		
		this.permissions = permissions;
	}
	
	@Getter @Setter
	private String displayName;
	
	@Getter @Setter
	private boolean isDefault;
	
	@Getter
	private final boolean isOwner;
	
	@Getter @Setter
	private Material icon = Material.PAPER;
	
	@Getter
	private Set<Permission> permissions = Sets.newTreeSet();
	
	public void addPerm(Permission perm) {
		this.permissions.add(perm);
	}
	
	public boolean removePerm(Permission perm) {
		if(this.permissions.contains(perm)) {
			this.permissions.remove(perm);
			return true;
		}else {
			return false;
		}
	}
	
	public boolean hasPermission(Permission perm) {
		if (isOwner) return true;
		return this.permissions.contains(perm);
	}
	
	@Override
	public Map<String, Object> serialize() {
		LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();

		List<String> perms = this.permissions.stream().map(p -> p.toString()).collect(Collectors.toList());

		result.put("icon", icon.toString());
		result.put("permissions", perms);
		result.put("isOwner", this.isOwner);
		result.put("isDefault", this.isDefault);
		result.put("displayname", displayName);

		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	public static Rank deserialize(Map<String, Object> args) {
		
		String displayname = (String) args.get("displayname");
		boolean isDefault = (Boolean) args.get("isDefault");
		boolean isOwner = (Boolean) args.get("isOwner");
		List<String> perms = (List<String>) args.get("permissions");
		
		Set<Permission> permissions = perms.stream().map(s -> Permission.valueOf(s)).collect(Collectors.toSet());
		
		Rank rank = new Rank(displayname, isDefault, isOwner, permissions);
		return rank;
	}
	
}
