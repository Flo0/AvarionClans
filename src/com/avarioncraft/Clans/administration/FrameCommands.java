package com.avarioncraft.Clans.administration;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.avarioncraft.Clans.administration.framebuilder.PacketFrame;
import com.avarioncraft.Clans.administration.framebuilder.RawFrame;
import com.avarioncraft.Clans.administration.framebuilder.handling.FrameMemory;
import com.avarioncraft.Clans.administration.framebuilder.handling.FrameSender;
import com.avarioncraft.Clans.core.AvarionClans;

import net.crytec.api.devin.commands.Command;
import net.crytec.api.devin.commands.CommandResult;
import net.crytec.api.devin.commands.Commandable;

public class FrameCommands implements Commandable{

	@Command(struct = "cl frame build", desc = "fuu", perms = "clan.admin")
	public CommandResult framebuilder(Player sender, int arg) {
		
		FrameMemory.get().RawFrames.put(arg, new RawFrame());
		
		return CommandResult.success();
	}
	
	@Command(struct = "cl frame convert", desc = "fuu", perms = "clan.admin")
	public CommandResult frameconvert(Player sender, int arg) {
		
		FrameMemory.get().PacketFrames.put(sender, new PacketFrame(FrameMemory.get().RawFrames.get(arg), sender));
		
		return CommandResult.success();
	}
	
	@Command(struct = "cl frame play", desc = "fuu", perms = "clan.admin")
	public CommandResult frameplay(Player sender, int arg) {
		
		Bukkit.getScheduler().runTaskAsynchronously(AvarionClans.getPlugin(), new FrameSender(arg, FrameMemory.get().PacketFrames.get(sender)));
		
		return CommandResult.success();
	}
	
}