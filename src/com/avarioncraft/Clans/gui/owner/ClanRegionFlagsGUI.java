package com.avarioncraft.Clans.gui.owner;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.gui.GUIs;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import net.crytec.api.itemstack.ItemBuilder;
import net.crytec.api.smartInv.ClickableItem;
import net.crytec.api.smartInv.content.InventoryContents;
import net.crytec.api.smartInv.content.InventoryProvider;
import net.crytec.api.smartInv.content.SlotPos;
import net.crytec.api.util.F;

public class ClanRegionFlagsGUI implements InventoryProvider {

	@Override
	public void init(Player player, InventoryContents contents) {
		
		Clan clan = Clan.ofMember(player).get();
		RegionManager manager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(clan.getBaseLocation().getWorld()));
		ProtectedRegion region = manager.getRegion("clan-" + clan.getIdentifier());
		
		contents.fillRow(0, ClickableItem.empty(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).name(" ").build()));
		contents.fillRow(3, ClickableItem.empty(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).name(" ").build()));
		
		contents.set(SlotPos.of(0, 0), ClickableItem.of(new ItemBuilder(Material.REDSTONE_BLOCK).name("§fZurück zur Übersicht").build(), e -> contents.inventory().getParent().get().open(player)));

		ItemStack leave_decay = new ItemBuilder(Material.JUNGLE_LEAVES).name(getFlagState(Flags.LEAF_DECAY, region) + "Leave Decay").build();
		ItemStack ice_melt = new ItemBuilder(Material.ICE).name(getFlagState(Flags.ICE_MELT, region) + "Ice Melt").build();
		ItemStack ice_form = new ItemBuilder(Material.PACKED_ICE).name(getFlagState(Flags.ICE_FORM, region) + "Ice Form").build();
		ItemStack mycelium_spread = new ItemBuilder(Material.MYCELIUM).name(getFlagState(Flags.MYCELIUM_SPREAD, region) + "Mycelium Spread").build();
		ItemStack vine_growth = new ItemBuilder(Material.VINE).name(getFlagState(Flags.VINE_GROWTH, region) + "Vine Growth").build();
		ItemStack grass_spread = new ItemBuilder(Material.VINE).name(getFlagState(Flags.GRASS_SPREAD, region) + "Grass Spread").build();
		
		contents.set(SlotPos.of(1, 2), ClickableItem.of(leave_decay, e -> this.toggleState(player, Flags.LEAF_DECAY, region, e.getClick())));
		contents.set(SlotPos.of(1, 4), ClickableItem.of(ice_melt, e -> this.toggleState(player, Flags.ICE_MELT, region, e.getClick())));
		contents.set(SlotPos.of(1, 6), ClickableItem.of(ice_form, e -> this.toggleState(player, Flags.ICE_FORM, region, e.getClick())));
		contents.set(SlotPos.of(1, 8), ClickableItem.of(mycelium_spread, e -> this.toggleState(player, Flags.MYCELIUM_SPREAD, region, e.getClick())));
		contents.set(SlotPos.of(1, 1), ClickableItem.of(vine_growth, e -> this.toggleState(player, Flags.VINE_GROWTH, region, e.getClick())));
		contents.set(SlotPos.of(1, 3), ClickableItem.of(grass_spread, e -> this.toggleState(player, Flags.GRASS_SPREAD, region, e.getClick())));
	}

	@Override
	public void update(Player player, InventoryContents contents) {
		
	}
	
	private void toggleState(Player player, StateFlag flag, ProtectedRegion region, ClickType type) {
		if (type == ClickType.RIGHT) {
			region.setFlag(flag, null);
			player.sendMessage(F.main("Clan", "Flag Status wurde zurückgesetzt!"));
			GUIs.OWNER_FLAG_SETTINGS.open(player);
		} else {
			if (region.getFlags().containsKey(flag)) {
				if (region.getFlag(flag) == State.DENY) {
					region.setFlag(flag, State.ALLOW);
					player.sendMessage(F.main("Clan", F.name(flag.getName()) + " ist nun erlaubt."));
					GUIs.OWNER_FLAG_SETTINGS.open(player);
				} else {
					region.setFlag(flag, State.DENY);
					player.sendMessage(F.main("Clan", F.name(flag.getName()) + " ist nun verboten."));
					GUIs.OWNER_FLAG_SETTINGS.open(player);
				}
			} else {
				region.setFlag(flag, State.ALLOW);
				player.sendMessage(F.main("Clan", F.name(flag.getName()) + " ist nun erlaubt."));
				GUIs.OWNER_FLAG_SETTINGS.open(player);
			}
		}
	}
	
	private ChatColor getFlagState(StateFlag flag, ProtectedRegion region) {

		if (region.getFlags().containsKey(flag)) {
			if (region.getFlag(flag) == StateFlag.State.DENY) {
				return ChatColor.RED;
			} else {
				return ChatColor.GREEN;
			}
		} else {
			return ChatColor.GRAY;
		}
	}
}