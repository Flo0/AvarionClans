package com.avarioncraft.Clans.regions.packet;

import org.bukkit.entity.Player;

import com.avarioncraft.Clans.administration.framebuilder.PacketFrame;
import com.avarioncraft.Clans.administration.framebuilder.handling.FrameMemory;
import com.avarioncraft.Clans.regions.selection.PreviewMode;
import com.avarioncraft.Clans.util.enums.ArchitectureStyle;

public class PreviewThread implements Runnable{
	
	public PreviewThread(Player player, ArchitectureStyle style) {
		this.player = player;
		System.out.println("Loading frame ID " + style.getStyleID());
		this.frame = new PacketFrame(FrameMemory.get().RawFrames.get(style.getStyleID()), player);
		this.border = new PacketFrame(FrameMemory.get().RawFrames.get(10), player);
	}
	
	private final Player player;
	private final PacketFrame frame;
	private final PacketFrame border;
	
	@Override
	public void run() {
		
		border.sendPlayer(player);
		
		while(PreviewMode.get().isInPreview(player)) {
			
			frame.sendPlayer(player);

			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			frame.sendPlayerEmpty(player);
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		border.sendPlayerEmpty(player);
		frame.sendPlayerEmpty(player);
		
	}
	
}
