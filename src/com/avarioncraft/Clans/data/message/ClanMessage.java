package com.avarioncraft.Clans.data.message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;

public class ClanMessage implements Comparable<ClanMessage>{
	
	public ClanMessage(String title, String message, long creationTime, String sender) {
		this.title = title;
		this.message = message;
		this.creationTime = creationTime;
		this.sender = sender;
	}
	@Getter
	private final String title;
	private final String message;
	@Getter
	private final long creationTime;
	@Getter
	private final String sender;
	
	public String getDate() {
	       Date date = new Date();
	       date.setTime(this.creationTime);
	       DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	       return "§o" + sdf.format(date);
	}
	
	public String[] getLores() {
		String[] backslash = this.message.split("%");
		for(int n = 0; n < backslash.length; n++) {
			backslash[n] = "§f" + backslash[n];
		}
		return backslash;
	}
	
	@Override
	public int compareTo(ClanMessage incomming) {
		if(incomming.getCreationTime() < this.creationTime) {
			return -1;
		}else {
			return 1;
		}
	}
	
}
