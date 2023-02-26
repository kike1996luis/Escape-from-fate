package com.me.EFF;

import com.badlogic.gdx.math.Vector2;

public class Cinematics {
	
	private boolean isAnimation;
	
	public Cinematics() {
		
	}
	
	public void buildCinematic(String type, Vector2 pos, float angle) {
		
		isAnimation = true;
		if(type.equals("map01C0")) {
			
		}
	}
	
	public void stopCinematic() {
		isAnimation = false;
	}
	
	public boolean isAnimation() {
		return isAnimation;
	}
}
