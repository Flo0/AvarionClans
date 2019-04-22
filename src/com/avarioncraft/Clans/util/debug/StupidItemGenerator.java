package com.avarioncraft.Clans.util.debug;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

import net.crytec.api.itemstack.ItemBuilder;

public class StupidItemGenerator {

	final String alphabet = "0123456789abcdefghijklmopqrstuvwABCDEFGHIJKLMNOPQRSTUVWXYZ";
	final int N = alphabet.length();

	private char randChar() {
		return alphabet.charAt(ThreadLocalRandom.current().nextInt(this.N));
	}

	private String randWord(int length) {
		String word = "";
		for (int place = 0; place <= length; place++) {
			word += this.randChar();
		}
		return word;
	}

	private Material randMat() {
		Material mat = Material.values()[ThreadLocalRandom.current().nextInt(Material.values().length)];
		if (mat.toString().contains("LEGACY")) {
			return this.randMat();
		}
		return mat;
	}

	private String randLoreLine() {
		String line = "";
		for (int n = 0; n <= ThreadLocalRandom.current().nextInt(3, 8); n++) {
			line += this.randWord(ThreadLocalRandom.current().nextInt(4, 10)) + " ";
		}
		return line;
	}

	private List<String> randomLore() {
		List<String> lores = Lists.newArrayList();

		for (int n = 0; n <= ThreadLocalRandom.current().nextInt(2, 8); n++) {
			lores.add(this.randLoreLine());
		}

		return lores;
	}

	private ItemStack getFatItem() {

		ItemStack item = new ItemBuilder(this.randMat())
				.name("§a" + this.randWord(ThreadLocalRandom.current().nextInt(5, 14))).lore("").lore(this.randomLore())
				.build();
		if (ThreadLocalRandom.current().nextInt(100) >= 75) {
			item = new ItemBuilder(item).addNBTString(this.randWord(ThreadLocalRandom.current().nextInt(5, 8)),
					this.randWord(ThreadLocalRandom.current().nextInt(5, 8))).build();
			if (ThreadLocalRandom.current().nextInt(100) >= 50) {
				item = new ItemBuilder(item).addNBTInt(this.randWord(ThreadLocalRandom.current().nextInt(5, 8)),
						ThreadLocalRandom.current().nextInt(100, 1000)).build();
			}
		}
		return item;
	}

	public void addStupidItem(Player player) {
		player.getInventory().addItem(this.getFatItem());
	}
	
	public void addFilledBox(int amount, Player player) {
		
		Location loc = player.getLocation();
		for(int count = 0; count < amount; count++) {
			loc.getBlock().setType(Material.SHULKER_BOX);
			ShulkerBox box = (ShulkerBox) loc.getBlock().getState();
			for(int n = 0; n < 27; n++) {
				box.getInventory().addItem(this.getFatItem());
			}
			loc.getBlock().breakNaturally();
		}
		
	}

}
