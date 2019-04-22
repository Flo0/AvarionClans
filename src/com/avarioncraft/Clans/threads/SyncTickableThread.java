package com.avarioncraft.Clans.threads;

import com.avarioncraft.Clans.threads.memory.SyncMemory;

public class SyncTickableThread implements Runnable{
	
	private final long runtime = System.currentTimeMillis();
	private int tick = 1;
	
	@Override
	public void run() {
		
		if(tick < 19) {
			tick++;
		}else {
			tick = 1;
		}
		
		SyncMemory.fillQueue(tick);
		
		while(System.currentTimeMillis() - runtime <= 50) {
			
			SyncMemory.SyncCashe.poll().action();
			if(SyncMemory.SyncCashe.isEmpty()) break;
			
		}
		
	}
	
	
}
