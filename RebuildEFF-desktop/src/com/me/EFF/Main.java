package com.me.EFF;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Escape From Fate";
		cfg.useGL20 = false;
		cfg.width = 1366;
		cfg.height = 768;
		cfg.resizable = false;
		cfg.fullscreen = true;
		new LwjglApplication(new GameClass(), cfg);
	}
}