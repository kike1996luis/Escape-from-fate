package com.me.EFF;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;

public class Item {

	private String type;
	private int goalID;
	private Vector2 position;
	private boolean isReceived;
	private Body item;
	private Sprite sprite, textGrab;
	private Texture imagen;
	private UI ui;
	private Vector2 originPos;
	private float originAngle;
	private String text;
	private Random random;
	private Sounds sounds;
	private TextureRegion[] movArrow;
	private Animation animationArrow;
	private float tiempo;
	private TextureRegion frameActual2;
	private Sprite spriteAux;
	private SpriteBatch batch;
	private boolean isClose = false;
	private World world;
	private Array<Body> tmpBodies = new Array<Body>();
	private boolean isInactive = true;

	public Item(String type, int goalID, Vector2 position, float angle, World world, UI ui, String text,
			SpriteBatch batch, Sounds sounds) {

		this.world = world;
		this.batch = batch;
		this.spriteAux = new Sprite();
		this.sounds = sounds;
		this.random = new Random();
		this.goalID = goalID;
		this.type = type;
		this.position = position;
		this.ui = ui;
		this.originPos = position;
		this.originAngle = angle;
		this.text = text;
		this.isReceived = false;
		this.isInactive = false;
		this.movArrow = new TextureRegion[10];
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		bodyDef.type = BodyType.StaticBody;
		PolygonShape box = new PolygonShape();
		box.setAsBox(.6f, .6f, new Vector2(0, -.2f), 0);
		fixtureDef.isSensor = true;
		fixtureDef.shape = box;
		item = world.createBody(bodyDef);
		item.createFixture(fixtureDef).setUserData(this);
		box.dispose();
		item.setTransform(position, 0);
		imagen = new Texture("data/sprites/UI/arrow.png");
		if (type.equals("key")) {

			int rand = random.nextInt(4);
			if (rand == 0) {
				sprite = new Sprite(new Texture("data/sprites/items/key1.png"));
			} else if (rand == 1) {
				sprite = new Sprite(new Texture("data/sprites/items/key2.png"));
			} else if (rand == 2) {
				sprite = new Sprite(new Texture("data/sprites/items/key3.png"));
			} else if (rand == 3) {
				sprite = new Sprite(new Texture("data/sprites/items/key4.png"));
			} else if (rand == 4) {
				sprite = new Sprite(new Texture("data/sprites/items/key5.png"));
			}
			sprite.setSize(6, 6);
			textGrab = new Sprite(new Texture("data/sprites/UI/grabKey.png"));
			textGrab.setSize(4.5f, 4.5f);
		} else if (type.equals("book")) {
			int rand = random.nextInt(4);
			if (rand == 0) {
				sprite = new Sprite(new Texture("data/sprites/items/book1.png"));
			} else if (rand == 1) {
				sprite = new Sprite(new Texture("data/sprites/items/book2.png"));
			} else if (rand == 2) {
				sprite = new Sprite(new Texture("data/sprites/items/book3.png"));
			} else if (rand == 3) {
				sprite = new Sprite(new Texture("data/sprites/items/book4.png"));
			}
			sprite.setSize(6, 6);
			textGrab = new Sprite(new Texture("data/sprites/UI/grabBook.png"));
			textGrab.setSize(4.5f, 4.5f);
		} else if (type.equals("note")) {
			int rand = random.nextInt(4);
			if (rand == 0) {
				sprite = new Sprite(new Texture("data/sprites/items/note1.png"));
			} else if (rand == 1) {
				sprite = new Sprite(new Texture("data/sprites/items/note2.png"));
			} else if (rand == 2) {
				sprite = new Sprite(new Texture("data/sprites/items/note3.png"));
			} else if (rand == 3) {
				sprite = new Sprite(new Texture("data/sprites/items/note4.png"));
			}
			sprite.setSize(5f, 5f);
			textGrab = new Sprite(new Texture("data/sprites/UI/grabNote.png"));
			textGrab.setSize(4.5f, 4.5f);
		}
		TextureRegion[][] tmp1 = TextureRegion.split(imagen, imagen.getWidth() / 5, imagen.getHeight());
		System.arraycopy(tmp1[0], 0, movArrow, 0, 5);
		for (int i = 9, k = 0; i >= 5; i--) {
			movArrow[i] = movArrow[k];
			k++;
		}
		animationArrow = new Animation(0.10f, movArrow);
		tiempo = 0f;
	}

	public void renderArrow() {
		if(!isInactive) {
			item.setUserData(this);
			if (isClose) {
				if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !isReceived) {
					sounds.grab.play();
					if (type.equals("key")) {
						isReceived = true;
					}
				}
				frameActual2 = animationArrow.getKeyFrame(tiempo, true);
				textGrab.setOrigin(textGrab.getWidth() / 2, textGrab.getHeight() / 2);
				textGrab.setPosition((item.getPosition().x - sprite.getWidth() / 2) + 1,
						(item.getPosition().y - sprite.getHeight() / 2));
				spriteAux.setRegion(frameActual2);
				spriteAux.setSize(5.5f, 5.5f);
				spriteAux.setOrigin(spriteAux.getWidth() / 2, spriteAux.getHeight() / 2);
				spriteAux.setPosition(item.getPosition().x - sprite.getWidth() / 2,
						item.getPosition().y - sprite.getHeight() / 2);
				batch.begin();
				textGrab.draw(batch);
				spriteAux.draw(batch);
				batch.end();
			}
		}
	}

	public void render() {
		if(!isInactive) {
			item.setUserData(this);
			if (!isReceived) {
				
				if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && isClose) {
					if(!sounds.grab.isPlaying()) {
						sounds.grab.play();
					}
					if (type.equals("key")) {
						isReceived = true;
					}
				}
				world.getBodies(tmpBodies);
				for (Body body : tmpBodies) {
					if (body.getUserData() != null && body.getUserData() instanceof Item) {
						batch.begin();
						sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
						sprite.setPosition(item.getPosition().x - sprite.getWidth() / 2,
								item.getPosition().y - sprite.getHeight() / 2);
						sprite.setRotation(originAngle);
						sprite.draw(batch);
						batch.end();
					}
				}
				tiempo += Gdx.graphics.getDeltaTime();
			} else {
				item.setTransform(-999, -999, 0);
			}
		}
	}

	public String getText() {
		return text;
	}

	public void restart() {
		item.setTransform(originPos, originAngle);
		isReceived = false;
		item.setUserData("active");
		isInactive = false;
	}

	public String getType() {
		return type;
	}

	public float getAngle() {
		return item.getAngle();
	}

	public void setPosition(Vector2 pos) {
		item.setTransform(pos, 0);
	}

	public void setReceived() {

		isClose = true;
	}

	public void endContact() {
		isClose = false;
	}

	public boolean isReceived() {
		return isReceived;
	}

	public int goalID() {
		return goalID;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void clean() {
		if(!isInactive ) {
			isInactive = true;
			sprite.getTexture().dispose();
			imagen.dispose();
			item.setUserData("inactive");
			item.setTransform(new Vector2(-999, -999), 0);
		}
	}
	
	public void dispose() {
		textGrab.getTexture().dispose();
		sprite.getTexture().dispose();
		imagen.dispose();
		item.setUserData("destroy");
	}
}
