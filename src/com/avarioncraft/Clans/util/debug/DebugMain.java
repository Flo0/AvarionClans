package com.avarioncraft.Clans.util.debug;

import com.avarioncraft.Clans.core.AvarionClans;

import net.crytec.Debug;
import net.crytec.api.devin.commands.CommandRegistrar;
import net.crytec.api.devin.commands.Commandable;

public class DebugMain {
	private CommandRegistrar regist;
	public DebugMain() {
		if(DebugMode) {
			Debug.log("Debug mode is enabled");
			this.regist = new CommandRegistrar(AvarionClans.getPlugin());
		}
	}

	public static final boolean DebugMode = true;
	
	protected void registerCommand(Commandable C) {
		regist.registerCommands(C);
	}

}
