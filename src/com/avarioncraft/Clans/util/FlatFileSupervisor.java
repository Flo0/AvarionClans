package com.avarioncraft.Clans.util;

import java.io.File;
import java.util.Set;

import com.google.common.collect.Sets;

import net.crytec.Debug;

public class FlatFileSupervisor {
	/**
	 * Hält alle default Ordner.
	 */
	private static Set<File> FolderSet = Sets.newHashSet();
	/**
	 * Fügt einen neuen default Ordner zur Datenstruktur hinzu.
	 * @param folder der neue defaultFolder.
	 */
	protected void addDefaultFolder(File folder) {
		FolderSet.add(folder);
	}
	/**
	 * Erstellt alle gebrauchten default Ordner und Files
	 */
	public void setupData() {
		// Erstelle Ordner, wenn sie nicht existieren
		for(File folder : FolderSet) {
			if(!folder.exists()) {
				
				folder.mkdirs();
				Debug.log("Ordner erstellt -> " + folder.getName() + " " + folder.getPath());
			}
		}
	}
}
