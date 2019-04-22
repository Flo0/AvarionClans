package com.avarioncraft.Clans.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.avarioncraft.Clans.ambient.animations.WarehouseAnim;
import com.avarioncraft.Clans.core.AvarionClans;
import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.data.clanIntern.clanUpgrades.warehouse.access.WarehouseAccesspoint;
import com.avarioncraft.Clans.gui.GUIs;
import com.avarioncraft.Clans.regions.protection.ClanProtectionUtil;

import net.crytec.api.itemstack.ItemBuilder;
import net.crytec.api.util.F;

public class WarehouseListener implements Listener{
	
	public WarehouseListener() {
		this.anim = new WarehouseAnim();
		Bukkit.getPluginManager().registerEvents(this, AvarionClans.getPlugin());
	}
	
	private final WarehouseAnim anim;
	
	@EventHandler
	public void clickChest(PlayerInteractEvent event) {
		if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if(event.getClickedBlock().getType() != Material.ENDER_CHEST) return;
		Location loc = event.getClickedBlock().getLocation();
		if(WarehouseAccesspoint.isWarehouse(loc)) {
			if(WarehouseAccesspoint.ofLoc(loc).getClan().getMembers().contains(event.getPlayer().getUniqueId())) {
				GUIs.ClanWarehouseOverview.open(event.getPlayer());
				event.setCancelled(true);
			}
		}
		
	}
	
	@EventHandler
	public void placeChest(BlockPlaceEvent event) {
		
		Block block = event.getBlockPlaced();
		
		if(block.getType() != Material.ENDER_CHEST) return;
		if(!event.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) return;
		if(!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) return;
		if(!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("§a§f§aLagerhaus Kiste")) return;
		if(!Clan.ofMember(event.getPlayer()).isPresent()) {
			event.getPlayer().sendMessage(F.main("Warenhaus", "Du musst einen Clan besitzen, um einen Zugangspunkt zu platzieren."));
			event.setCancelled(true);
			return;
		}
		Location loc = event.getBlockPlaced().getLocation().add(0.5, 0, 0.5);
		if(ClanProtectionUtil.isOnClanArea(event.getPlayer())) {
			new WarehouseAccesspoint(block.getLocation(), Clan.ofMember(event.getPlayer()).get());
			event.getPlayer().sendMessage(F.main("Warenhaus", "Zugangspunkt erstellt."));
			loc.getWorld().playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.4F, 1.4F);
		}else {
			event.getPlayer().sendMessage(F.main("Warenhaus", "Du musst auf deinem Clangebiet stehen."));
		}
		
	}
	
	@EventHandler
	public void breakChest(BlockBreakEvent event) {
		Block block = event.getBlock();
		if(block.getType() != Material.ENDER_CHEST) return;
		if(!WarehouseAccesspoint.isWarehouse(block.getLocation())) return;
		if(ClanProtectionUtil.isOnClanArea(event.getPlayer())) {
			block.setType(Material.AIR);
			anim.playBreakAnim(block.getLocation());
			block.getLocation().getWorld().dropItem(block.getLocation(), new ItemBuilder(Material.ENDER_CHEST)
					.name("§a§f§aLagerhaus Kiste")
					.build());
			WarehouseAccesspoint.ofLoc(block.getLocation()).onRemove();
			event.getPlayer().sendMessage(F.main("Warenhaus", "Zugangspunkt zerstört."));
			event.setCancelled(true);
		}else {
			event.getPlayer().sendMessage(F.main("Warenhaus", "Du musst auf deinem Clangebiet stehen."));
		}
		
	}
	
}