package com.me.aTester;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;

public class Layer {

	private Sprite sprite;
	private float initAngle;
	private int ID, actualColor;
	private Array<Body> tmpBodies = new Array<Body>();
	private SpriteBatch batch;
	private TextureRegion[] animationArr;
	private Animation animationField;
	private float tiempo;
	private TextureRegion frameActual2;
	private Body layerCircle;
	private World world;
	private boolean isSelected = false;
	private Color color[], originalColor;
	private boolean decrescendo;
	private int tempColor = 0;
	private Vector2 initPosition;

	public Layer(int ID, World world, String path, float x, float y, float angle, float height, float width, int Split,
			OrthographicCamera camera, SpriteBatch batch, float speed) {

		this.ID = ID;
		this.world = world;
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		bodyDef.position.set(x, y);
		bodyDef.type = BodyType.DynamicBody;

		CircleShape ballShape = new CircleShape();
		ballShape.setPosition(new Vector2(-1.2f, 0f));
		ballShape.setRadius(1.3f);

		fixtureDef.shape = ballShape;
		fixtureDef.isSensor = true;
		fixtureDef.density = 35;

		layerCircle = world.createBody(bodyDef);
		layerCircle.createFixture(fixtureDef);
		layerCircle.setUserData(ID);
		ballShape.dispose();
		layerCircle.setTransform(x, y, initAngle);
		layerCircle.setLinearDamping(5);
		layerCircle.setAngularDamping(20);

		this.sprite = new Sprite(new Texture(path));
		this.initAngle = angle;
		this.batch = batch;
		TextureRegion[][] tmp = TextureRegion.split(sprite.getTexture(), (int) sprite.getWidth() / Split,
				(int) sprite.getHeight());
		animationArr = new TextureRegion[Split];
		System.arraycopy(tmp[0], 0, animationArr, 0, Split);
		animationField = new Animation(speed, animationArr);
		tiempo = 0f;
		sprite.setSize(width, height);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.setPosition(x, y);
		sprite.rotate(angle);
		originalColor = sprite.getColor();
		color = new Color[10];
		color[0] = new Color(1, 0, 0, 1);
		color[1] = new Color(.8f, 0, 0, 1);
		color[2] = new Color(.6f, 0, 0, 1);
		color[3] = new Color(.4f, 0, 0, 1);
		color[4] = new Color(.2f, 0, 0, 1);
		color[5] = new Color(.2f, 0, 0, 1);
		color[6] = new Color(.4f, 0, 0, 1);
		color[7] = new Color(.6f, 0, 0, 1);
		color[8] = new Color(.8f, 0, 0, 1);
		color[9] = new Color(1, 0, 0, 1);
		initPosition = new Vector2(x, y);
	}

	public int getID() {
		return ID;
	}

	public void selectLayer() {
		isSelected = true;
	}

	public void deselectLayer() {
		isSelected = false;
		sprite.setColor(originalColor);
	}

	public void move() {
		if (isSelected) {
			if (Gdx.input.isKeyPressed(Keys.UP)) {
				layerCircle.setLinearVelocity(0, 10);
			} else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
				layerCircle.setLinearVelocity(0, -10);
			} else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
				layerCircle.setLinearVelocity(-10, 0);
			} else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
				layerCircle.setLinearVelocity(10, 0);
			} else if (Gdx.input.isKeyPressed(Keys.NUM_1)) {
				layerCircle.setAngularVelocity(1);
			} else if (Gdx.input.isKeyPressed(Keys.NUM_2)) {
				layerCircle.setAngularVelocity(-1);
			}
		}
	}
	
	public void restart() {
		layerCircle.setLinearVelocity(0, 0);
		layerCircle.setTransform(initPosition, initAngle * MathUtils.degreesToRadians);
	}

	public void render() {

		if (isSelected) {
			sprite.setColor(color[actualColor]);
		}
		if (actualColor < 9 && decrescendo == false) {
			if (tempColor < 3) {
				tempColor++;
			} else {
				actualColor++;
				tempColor = 0;
			}
		} else {
			if (actualColor == 0) {
				decrescendo = false;
			} else {
				decrescendo = true;
				if (tempColor < 3) {
					tempColor++;
				} else {
					actualColor--;
					tempColor = 0;
				}
			}
		}
		move();
		frameActual2 = animationField.getKeyFrame(tiempo, true);
		sprite.setRegion(frameActual2);
		world.getBodies(tmpBodies);
		for (Body body : tmpBodies) {
			int lol = (int) body.getUserData();
			if (body != null && lol == ID) {
				batch.begin();
				sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
						body.getPosition().y - sprite.getHeight() / 2);
				sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
				sprite.draw(batch);
				batch.end();
			}
		}
		tiempo += Gdx.graphics.getDeltaTime();
	}
}
