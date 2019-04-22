package com.avarioncraft.Clans.commands;

import org.bukkit.entity.Player;

import com.avarioncraft.Clans.administration.schematic.AvarionSchematic;
import com.avarioncraft.Clans.administration.schematic.SchematicMemory;

import net.crytec.api.devin.commands.Command;
import net.crytec.api.devin.commands.CommandResult;
import net.crytec.api.devin.commands.Commandable;

public class AdminCommands implements Commandable {

	@Command(struct = "clanadmin reload", desc = "Lädt die Konfiguration neu", perms = "clan.admin")
	public CommandResult adminClanReloadCommand(Player sender) {
		// Config bzw. Plugin neu laden.
		return CommandResult.success();
	}

	@Command(struct = "clanadmin list", desc = "Listet alle Clans auf", perms = "clan.admin")
	public CommandResult adminClanListCommand(Player sender) {
		// Menü (pagination) öffnen in dem alle Clans aufgelistet sind -> Klicken: Clan Admin bearbeitung
		return CommandResult.success();
	}

	@Command(struct = "clanadmin clanhome create", desc = "Erstellt ein neues Clan-Home schematic", perms = "clan.admin")
	public CommandResult createClanHomeCommand(Player sender, int ID) {
		SchematicMemory.add(ID);
		return CommandResult.success();
	}

	@Command(struct = "clanadmin clanhome build", desc = "Erstellt ein neues Clan-Home schematic", perms = "clan.admin")
	public CommandResult buildClanHomeCommand(Player sender, int ID) {
		new AvarionSchematic(sender, ID).build();
		return CommandResult.success();
	}

}
