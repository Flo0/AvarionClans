package com.avarioncraft.Clans.commands;

import org.bukkit.entity.Player;

import com.avarioncraft.Clans.gui.GUIs;

import net.crytec.api.devin.commands.Command;
import net.crytec.api.devin.commands.CommandResult;
import net.crytec.api.devin.commands.Commandable;

public class UserCommands implements Commandable {

	@Command(struct = "clan", desc = "Öffnet das Clan Menü")
	public CommandResult userClanCommand(Player sender) {
		GUIs.openMain(sender);
		return CommandResult.success();
	}
}