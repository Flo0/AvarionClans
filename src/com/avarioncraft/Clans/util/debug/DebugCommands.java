package com.avarioncraft.Clans.util.debug;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Nameable;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.avarioncraft.Clans.core.AvarionClans;
import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.manager.DataManager;
import com.avarioncraft.Clans.regions.protection.ProtectionManager;
import com.sk89q.worldedit.UnknownDirectionException;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.math.BlockVector3;

import net.crytec.api.devin.commands.Command;
import net.crytec.api.devin.commands.CommandResult;
import net.crytec.api.devin.commands.Commandable;
import net.crytec.api.itemstack.ItemBuilder;
import net.crytec.api.util.F;
import net.crytec.api.util.UtilLoc;

public class DebugCommands extends DebugMain implements Commandable{

	public DebugCommands() {
		super();
		super.registerCommand(this);
	}
	
	//TODO remove
	@Command(struct = "fatitem", desc = "Debug Command", params = {"DebugValue"})
	public CommandResult fatitem(Player player) {
		StupidItemGenerator gen = new StupidItemGenerator();
		gen.addStupidItem(player);
		return CommandResult.success();
	}
	
	@Command(struct = "clanchest", desc = "Debug Command", params = {"DebugValue"})
	public CommandResult clanchest(Player player, int chestID) {
		
		Clan.ofMember(player).get().getWarehouse().getStock().getChest(chestID).open(player);
		
		return CommandResult.success();
	}
	
	@Command(struct = "clanchest get", desc = "Debug Command", params = {"DebugValue"})
	public CommandResult clanchestget(Player player) {
		
		player.getInventory().addItem(new ItemBuilder(Material.ENDER_CHEST).name("§a§f§aLagerhaus Kiste").build());
		
		return CommandResult.success();
	}
	
	//TODO remove
	@Command(struct = "fatchest", desc = "Debug Command", params = {"DebugValue"})
	public CommandResult fatchest(Player player, int amount) {
		StupidItemGenerator gen = new StupidItemGenerator();
		gen.addFilledBox(amount, player);
		return CommandResult.success();
	}
	
	// Main Debug Command
	@Command(struct = "clandebug", desc = "Debug Command", params = {"DebugValue"})
	public CommandResult Debug(Player player, String value) {
		this.getCurrentClanID(player, value);
		return CommandResult.success();
	}
	
	
	@Command(struct = "clandebug save", desc = "Debug Command", params = {"DebugValue"})
	public CommandResult DebugSave(Player player) {
		AvarionClans.getPlugin().getStorage().saveClan(Clan.ofMember(player).get());
		player.sendMessage(F.main("§4Clan", "Dein Clan wurde gespeichert"));
		return CommandResult.success();
	}
		
	@Command(struct = "clandebug points add", desc = "stuff", params = {"DebugValue"})
	public CommandResult pointsAdd(Player player, int points) {
		Clan.ofMember(player).get().addClanPoints(points);
		return CommandResult.success();
	}
	
	@Command(struct = "clandebug createRegion", desc = "Debug Command")
	public CommandResult createRegionDebug(Player player) {
		
		Clan clan = new Clan(player.getUniqueId(), -999, System.currentTimeMillis(), null);
		ProtectionManager.get().createClanRegion(clan, player);
		
		player.sendMessage(F.main("Clan|DEV", "Clan Region wurde erstellt"));
		
		return CommandResult.success();
	}
	
	@Command(struct = "clandebug direction", desc = "Debug Command")
	public CommandResult Debug(Player player) {
		WorldEditPlugin we = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
		BlockFace facing = UtilLoc.yawToFace(player.getLocation().getYaw(), false);
		try {
			BlockVector3 dir = we.getWorldEdit().getDirection(we.wrapPlayer(player), facing.toString());
			player.sendMessage(dir.toString());
		} catch (UnknownDirectionException e) {
			e.printStackTrace();
		}
		
		
		return CommandResult.success();
	}
	
	@Command(struct = "clandebug loadFrames", desc = "Debug Command")
	public CommandResult loadFrames(Player player) {

		DataManager.get().loadRawFrames();
		
		return CommandResult.success();
	}
	
	@Command(struct = "clandebug saveFrames", desc = "Debug Command")
	public CommandResult saveFrames(Player player) throws IOException {

		DataManager.get().saveRawFrames();
		
		return CommandResult.success();
	}
	
	@Command(struct = "clandebug saveItem", desc = "Debug Command")
	public CommandResult saveItem(Player player) throws IOException {
		File file = new File (AvarionClans.getPlugin().getDataFolder() + File.separator + "Item.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("Item", player.getInventory().getItemInMainHand());
		config.save(file);
		return CommandResult.success();
	}
	
	@Command(struct = "clandebug loadItem", desc = "Debug Command")
	public CommandResult loadItem(Player player) throws IOException {
		File file = new File (AvarionClans.getPlugin().getDataFolder() + File.separator + "Item.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		player.getInventory().addItem(config.getSerializable("Item", ItemStack.class));
		return CommandResult.success();
	}
		
	@Command(struct = "clandebug clanchest", desc = "Debug Command")
	public CommandResult spawnChest(Player player) {

		player.getLocation().getBlock().setType(Material.CHEST);
		BlockState state = player.getLocation().getBlock().getState();
		Nameable chest = (Nameable) state;
		chest.setCustomName("Clan Resourcen");
		state.update(true, true);
		
		return CommandResult.success();
	}
	

	// Simple Debug Methods
	
	/**
	 * Gets Clan ID of commandSender
	 * @param player sender
	 * @param value command
	 */
	private void getCurrentClanID(Player player, String value) {
		if(value.equalsIgnoreCase("clanid")) {
			System.out.println("ClanIdentifier of " + player.getName() + " is " + Clan.ofMember(player).get().getIdentifier());
		}
	}
	
}
