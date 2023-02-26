package com.me.EFF;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UI {

	private final Stage stage;
	private final Skin skin;
	private final ShapeRenderer fatigueBar;
	private final Texture txR;
	private Texture txR1;
	private Texture zoomTx;
	float fatiguebar = 200;
	TextButton Ammo;
	private final SpriteBatch batch;
	private TextButton aux;
	private boolean isZoomed;
	private cursor curs;
	private Sprite sprite2;
	private Sprite sprite;
	private Texture zoomTx1;
	private boolean weaponSelected[];
	private BitmapFont font;
	private Sprite spritesWeapon[] = { new Sprite(new Texture(
			"data/sprites/UI/ak47ui.png")),
			new Sprite(new Texture(
					"data/sprites/UI/m4a1ui.png")),
			new Sprite(new Texture(
					"data/sprites/UI/punchui.png")),
			new Sprite(new Texture(
					"data/sprites/UI/deagleui.png")),
			new Sprite(new Texture(
					"data/sprites/UI/minigunui.png")),
			new Sprite(new Texture(
					"data/sprites/UI/knifeui.png")),
			new Sprite(new Texture(
					"data/sprites/UI/crowbarui.png")),
			new Sprite(new Texture(
					"data/sprites/UI/shotgunui.png")) };
	private Sprite gameOver = new Sprite(new Texture("data/sprites/UI/gameover.png"));
	private boolean hide;
	public boolean isDeath;
	private int timego = 0;

	public UI(Weapon weapon) {

		font = new BitmapFont();
		font.setScale(2, 2);
		weaponSelected = new boolean[8];
		curs = new cursor();
		batch = new SpriteBatch();
		stage = new Stage();
		txR = new Texture(
				"data/sprites/UI/ui.png");
		txR1 = new Texture(
				"data/sprites/UI/punchui.png");
		zoomTx = new Texture(
				"data/sprites/UI/zoomOn.png");
		zoomTx1 = new Texture(
				"data/sprites/UI/zoomOff.png");
		skin = new Skin(Gdx.files.internal(
				"data/skin/uiskin.json"));
		fatigueBar = new ShapeRenderer();
		TextButton fatigue = new TextButton("Fatigue Bar", skin);
		fatigue.setColor(.1f, .1f, .1f, 1);
		fatigue.setPosition(235, 50);
		stage.addActor(fatigue);
		this.Ammo = new TextButton("    X/X    ", skin);
		this.Ammo.setPosition(135, 90);
		Ammo.setColor(.1f, .1f, .1f, 1);
		stage.addActor(Ammo);
		fatigueBar.dispose();
		aux = new TextButton("", skin);
		sprite = new Sprite(zoomTx);
		sprite2 = new Sprite(zoomTx1);
		sprite.setPosition(Gdx.graphics.getWidth() - 74, Gdx.graphics.getHeight() - 70);
		sprite2.setPosition(Gdx.graphics.getWidth() - 74, Gdx.graphics.getHeight() - 70);
		sprite.setSize(70, 70);
		sprite2.setSize(70, 70);
		gameOver.setSize(800, 200);
		gameOver.setPosition((Gdx.graphics.getWidth()/2)-375, (Gdx.graphics.getHeight()/2)-100);
		for (int i = 0; i < spritesWeapon.length; i++) {
			spritesWeapon[i].setPosition(25, -235);
			spritesWeapon[i].setSize(400, 400);
		}
	}

	public void changeCursor(String type) {
		curs.changeCursor(type);
	}
	
	public void dispose() {
		fatigueBar.dispose();
		stage.dispose();
		batch.dispose();
		txR.dispose();
		txR1.dispose();
		zoomTx.dispose();
		skin.dispose();
		sprite.getTexture().dispose();
		sprite.getTexture().dispose();
		for(int i=0;i<spritesWeapon.length;i++) {
			spritesWeapon[i].getTexture().dispose();
		}
	}

	public void hideUI() {
		hide = true;
		
	}
	
	private void putFalse() {
		for (int i = 0; i < weaponSelected.length; i++) {
			weaponSelected[i] = false;
		}
	}

	public void changeWeaponUI(String weapon) {

		putFalse();
		if (weapon.equals("ak47")) {
			weaponSelected[0] = true;
		} else if (weapon.equals("m4a1")) {
			weaponSelected[1] = true;
		} else if (weapon.equals("punch")) {
			weaponSelected[2] = true;
		} else if (weapon.equals("deagle")) {
			weaponSelected[3] = true;
		} else if (weapon.equals("minigun")) {
			weaponSelected[4] = true;
		} else if (weapon.equals("knife")) {
			weaponSelected[5] = true;
		} else if (weapon.equals("crowbar")) {
			weaponSelected[6] = true;
		} else if (weapon.equals("shotgun")) {
			weaponSelected[7] = true;
		}
	}

	public void setAmmo(int newAmmo, int limitW) {
		aux.setText(newAmmo + "/" + limitW);
		if (!Ammo.getText().equals(aux.getText())) {
			if (limitW == 999) {
				aux.setText("    X/X    ");
			}
			CharSequence asd = aux.getText();
			Ammo.setText(asd.toString());
		}
	}

	public void setZoomUI() {
		if (Gdx.input.isKeyPressed(Keys.V) && isZoomed) {
			isZoomed = false;

		} else if (!Gdx.input.isKeyPressed(Keys.V) && !isZoomed) {
			isZoomed = true;

		}
	}

	public void setfB(float fatigue) {
		this.fatiguebar = fatigue + 1.5f;
	}

	private void paint() {

		fatigueBar.begin(ShapeType.Filled);
		fatigueBar.setColor(.1f, .1f, .1f, 1);
		fatigueBar.rect(25, 30, 300, 15);
		fatigueBar.end();
		fatigueBar.begin(ShapeType.Filled);
		fatigueBar.setColor(1, .5f, 0, 1);
		fatigueBar.rect(25, 30, fatiguebar, 15);
		fatigueBar.end();
	}

	public void setDeath() {
		
		isDeath=true;
		timego=0;
	}
	
	public void render() {

		if(!hide) {
			paint();
			stage.draw();
			stage.act(Gdx.graphics.getRawDeltaTime());
			batch.begin();
			batch.draw(txR, 25, -235, 400, 400);
			for (int i = 0; i < weaponSelected.length; i++) {
				if (weaponSelected[i]) {
					spritesWeapon[i].draw(batch);
					break;
				}
			}
			if(isDeath) {
				if(timego>60) {
					gameOver.draw(batch);
				}
				else {
					timego++;
				}
				font.draw(batch, "Presione 'R' para reiniciar el nivel", (Gdx.graphics.getWidth()/2)-200, (Gdx.graphics.getHeight()/2)-125);
			}else {
				timego = 0;
			}
			if (isZoomed) {
				sprite.draw(batch);
			} else {
				sprite2.draw(batch);
			}
			batch.end();
			curs.render(batch);
		}
	}
}