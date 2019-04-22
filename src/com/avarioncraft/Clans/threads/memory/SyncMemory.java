package com.avarioncraft.Clans.threads.memory;

import java.util.Queue;
import java.util.Set;

import com.avarioncraft.Clans.threads.SyncTickAction;
import com.avarioncraft.Clans.util.interfaces.SyncTickable;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;

public class SyncMemory {
	
	public static Queue<SyncTickAction> SyncCashe = Queues.newArrayDeque();
	public static Set<SyncTickable> SyncTickables = Sets.newHashSet();
	
	public static void fillQueue(int tick) {
		SyncTickables.forEach(tickable->{
			if(tickable.tickDelay() == tick) {
				SyncCashe.add(tickable.getTickAction());
			}
		});
	}
}