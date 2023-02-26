package com.me.EFF;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

public class GameClass extends Game {

	public final static int MENU = 0;
	public final static int OPTIONS = 1;
	public final static int STARTGAME = 2;
	public final static int CONTINUE = 3;
	public final static int SELECTMAP = 4;
	public static mainScreen mainMenu;
	public static CodeGame codeGame;
	public static OptionScreen options;
	private int lastLevel = 0;
	private boolean[] screenActive;
	private JSONDataRW dataReader;

	@Override
	public void create() {
		codeGame = new CodeGame(this);
		dataReader = new JSONDataRW(codeGame, this);
		screenActive = new boolean[5];
		mainMenu = new mainScreen(this);
		this.setScreen(mainMenu);
		setScreenActive(0);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		renderScreen();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	public void chooseScreen(int screen) {
		switch (screen) {
		case MENU:
			if (mainMenu == null) {
				mainMenu = new mainScreen(this);
			}
			setScreenNull();
			setScreenActive(0);
			this.setScreen(mainMenu);
			break;
		case OPTIONS:
			if (options == null) {
				options = new OptionScreen(this);
			}
			setScreenNull();
			setScreenActive(1);
			this.setScreen(options);
			
			break;
		case STARTGAME:
			setScreenActive(2);
			codeGame.chooseLevel(0);
			this.setScreen(codeGame);
			break;
		case CONTINUE:
			setScreenActive(3);
			dataReader.readJSON("data/gamedata.json");
			codeGame.chooseLevel(lastLevel);
			this.setScreen(codeGame);
			break;
		case SELECTMAP:
			break;
		}
	}

	public void renderScreen() {
		for (int i = 0; i < screenActive.length; i++) {
			if (screenActive[i]) {
				if (i == 0) {
					mainMenu.render(0);
				}
				else if (i == 1) {
					options.render(0);
				}
				else if (i == 2) {
					codeGame.render(0);
				}else if(i == 3) {
					codeGame.render(0);
				}
			}
		}
	}
	
	public void setScreenNull(){
		for (int i = 0; i < screenActive.length; i++){
			if(screenActive[0] == true){
				mainMenu= null;
			}
			else if(screenActive[1] == true){
				
				options= null;
			}
			else if(screenActive[2] == true){
				
				//codegame=null;
			}
			else if(screenActive[3] == true){
				
				//codegame=null;
			}
			else if(screenActive[4] == true){
				
				//selectmap= null;
			}
			
			
		}
	}

	private void setScreenActive(int act) {

		for (int i = 0; i < screenActive.length; i++) {
			if (i == act) {
				screenActive[i] = true;
			} else {
				screenActive[i] = false;
			}
		}
	}

	public void readData(int lastLevel) {
		
		this.lastLevel = lastLevel;
	}
}
