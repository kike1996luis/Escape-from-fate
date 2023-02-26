package com.me.EFF;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class mainScreen implements Screen{

	GameClass parent;
	private Stage stage;
	private Skin skin;
	public mainScreen(final GameClass parent) {
		
		this.parent = parent;
		stage = new Stage();
		skin = new Skin(Gdx.files.internal(
				"data/skin/uiskin.json"));
		Gdx.input.setInputProcessor(stage);
		TextButton startGame = new TextButton("New Game", skin);
		startGame.setColor(.1f, .1f, .1f, 1);
		startGame.setHeight(120);
		startGame.setPosition(235, 100); 
		stage.addActor(startGame);
		TextButton exit = new TextButton("Exit", skin);
		exit.setColor(.1f, .1f, .1f, 1);
		exit.setHeight(120);
		exit.setPosition(435, 100);
		stage.addActor(exit);
		TextButton Continue = new TextButton("Continue", skin);
		Continue.setColor(.1f, .1f, .1f, 1);
		Continue.setHeight(15);
		Continue.setPosition(435, 50);
		stage.addActor(Continue);
		TextButton Options = new TextButton("Options", skin);
		Options.setColor(.1f, .1f, .1f, 1);
		Options.setHeight(50);
		Options.setPosition(235, 50);
		stage.addActor(Options);
		exit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		});
		startGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.chooseScreen(GameClass.STARTGAME);
			}
		});
		Continue.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				parent.chooseScreen(GameClass.CONTINUE);
			}
		});
		Options.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				stage.dispose();
				skin.dispose();
				parent.chooseScreen(GameClass.OPTIONS);
			}
		});
	}
	
	@Override
	public void render(float delta) {
		stage.draw();
		stage.act(Gdx.graphics.getRawDeltaTime());
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();	
	}
}
