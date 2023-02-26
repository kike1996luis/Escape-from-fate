package com.me.EFF;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class Sounds {

	public Music background0, grab, drop, ak47, minigun, footsteps, footstepsslow, footstepsfast, knife, empty, death,
	shotgun, deagle, m4a1, crowbar, punch, tired, running, break1, explosion, break2, splash1, splash2,
	splash3, bs1, bs2, bs3, bs4, bs5, puc1, puc2, puc3, pch1, pch2, pch3, pch4, pch5, pch6, pch7, pch8,
	bn1, bn2, bn3, bn4, bn5, bn6, bn7, bn8, bn9, bn10, bw1, bw2, bw3, hollow, capisci, alarm, cbarhit,
	knifehit;
	
	public void loadSounds() {

		drop = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/drop.wav"));
		grab = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/grab.wav"));
		background0 = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/grab.wav"));
		background0.setVolume(4);
		ak47 = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/ak47.wav"));
		m4a1 = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/m4a1.wav"));
		shotgun = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/shotgun.wav"));
		deagle = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/deagle.wav"));
		death = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/death.wav"));
		crowbar = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/crowbar.wav"));
		footsteps = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/footsteps.wav"));
		punch = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/punch.wav"));
		knife = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/knife.wav"));
		empty = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/empty.wav"));
		tired = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/tired.wav"));
		running = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/running.wav"));
		minigun = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/minigun.wav"));
		break1 = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/break1.wav"));
		break2 = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/break2.wav"));
		explosion = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/explosion.wav"));
		splash1 = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/splash1.wav"));
		splash2 = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/splash2.wav"));
		splash3 = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/splash3.wav"));
		bs1 = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/bs1.wav"));
		bs2 = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/bs2.wav"));
		bs3 = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/bs3.wav"));
		bs4 = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/bs4.wav"));
		bs5 = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/bs5.wav"));
		bn1 = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/bn1.wav"));
		bn2 = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/bn2.wav"));
		bn3 = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/bn3.wav"));
		bn4 = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/bn4.wav"));
		bn5 = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/bn5.wav"));
		bn6 = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/bn6.wav"));
		bn7 = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/bn7.wav"));
		bn8 = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/bn8.wav"));
		bn9 = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/bn9.wav"));
		bn10 = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/bn10.wav"));
		bw1 = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/bw1.wav"));
		bw2 = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/bw2.wav"));
		bw3 = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/bw3.wav"));
		puc1  = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/puc1.wav"));
		puc2  = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/puc2.wav"));
		puc3  = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/puc3.wav"));
		pch1  = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/pch1.wav"));
		pch2  = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/pch2.wav"));
		pch3  = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/pch3.wav"));
		pch4  = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/pch4.wav"));
		pch5  = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/pch5.wav"));
		pch6  = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/pch6.wav"));
		pch7  = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/pch7.wav"));
		pch8  = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/pch8.wav"));
		hollow  = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/hollow.wav"));
		alarm  = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/alarm.wav"));
		capisci  = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/capisci.wav"));
		cbarhit = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/cbarhit.wav"));
		knifehit = Gdx.audio.newMusic(Gdx.files.internal(
				"data/sounds/knifehit.wav"));background0.setVolume(0);
	}

	public void stopSounds() {
		
		knifehit.stop();
		cbarhit.stop();
		alarm.stop();
		capisci.stop();
		hollow.stop();
		drop.stop();
		grab.stop();
		ak47.stop();
		m4a1.stop();
		shotgun.stop();
		deagle.stop();
		death.stop();
		crowbar.stop();
		footsteps.stop();
		punch.stop();
		knife.stop();
		empty.stop();
		tired.stop();
		running.stop();
		minigun.stop();
		bs1.stop();
		bs2.stop();
		bs3.stop();
		bs4.stop();
		bs5.stop();
		bw1.stop();
		bw2.stop();
		bw3.stop();
		puc1.stop();
		puc2.stop();
		puc3.stop();
		pch1.stop();
		pch2.stop();
		pch3.stop();
		pch4.stop();
		pch5.stop();
		pch6.stop();
		pch7.stop();
		pch8.stop();
		bn1.stop();
		bn2.stop();
		bn3.stop();
		bn4.stop();
		bn5.stop();
		bn6.stop();
		bn7.stop();
		bn8.stop();
		bn9.stop();
		bn10.stop();
	}
	
	public void disposeAssets() {

		knifehit.dispose();
		cbarhit.dispose();
		bw1.dispose();
		bw2.dispose();
		bw3.dispose();
		bn1.dispose();
		bn2.dispose();
		bn3.dispose();
		bn4.dispose();
		bn5.dispose();
		bn6.dispose();
		bn7.dispose();
		bn8.dispose();
		bn9.dispose();
		bn10.dispose();
		pch1.dispose();
		pch2.dispose();
		pch3.dispose();
		pch4.dispose();
		pch5.dispose();
		pch6.dispose();
		pch7.dispose();
		pch8.dispose();
		puc1.dispose();
		puc2.dispose();
		puc3.dispose();
		bs1.dispose();
		bs2.dispose();
		bs3.dispose();
		bs4.dispose();
		bs5.dispose();
		splash1.dispose();
		splash2.dispose();
		splash3.dispose();
		explosion.dispose();
		drop.dispose();
		grab.dispose();
		ak47.dispose();
		m4a1.dispose();
		shotgun.dispose();
		deagle.dispose();
		death.dispose();
		crowbar.dispose();
		footsteps.dispose();
		punch.dispose();
		knife.dispose();
		empty.dispose();
		tired.dispose();
		running.dispose();
		minigun.dispose();
		background0.dispose();
		break1.dispose();
		break2.dispose();
		hollow.dispose();
		capisci.dispose();
		alarm.dispose();
	}
}
