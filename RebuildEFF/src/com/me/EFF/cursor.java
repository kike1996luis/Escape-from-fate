package com.me.EFF;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class cursor {

	private Texture imagen;
	private TextureRegion[] crosshairIngame;
	private Animation animationTime;
	private float tiempo;
	private TextureRegion frameActual;
	private Sprite sprite;
	int pos = 0;
	float xaux = 0, yaux = 0;
	private boolean isChanged;

	public cursor() {

		isChanged = false;
		imagen = new Texture(Gdx.files.internal("data/sprites/UI/crosshairAnim.png"));
		TextureRegion[][] tmp = TextureRegion.split(imagen, imagen.getWidth() / 5, imagen.getHeight());
		crosshairIngame = new TextureRegion[10];
		System.arraycopy(tmp[0], 0, crosshairIngame, 0, 5);
		for (int i = 9, k = 0; i >= 5; i--) {
			crosshairIngame[i] = crosshairIngame[k];
			k++;
		}
		tiempo = 0f;
		animationTime = new Animation(0.06f, crosshairIngame);

	}

	public void dispose() {
		sprite.getTexture().dispose();
	}
	
	public void changeCursor(String type) {

		Pixmap customCursor = new Pixmap(Gdx.files.internal("data/sprites/UI/crosshairInvisible.png"));
		Gdx.input.setCursorImage(customCursor, 0, 0);
		customCursor.dispose();
		if (type.equals("inGameCursor")) {
			if(isChanged) {
				sprite.getTexture().dispose();
			}
			isChanged = true;
			frameActual = animationTime.getKeyFrame(tiempo, true);
			sprite = new Sprite(frameActual);
			sprite.setSize(100, 100);
		}
	}

	public void render(SpriteBatch batch) {

		if (isChanged) {

			xaux = Gdx.input.getX();
			yaux = Gdx.graphics.getHeight() - Gdx.input.getY();
			sprite = new Sprite(frameActual);
			sprite.setPosition(xaux - 29, yaux - 62);
			batch.begin();
			sprite.draw(batch);
			batch.end();
			frameActual = animationTime.getKeyFrame(tiempo, true);
			tiempo += Gdx.graphics.getDeltaTime();
		}
	}
}
