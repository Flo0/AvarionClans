package com.avarioncraft.Clans.administration.framebuilder.handling;

import com.avarioncraft.Clans.administration.framebuilder.PacketFrame;

public class FrameSender implements Runnable {

	public FrameSender(int times, PacketFrame frame) {
		this.frame = frame;
		this.times = times;
	}

	private final PacketFrame frame;
	private final int times;
	private int count = 0;

	@Override
	public void run() {

		while (count < times) {
			count++;
			frame.send();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			frame.sendEmpty();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

}
