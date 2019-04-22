package com.avarioncraft.Clans.util.interfaces;

import com.avarioncraft.Clans.threads.SyncTickAction;

public interface SyncTickable {
	
	public SyncTickAction getTickAction();
	public int tickDelay();
	
}
