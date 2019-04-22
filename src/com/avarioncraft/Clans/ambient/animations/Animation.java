package com.avarioncraft.Clans.ambient.animations;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import com.google.common.collect.Lists;

public class Animation {
	
	public static ArrayList<Vector> drawCircle(Location loc, double radius, int precision){
		ArrayList<Vector> locations = Lists.newArrayList();
		
        for(int i = 0; i < precision; i++) {
        	
            double p1 = (i * Math.PI) / (precision / 2);
            double p2 = (((i == 0) ? precision : i-1) * Math.PI) / (precision / 2);
            
            double x1 = Math.cos(p1) * radius;
            double x2 = Math.cos(p2) * radius;
            double z1 = Math.sin(p1) * radius;
            double z2 = Math.sin(p2) * radius;
            
            Vector vec = new Vector(x2-x1,0,z2-z1);
            
            locations.add(vec);
            
         }
		
		return locations;
	}
	
}
