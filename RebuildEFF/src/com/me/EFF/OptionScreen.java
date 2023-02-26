package com.me.EFF;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class OptionScreen implements Screen{

	GameClass parent;
	private Stage stage;
	private Skin skin;
	public OptionScreen(final GameClass parent) {
		this.parent = parent;
		stage = new Stage();
		skin = new Skin(Gdx.files.internal(
				"data/skin/uiskin.json"));
		Gdx.input.setInputProcessor(stage);
		TextButton controls = new TextButton("Controls", skin);
		controls.setColor(.1f, .1f, .1f, 1);
		controls.setHeight(120);
		controls.setPosition(235, 100); 
		stage.addActor(controls);
		controls.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
	
				stage.dispose();
				skin.dispose();
				parent.chooseScreen(GameClass.MENU);
			}
		});
	}
	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();	
	}
	@Override
	public void render(float delta) {
		stage.draw();
		stage.act(Gdx.graphics.getRawDeltaTime());
		
	}
	@Override
	public void hide() {
		
		
		
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

}
