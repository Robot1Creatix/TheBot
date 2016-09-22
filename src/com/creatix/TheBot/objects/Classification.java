package com.creatix.TheBot.objects;

import java.awt.Color;

public class Classification{
	
	public static enum ClassType{
		Irrelevant(Color.WHITE), Contingency(Color.YELLOW), Diego(Color.BLUE), Threat(Color.RED, Color.WHITE);
		
		public Color color1, color2;
		
		ClassType(Color c1){
			this.color1 = c1;
			this.color2 = c1;
		}
		ClassType(Color c1, Color c2){
			this.color1 = c1;
			this.color2 = c2;
		}
	}
	
	public ClassType type;
	public String className, conclusion;
	public float accessLevel;
	
	public Classification(String name,float al ,ClassType type, String conclusion){
		this.className = name;
		this.type = type;
		this.conclusion = conclusion;
		this.accessLevel = al;
	}
}
